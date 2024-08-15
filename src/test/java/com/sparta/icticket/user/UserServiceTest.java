package com.sparta.icticket.user;




import com.sparta.icticket.common.enums.UserStatus;
import com.sparta.icticket.order.OrderRepository;
import com.sparta.icticket.user.dto.UserProfileRequestDto;
import com.sparta.icticket.user.dto.UserProfileResponseDto;
import com.sparta.icticket.user.dto.UserResignRequestDto;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private User user;

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
        verify(passwordEncoder).encode(mock.getPassword());
        verify(userRepository).save(saveUser.capture());

        assertEquals(mock.getEmail(),saveUser.getValue().getEmail());
        // password는 encoding되기때문에 비교하면 오류뜸
        assertEquals(mock.getName(),saveUser.getValue().getName());
        assertEquals(mock.getNickname(),saveUser.getValue().getNickname());
        assertEquals(mock.getPhoneNumber(),saveUser.getValue().getPhoneNumber());
        assertEquals(mock.getAddress(),saveUser.getValue().getAddress());
    }



    @Test
    void createAdminUser() {
        //given

        //when

        //then

    }

    @Test
    @WithMockUser
    void logout() {
        //given
        User mockUser = mock(User.class);
        //when
        userService.logout(mockUser);
        //then
        verify(mockUser).removeRefreshToken();
    }

    @Test
    void resignUser() {
        //given
        UserResignRequestDto requestDtoMock = mock(UserResignRequestDto.class);
        when(requestDtoMock.getPassword()).thenReturn("qwer1234");

        User loginUserMock = mock(User.class);
        String encodedPassword = passwordEncoder.encode("qwer1234");
        when(loginUserMock.getEmail()).thenReturn("example@naver.com");
        when(loginUserMock.getPassword()).thenReturn(encodedPassword);
//        when(loginUserMock.getUserStatus()).thenReturn(UserStatus.ACTIVATE);

        when(userRepository.findByEmailAndUserStatus(loginUserMock.getEmail(), UserStatus.ACTIVATE))
                .thenReturn(Optional.of(loginUserMock));
        when(passwordEncoder.matches(requestDtoMock.getPassword(), loginUserMock.getPassword()))
                .thenReturn(true);

        //when
        userService.resignUser(requestDtoMock,loginUserMock);
        //then
        verify(loginUserMock).updateResignUser();
//        assertNull(loginUserMock.getRefreshToken());
//        assertEquals(UserStatus.DEACTIVATE,loginUserMock.getUserStatus());
    }
    // + INVALID_PASSwORD 예외처리

    @Test
    void updateProfile() {
        //given
        UserProfileRequestDto requestDtoMock = mock(UserProfileRequestDto.class);
        when(requestDtoMock.getNickname()).thenReturn("after");
//        when(requestDtoMock.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(requestDtoMock.getAddress()).thenReturn("수정후주소");

        User userBeforeUpdate = mock(User.class);
        when(userBeforeUpdate.getEmail()).thenReturn("example@naver.com");
//        when(userBeforeUpdate.getNickname()).thenReturn("before");
//        when(userBeforeUpdate.getPhoneNumber()).thenReturn("010-0000-0000");
//        when(userBeforeUpdate.getAddress()).thenReturn("수정전주소");

        when(userRepository.findByEmailAndUserStatus(userBeforeUpdate.getEmail(),UserStatus.ACTIVATE))
                .thenReturn(Optional.of(userBeforeUpdate));

        //when
        userService.updateProfile(requestDtoMock, userBeforeUpdate);
        //then
//        verify(userService).checkDuplicateNickname(requestDtoMock.getNickname());
        verify(userBeforeUpdate).updateUserProfile(requestDtoMock);
//        assertEquals("after",userBeforeUpdate.getNickname());
//        assertEquals("010-1234-5678",userBeforeUpdate.getPhoneNumber());
//        assertEquals("수정후주소",userBeforeUpdate.getAddress());
    }
    // + 닉네임 중복 검사 테스트코드

    @Test
    void getProfile() {
        //given
        User mockUser = mock(User.class);
        when(mockUser.getEmail()).thenReturn("example@naver.com");

        when(userRepository.findByEmailAndUserStatus(mockUser.getEmail(), UserStatus.ACTIVATE))
                .thenReturn(Optional.of(mockUser));
        when(orderRepository.countAllByUser(mockUser)).thenReturn(3);
        //when
        UserProfileResponseDto result = userService.getProfile(mockUser);
        //then
        assertEquals(3,result.getOrderCount());
    }
}