package server.apptech.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.comment.dto.CommentResponse;
import server.apptech.comment.dto.CommentUpdateRequest;
import server.apptech.comment.dto.PageCommentResponse;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.file.FileRepository;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CommentRepository commentRepository;
    private final FileRepository fileRepository;

    public Long createComment(Long userId, Long advertisementId, CommentCreateRequest commentCreateRequest) {

        //user 가져오기
        User user = userRepository.findById(userId).orElseThrow(()-> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        //comment 가져오기
        Comment comment = Comment.of(commentCreateRequest,user);

        //advertisement 가져오기
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID));
        comment.setAdvertisement(advertisement);

        //file 설정
        setFileForComment(commentCreateRequest.getFileId(), comment);
        return commentRepository.save(comment).getId();
    }

    public PageCommentResponse getCommentsByAdvertisementId(Long advertisementId) {

        List<Comment> comments = commentRepository.findCommentsByAdvertisementId(advertisementId);
        return PageCommentResponse.of(comments.stream().map(CommentResponse::of).collect(Collectors.toList()));
    }

    public Long updateComment(Long userId, Long commentId, CommentUpdateRequest commentUpdateRequest) {


        Comment comment = findCommentById(commentId);

        //검증
        validateUserAccess(userId, comment);

        //파일이 삭제되거나, 변경된경우 이전 파일 삭제
        handleFileUpdate(commentUpdateRequest, comment);

        // 변경
        comment.updateComment(commentUpdateRequest);
        return commentRepository.save(comment).getId();
    }

    private void setFileForComment(Long fileId, Comment comment) {
        if (fileId != null) {
            comment.setFile(fileRepository.findById(fileId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_FILE)));
        }
    }

    private void handleFileUpdate(CommentUpdateRequest commentUpdateRequest, Comment comment) {
        if(comment.getFile() != null && (commentUpdateRequest.getFileId() == null || comment.getFile().getId() != commentUpdateRequest.getFileId()) ){
            Long deleteCommentId = comment.getFile().getId();
            comment.removeFile();
            fileRepository.deleteById(deleteCommentId);
        }
        setFileForComment(commentUpdateRequest.getFileId(), comment);
    }

    private static void validateUserAccess(Long userId, Comment comment) {
        if(comment.getUser().getId() != userId){
            throw new AuthException(ExceptionCode.UNAUTHORIZED_USER_ACCESS);
        }
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = findCommentById(commentId);
        validateUserAccess(userId, comment);
        commentRepository.deleteById(commentId);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));
    }
}
