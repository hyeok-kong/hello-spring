package hello.hellospring.service;

import hello.hellospring.common.exception.UnAuthorizationException;
import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.dto.CommentDto;
import hello.hellospring.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;
    @Mock
    LoginService loginService;
    @Mock
    EntityManager entityManager;

    Comment comment;
    Member member1;
    Member member2;
    Post post;
    CommentDto.Request request;

    @BeforeEach
    void init() {
        request = new CommentDto.Request("test");

        member1 = Member.builder()
                .id(1L)
                .email("test1@test.com")
                .password("password")
                .nickname("testuser1")
                .build();

        member2 = Member.builder()
                .id(2L)
                .email("test2@test.com")
                .password("password")
                .nickname("testuser2")
                .build();

        post = Post.builder()
                .title("test title")
                .content("test content")
                .member(member1)
                .build();

        comment = Comment.builder()
                .content("test content")
                .post(post)
                .member(member1)
                .build();
    }


    @Test
    @DisplayName("댓글 작성에 성공할 경우 댓글이 등록된다")
    void 댓글_작성_테스트() {
        //given

        //when
        commentService.saveComment(request, post, member1);

        //then
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 삭제에 성공할 경우 댓글이 삭제된다")
    void 댓글_삭제_성공_테스트() {
        //given
        comment = mock();
        when(entityManager.merge(comment)).thenReturn(comment);

        //when
        commentService.deleteComment(comment);

        //then
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    @DisplayName("댓글이 없을 경우 NoSuchElementException 예외가 발생한다")
    void 조회하려는_댓글이_없음_테스트() {
        //given
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> commentService.findCommentById(any()));
    }

    @Test
    @DisplayName("주인일 경우 true를 반환한다")
    void 댓글_주인_테스트() {
        //given
        when(loginService.getLoggedInMember()).thenReturn(member1);

        //when
        boolean isOwner = commentService.isOwner(comment);

        //then
        assertThat(isOwner).isTrue();
    }

    @Test
    @DisplayName("주인이 아닐 경우 UnAuthorizationException 예외가 발생한다")
    void 댓글_주인_아님_테스트() {
        //given
        when(loginService.getLoggedInMember()).thenReturn(member2);

        //then
        assertThrows(UnAuthorizationException.class, () -> commentService.isOwner(comment));
    }
}