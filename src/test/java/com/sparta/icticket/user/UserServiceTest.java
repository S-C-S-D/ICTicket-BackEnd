package com.sparta.icticket.user;




import com.sparta.icticket.order.OrderRepository;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        //given
        UserSignupRequestDto mock = Mockito.mock(UserSignupRequestDto.class);
        when(mock.getEmail()).thenReturn("example@naver.com");
        when(mock.getPassword()).thenReturn("qwer1234");
        when(mock.getName()).thenReturn("홍길동");
        when(mock.getNickname()).thenReturn("tweetie");
        when(mock.getPhoneNumber()).thenReturn("010-1234-5678");
        when(mock.getAddress()).thenReturn("천국");

        //when
        userService.createUser(mock);

        //then
        ArgumentCaptor<User> saveUser=ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saveUser.capture());
        assertEquals(mock.getEmail(),saveUser.getValue().getEmail());
    }


    @Test
    void createAdminUser() {
        //given

        //when

        //then

    }

    @Test
    void logout() {
    }

    @Test
    void resignUser() {

    }

    @Test
    void updateProfile() {

    }

    @Test
    void getProfile() {

    }
}