package server.apptech.comment.commentlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.comment.commentlike.domain.CommentLike;
import server.apptech.comment.commentlike.domain.repository.CommentLikeRepository;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Long addCommentLike(Long userId, Long commentId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));

        // 이미 누른 경우
        if(commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)){
            throw new RestApiException(ExceptionCode.ALREADY_LIKED_COMMENT);
        }

        return commentLikeRepository.save(CommentLike.of(comment, user)).getId();
    }

    public void cancelCommentLike(Long userId, Long commentId) {
        userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        commentRepository.findById(commentId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));

        // 안 눌렀을 경우
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId).orElseThrow(() -> new RestApiException(ExceptionCode.COMMENT_NOT_LIKED));
        commentLikeRepository.delete(commentLike);
    }
}
