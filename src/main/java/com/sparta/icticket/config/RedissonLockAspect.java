package com.sparta.icticket.config;

import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j(topic = "Redisson Lock")
@RequiredArgsConstructor
public class RedissonLockAspect {
    private static final String LOCK_KEY = "lock";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.sparta.icticket.config.DistributedLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        for(int i = 0; i < signature.getParameterNames().length; i++){
            log.info("test1: " + signature.getParameterNames()[i]);
            log.info("test2: " + joinPoint.getArgs()[i]);
        }
//        log.info("test다" + getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key()).toString());
        List<Long> seatIdList = ((SeatReservedRequestDto) joinPoint.getArgs()[1]).getSeatIdList();



//        String key = distributedLock.key() + ;

        RLock lock = redissonClient.getFairLock(LOCK_KEY);

        try {
            boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
            if (!isLocked) {
                log.info("Lock 획득 실패={}", LOCK_KEY);
                return null;
            }
            log.info("로직 수행");
            Object proceed = aopForTransaction.proceed(joinPoint);
            return proceed;
        } catch (InterruptedException e) {
            log.info("에러 발생");
            throw e;
        } finally {
            log.info("락 해제");
            lock.unlock();
        }
    }

    public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, Object.class);
        // lock + sessionId + 1 + requestDto + com.sparta.icticket.seat.dto.SeatReservedRequestDto@7446776e 1
        // lock + sessionId + 1 + requestDto + com.sparta.icticket.seat.dto.SeatReservedRequestDto@7446776e 2
    }
}