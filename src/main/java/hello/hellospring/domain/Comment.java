package hello.hellospring.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of = {"id", "content"})
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }
}
