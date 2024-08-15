package com.sparta.icticket.comment;

import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    PerformanceRepository performanceRepository;

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @BeforeEach
    void setUp() {
        System.out.println("performanceRepository: " + performanceRepository);
    }

    @Test
    void createComment() {
        //given
        Long performanceId = 1L;

        CreateCommentRequestDto requestDtoMock = mock(CreateCommentRequestDto.class);
        when(requestDtoMock.getTitle()).thenReturn("댓글제목");
        when(requestDtoMock.getDescription()).thenReturn("댓글내용");
        when(requestDtoMock.getRate()).thenReturn(5);

        User loginUserMock = mock(User.class);

        Performance performanceMock = mock(Performance.class);
        when(performanceMock.getId()).thenReturn(performanceId);

        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(performanceMock));
        //when
        commentService.createComment(performanceId, requestDtoMock,loginUserMock);
        //then
        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        Comment savedComment = captor.getValue();
        assertEquals("댓글제목",savedComment.getTitle());
        assertEquals("댓글내용",savedComment.getDescription());
        assertEquals(5,savedComment.getRate());
        assertEquals(loginUserMock,savedComment.getUser());
        assertEquals(performanceId,savedComment.getPerformance().getId());
    }

    @Test
    void deleteComment() {
        //given
        Long performanceId = 1L;

        Long commentId = 1L;

        User loginUserMock = mock(User.class);

        Comment commentMock = mock(Comment.class);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentMock));
        //when
        commentService.deleteComment(performanceId,commentId,loginUserMock);
        //then
        verify(commentRepository).delete(commentMock);
    }
    // + checkPerformance,checkUser

    @Test
    void getComments() {

    }
}