package hello.hellospring.dto;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PostDto {

    @Getter
    public static class Request {

        @NotEmpty
        @Length(max = 100, message = "제목은 최대 100자입니다.")
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private Long memberId;

        @Builder
        public Request(String title, String content, Long memberId) {
            this.title = title;
            this.content = content;
            this.memberId = memberId;
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
    public static class Response {
        private Long postId;
        private String title;
        private String content;
        private Integer viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;

        private String email;
        private String nickname;

        public static Response of(Post posts) {
            return Response.builder()
                    .postId(posts.getId())
                    .title(posts.getTitle())
                    .content(posts.getContent())
                    .viewCount(posts.getViewCount())
                    .createdDate(posts.getCreatedDate())
                    .updatedDate(posts.getUpdatedDate())
                    .email(posts.getMember().getEmail())
                    .nickname(posts.getMember().getNickname())
                    .build();
        }
    }
}
