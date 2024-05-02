package hello.hellospring.domain;


import hello.hellospring.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "title", "content", "viewCount"})
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private int viewCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "posts")
    private List<Comment> comment = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(PostDto.Request request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
