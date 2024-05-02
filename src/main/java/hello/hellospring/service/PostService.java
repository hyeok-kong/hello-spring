package hello.hellospring.service;


import hello.hellospring.domain.Post;
import hello.hellospring.dto.PostDto;
import hello.hellospring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    public Long saveNewPost(PostDto.Request request) {
        Post post = request.toEntity(memberService.findMemberById(request.getMemberId()));
        return postRepository.save(post).getId();
    }

    public Long updatePost(Post post, PostDto.Request request) {
        post.update(request);
        return post.getId();
    }

    public Post findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        post.increaseViewCount();
        return post;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
}
