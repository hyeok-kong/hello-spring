package hello.hellospring.service;


import hello.hellospring.domain.Post;
import hello.hellospring.dto.PostDto;
import hello.hellospring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;


    public void saveNewPost(PostDto.Request request) {
        Post post = request.toEntity(memberService.findMemberById(request.getMemberId()));
        postRepository.save(post);
    }

    public void updatePost(Post post, PostDto.Request request) {
        post.update(request);
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        post.increaseViewCount();
        return post;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
