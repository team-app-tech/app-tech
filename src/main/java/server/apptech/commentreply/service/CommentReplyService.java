package server.apptech.commentreply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.commentreply.domain.CommentReply;
import server.apptech.commentreply.domain.repository.CommentReplyRepository;
import server.apptech.commentreply.dto.CommentReplyCreateRequest;
import server.apptech.commentreply.dto.CommentReplyResponse;
import server.apptech.commentreply.dto.CommentReplyUpdateRequest;
import server.apptech.commentreply.dto.PageCommentReplyResponse;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReplyService {

    private final CommentReplyRepository commentReplyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    public Long createCommentReply(Long userId, Long commentId, CommentReplyCreateRequest commentReplyCreateRequest) {

        User user = userRepository.findById(userId).orElseThrow(()-> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));
        return commentReplyRepository.save(CommentReply.of(user, comment, commentReplyCreateRequest)).getId();
    }

    public PageCommentReplyResponse getCommentRepliesByCommentId(Long commentId) {
        List<CommentReply> commentReplies = commentReplyRepository.findCommentRepliesByCommentId(commentId);
        return PageCommentReplyResponse.of(commentReplies.stream().map(commentReply -> CommentReplyResponse.of(commentReply)).collect(Collectors.toList()));
    }

    public void updateCommentReply(Long userId, Long commentReplyId, CommentReplyUpdateRequest commentReplyUpdateRequest) {

        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(() -> new RuntimeException("NOT_FOUND_COMMENT_REPLY"));

        //검증
        validateUserAccess(userId, commentReply);

        // 변경
        commentReply.updateComment(commentReplyUpdateRequest);
    }

    private boolean validateUserAccess(Long userId, CommentReply commentReplyId){
        if(userId != commentReplyId.getUser().getId()){
            throw new AuthException(ExceptionCode.UNAUTHORIZED_USER_ACCESS);
        }
        return true;
    }

    public void deleteCommentReply(Long userId, Long commentReplyId) {
        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(() -> new RuntimeException("NOT_FOUND_COMMENT_REPLY"));
        validateUserAccess(userId, commentReply);
        commentReplyRepository.delete(commentReply);
    }
}
