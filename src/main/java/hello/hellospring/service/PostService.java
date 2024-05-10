package hello.hellospring.service;


import hello.hellospring.common.exception.UnAuthorizationException;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.dto.PostDto;
import hello.hellospring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LoginService loginService;

    public void saveNewPost(PostDto.Request request, Member member) {
        Post post = request.toEntity(member);
        postRepository.save(post);
    }

    public void updatePost(Post post, PostDto.Request request) {
        if (isOwner(post)) {
            post.update(request);
        }
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        post.increaseViewCount();
        return post;
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (isOwner(post)) {
            postRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public boolean isOwner(Post post) {
        Member member = loginService.getLoggedInMember();
        if (member != post.getMember()) {
            throw new UnAuthorizationException();
        }
        return true;
    }
}
