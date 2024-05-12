package hello.hellospring.dto;


import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class Request {

        @NotEmpty
        private String content;

        @Builder
        public Request(String content) {
            this.content = content;
        }

        public Comment toEntity(Post post, Member member) {
            return Comment.builder()
                    .content(content)
                    .post(post)
                    .member(member)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class Response {
        private Long commentId;
        private String content;
        private Long memberId;
        private String nickname;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response of(Comment comment) {
            return Response.builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .memberId(comment.getMember().getId())
                    .nickname(comment.getMember().getNickname())
                    .createdAt(comment.getCreatedDate())
                    .updatedAt(comment.getUpdatedDate())
                    .build();
        }

        public static List<Response> of(List<Comment> comments) {
            return comments.stream()
                    .map(Response::of)
                    .toList();
        }
    }
}
