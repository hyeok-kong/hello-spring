package hello.hellospring.dto;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Getter
    public static class Request {

        @NotEmpty
        @Length(max = 100, message = "제목은 최대 100자입니다.")
        private String title;

        @NotEmpty
        private String content;

        @Builder
        public Request(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public Post toEntity(Member member) {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .member(member)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class Response {
        private Long postId;
        private String title;
        private String content;
        private Integer viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;

        private String email;
        private String nickname;

        public static Response of(Post post) {
            return Response.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .viewCount(post.getViewCount())
                    .createdDate(post.getCreatedDate())
                    .updatedDate(post.getUpdatedDate())
                    .email(post.getMember().getEmail())
                    .nickname(post.getMember().getNickname())
                    .build();
        }

        public static Page<Response> of(Page<Post> posts) {
            List<Response> pagedPosts = posts.getContent().stream()
                    .map(Response::of)
                    .toList();

            return new PageImpl<>(pagedPosts, posts.getPageable(), posts.getTotalElements());
        }
    }
}
