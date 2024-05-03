package hello.hellospring.controller;


import hello.hellospring.domain.Post;
import hello.hellospring.dto.PostDto;
import hello.hellospring.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.Response> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(PostDto.Response.of(postService.findPostById(postId)));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> savePost(@RequestBody @Valid PostDto.Request request) {
        postService.saveNewPost(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<HttpStatus> updatePost(@PathVariable Long postId, @RequestBody @Valid PostDto.Request request) {
        Post post = postService.findPostById(postId);
        postService.updatePost(post, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
