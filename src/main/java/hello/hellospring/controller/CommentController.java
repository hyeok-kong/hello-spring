package hello.hellospring.controller;


import hello.hellospring.common.annotation.LoginMember;
import hello.hellospring.common.annotation.LoginRequired;
import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.dto.CommentDto;
import hello.hellospring.service.CommentService;
import hello.hellospring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hello.hellospring.common.HttpStatusResponseEntity.RESPONSE_OK;

@Controller
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto.Response>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.findAllCommentsByPostId(postId);
        return ResponseEntity.ok(CommentDto.Response.of(comments));
    }

    @LoginRequired
    @PostMapping("/{postId}")
    public ResponseEntity<HttpStatus> addComment(@PathVariable Long postId,
                                                 @RequestBody CommentDto.Request request,
                                                 @LoginMember Member member) {
        Post post = postService.findPostById(postId);
        commentService.saveComment(request, post, member);
        return RESPONSE_OK;
    }

    @LoginRequired
    @PatchMapping("/{commentId}")
    public ResponseEntity<HttpStatus> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentDto.Request request) {
        Comment comment = commentService.findCommentById(commentId);
        commentService.updateComment(request, comment);
        return RESPONSE_OK;
    }

    @LoginRequired
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long commentId) {
        Comment comment = commentService.findCommentById(commentId);
        commentService.deleteComment(comment);
        return RESPONSE_OK;
    }
}
