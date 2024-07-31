package com.sparta.icticket.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j(topic = "Redisson Lock")
@RequiredArgsConstructor
public class RedissonLockAspect {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.sparta.icticket.common.config.DistributedLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        // ex) LOCK:seat-sessionId-1
        String key = distributedLock.key() + "-" + signature.getParameterNames()[0] + "-" + joinPoint.getArgs()[0];
        log.info("키 이름: " + key);

        RLock lock = redissonClient.getFairLock(key);

        try {
            boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
            if (!isLocked) {
                log.info("Lock 획득 실패={}", key);
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
}