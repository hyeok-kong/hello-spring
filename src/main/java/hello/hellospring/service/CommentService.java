package hello.hellospring.service;

import hello.hellospring.common.annotation.OwnerOnly;
import hello.hellospring.common.exception.UnAuthorizationException;
import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.dto.CommentDto;
import hello.hellospring.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LoginService loginService;
    private final EntityManager entityManager;

    public List<Comment> findAllCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public void saveComment(CommentDto.Request request, Post post, Member member) {
        Comment comment = request.toEntity(post, member);
        commentRepository.save(comment);
    }

    public void updateComment(CommentDto.Request request, Comment comment) {
        if (isOwner(comment)) {
            Comment mergedComment = entityManager.merge(comment);
            mergedComment.update(request.getContent());
        }
    }

    @OwnerOnly(Comment.class)
    public void deleteComment(Comment comment) {
        Comment mergedComment = entityManager.merge(comment);
        commentRepository.delete(mergedComment);
    }

//    public void deleteComment(Comment comment) {
//        if (isOwner(comment)) {
//            Comment mergedComment = entityManager.merge(comment);
//            commentRepository.delete(mergedComment);
//        }
//    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Comment comment) {
        Member member = loginService.getLoggedInMember();
        if (comment.getMember() != member) {
            throw new UnAuthorizationException();
        }
        return true;
    }
}
