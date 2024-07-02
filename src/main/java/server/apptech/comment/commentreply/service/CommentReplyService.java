package server.apptech.comment.commentreply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.auth.AuthUser;
import server.apptech.auth.Authority;
import server.apptech.comment.commentreply.commentreplylike.CommentReplyLike;
import server.apptech.comment.commentreply.commentreplylike.repository.CommentReplyLikeRepository;
import server.apptech.comment.commentreply.domain.CommentReply;
import server.apptech.comment.commentreply.domain.repository.CommentReplyRepository;
import server.apptech.comment.commentreply.dto.CommentReplyCreateRequest;
import server.apptech.comment.commentreply.dto.CommentReplyResponse;
import server.apptech.comment.commentreply.dto.PageCommentReplyResponse;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.comment.commentreply.dto.CommentReplyUpdateRequest;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReplyService {

    private final CommentReplyRepository commentReplyRepository;
    private final CommentRepository commentRepository;
    private final CommentReplyLikeRepository commentReplyLikeRepository;
    private final UserRepository userRepository;
    public Long createCommentReply(Long userId, Long commentId, CommentReplyCreateRequest commentReplyCreateRequest) {

        User user = userRepository.findById(userId).orElseThrow(()-> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));
        return commentReplyRepository.save(CommentReply.of(user, comment, commentReplyCreateRequest)).getId();
    }

    public PageCommentReplyResponse getCommentRepliesByCommentId(AuthUser user, Long commentId) {
        List<CommentReply> commentReplies = commentReplyRepository.findCommentRepliesByCommentId(commentId);

        List<Long> commentReplyIds = commentReplies.stream()
                .map(CommentReply::getId)
                .collect(Collectors.toList());


        List<CommentReplyLike> commentReplyLikes = (user.getUserAuthority() != Authority.ROLE_VISITOR) ?
                commentReplyLikeRepository.findByUserIdAndCommentReplyIdIn(user.getUserId(), commentReplyIds) :
                Collections.emptyList();

        Map<Long, Boolean> likeMap = commentReplyLikes.stream()
                .collect(Collectors.toMap(
                        like -> like.getCommentReply().getId(),
                        like -> true,
                        (v1, v2) -> v1
                ));
        return PageCommentReplyResponse.of(commentReplies.stream().map(commentReply -> {
            CommentReplyResponse commentReplyResponse = CommentReplyResponse.of(commentReply);
            commentReplyResponse.setLiked(likeMap.getOrDefault(commentReply.getId(), false));
            return commentReplyResponse;
        }).collect(Collectors.toList()));
    }

    public void updateCommentReply(Long userId, Long commentReplyId, CommentReplyUpdateRequest commentReplyUpdateRequest) {

        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT_REPLY));

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
        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT_REPLY));
        validateUserAccess(userId, commentReply);
        commentReplyRepository.delete(commentReply);
    }
}
