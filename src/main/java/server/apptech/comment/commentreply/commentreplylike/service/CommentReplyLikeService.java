package server.apptech.comment.commentreply.commentreplylike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.comment.commentreply.commentreplylike.CommentReplyLike;
import server.apptech.comment.commentreply.commentreplylike.repository.CommentReplyLikeRepository;
import server.apptech.comment.commentreply.domain.CommentReply;
import server.apptech.comment.commentreply.domain.repository.CommentReplyRepository;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentReplyLikeService {

    private final CommentReplyLikeRepository commentReplyLikeRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final UserRepository userRepository;
    public Long addCommentReplyLike(Long userId, Long commentReplyId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));

        // 이미 누른 경우
        if(commentReplyLikeRepository.existsByCommentReplyIdAndUserId(commentReply.getId(), userId)){
            throw new RestApiException(ExceptionCode.ALREADY_LIKED_COMMENT_REPLY);
        }

        return commentReplyLikeRepository.save(CommentReplyLike.of(commentReply, user)).getId();

    }

    public void cancelCommentReplyLike(Long userId, Long commentReplyId) {

        userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        commentReplyRepository.findById(commentReplyId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));

        // 안 눌렀을 경우
        CommentReplyLike commentReplyLike = commentReplyLikeRepository.findByCommentReplyIdAndUserId(commentReplyId, userId).orElseThrow(() -> new RestApiException(ExceptionCode.COMMENT_REPLY_NOT_LIKED));
        commentReplyLikeRepository.delete(commentReplyLike);
    }
}
