package hello.hellospring.controller;


import hello.hellospring.domain.Post;
import hello.hellospring.dto.PostDto;
import hello.hellospring.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hello.hellospring.common.HttpStatusResponseEntity.RESPONSE_OK;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostDto.Response>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> posts = postService.findAllPosts(pageable);
        return ResponseEntity.ok(PostDto.Response.of(posts));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.Response> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(PostDto.Response.of(postService.findPostById(postId)));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> savePost(@RequestBody @Valid PostDto.Request request) {
        postService.saveNewPost(request);
        return RESPONSE_OK;
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<HttpStatus> updatePost(@PathVariable Long postId, @RequestBody @Valid PostDto.Request request) {
        Post post = postService.findPostById(postId);
        postService.updatePost(post, request);
        return RESPONSE_OK;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return RESPONSE_OK;
    }
}
