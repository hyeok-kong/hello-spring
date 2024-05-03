package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.dto.PostDto;
import hello.hellospring.repository.PostRepository;
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

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    MemberService memberService;

    private Member member;
    private PostDto.Request request;
    private Post post;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .email("test@admin.com")
                .password("password")
                .nickname("테스트 멤버")
                .build();

        request = PostDto.Request.builder()
                .title("테스트")
                .content("테스트입니다. PostService의 동작을 확인합니다.")
                .memberId(member.getId())
                .build();

        post = request.toEntity(member);

    }

    @Test
    @DisplayName("성공할 경우 게시글이 등록된다.")
    void 게시글_저장_성공_테스트() {
        //given
//        when(memberService.findMemberById(any())).thenReturn(member);
//        when(postRepository.save(any())).thenReturn(post);

        //when
        postService.saveNewPost(request);

        //then
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("특정 아이디의 게시글이 존재하면, 성공적으로 조회한다.")
    void 게시글_조회_성공_테스트() {
        //given
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        //when
        Post testPost = postService.findPostById(post.getId());

        //then
        assertEquals(testPost.getTitle(), post.getTitle());
        assertEquals(testPost.getContent(), post.getContent());
    }

    @Test
    @DisplayName("특정 아이디의 게시글이 존재하지 않으면, NoSuchElementException 예외를 발생시킨다.")
    void 게시글_조회_실패_테스트() {
        //given
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        //when

        //then
        assertThrows(NoSuchElementException.class, () -> postService.findPostById(post.getId()));
    }

    @Test
    @DisplayName("게시글 조회에 성공하면 조회수를 증가시킨다.")
    void 게시글_조회수_증가_테스트() {
        //given
        Post testPost = mock(Post.class);
        when(postRepository.findById(any())).thenReturn(Optional.of(testPost));

        //when
        postService.findPostById(testPost.getId());

        //then
        verify(testPost, times(1)).increaseViewCount();
    }
}