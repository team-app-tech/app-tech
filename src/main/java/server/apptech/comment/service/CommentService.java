package server.apptech.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.comment.dto.CommentResponse;
import server.apptech.comment.dto.PageCommentResponse;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.file.FileRepository;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        //advertisement 가져오기
        Comment comment = Comment.of(commentCreateRequest,user);

        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID));
        comment.setAdvertisement(advertisement);

        if(commentCreateRequest.getFileId() != null){
            comment.setFile(fileRepository.findById(commentCreateRequest.getFileId()).orElseThrow(() -> new RuntimeException("존재하는 파일이 없음")));
        }

        //부모있으면
        if(commentCreateRequest.getParentId() != null){
            comment.setParent(commentRepository.findById(commentCreateRequest.getParentId()).orElseThrow(() -> new RuntimeException("존재하는 댓글 없음")));
        }
        return commentRepository.save(comment).getId();
    }

    public PageCommentResponse getCommentsByAdvertisementId(Long advertisementId) {

        List<Comment> rootComments = findRootComment(commentRepository.findCommentsByAdvertisementId(advertisementId));
        return PageCommentResponse.of(rootComments.stream().map(CommentResponse::of).collect(Collectors.toList()));
    }

    private List<Comment> findRootComment(List<Comment> flatComments){
        List<Comment> rootComments = new ArrayList<>();
        for (Comment comment : flatComments) {
            if (comment.getParent() == null) { //부모없음
                rootComments.add(comment);
            }
        }
        return rootComments;
    }
}
