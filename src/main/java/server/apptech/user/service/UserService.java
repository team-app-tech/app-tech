package server.apptech.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;
import server.apptech.advertisement.advertisementlike.domain.repository.AdvertisementLikeRepository;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdResponse;
import server.apptech.auth.AuthUser;
import server.apptech.auth.Authority;
import server.apptech.comment.commentlike.domain.CommentLike;
import server.apptech.comment.commentlike.domain.repository.CommentLikeRepository;
import server.apptech.comment.commentreply.commentreplylike.CommentReplyLike;
import server.apptech.comment.commentreply.commentreplylike.repository.CommentReplyLikeRepository;
import server.apptech.comment.commentreply.domain.CommentReply;
import server.apptech.comment.commentreply.domain.repository.CommentReplyRepository;
import server.apptech.comment.commentreply.dto.CommentReplyResponse;
import server.apptech.comment.commentreply.dto.PageCommentReplyResponse;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.comment.dto.CommentResponse;
import server.apptech.comment.dto.PageCommentResponse;
import server.apptech.file.FileService;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.user.MyCommentResponse;
import server.apptech.user.PageMyCommentResponse;
import server.apptech.user.controller.NickNameUpdateRequest;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementLikeRepository advertisementLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final CommentReplyLikeRepository commentReplyLikeRepository;
    private final FileService fIleUploadService;

    public User createUser(OauthUserInfo oauthUserInfo){
        return userRepository.save(User.of(oauthUserInfo));
    }

    public Optional<User> findByAuthId(String authId){
        return userRepository.findByAuthId(authId);
    }

    public User findByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
    }

    public Long updateUserNickName(Long userId, NickNameUpdateRequest nickNameUpdateRequest) {
        System.out.println("userId: " + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        if(userRepository.existsByNickName(nickNameUpdateRequest.getNickName())){
            throw new RestApiException(ExceptionCode.ALREADY_EXIST_NICKNAME);
        }
        user.changeNickName(nickNameUpdateRequest.getNickName());
        return userRepository.save(user).getId();
    }

    public Long updateProfileImage(Long userId, MultipartFile multipartFile) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        if(user.getProfileImageUrl() != null){ //현재 이미지가 없지 않으면
            String currentProfileImageUrl = user.getProfileImageUrl();
            fIleUploadService.deleteFile(currentProfileImageUrl);
        }
        user.updateProfileImageUrl(fIleUploadService.saveFile(multipartFile).getUrl());
        return userRepository.save(user).getId();
    }

    public List<AdResponse> getMyAdvertisements(AuthUser user) {

        List<Advertisement> myAdvertisements = advertisementRepository.findWithUserByUserId(user.getUserId());

        List<Long> advertisementIds = myAdvertisements.stream()
                .map(Advertisement::getId)
                .collect(Collectors.toList());

        System.out.println(myAdvertisements.size());

        List<AdvertisementLike> likes = (user.getUserAuthority() != Authority.ROLE_VISITOR) ?
                advertisementLikeRepository.findByUserIdAndAdvertisementIdIn(user.getUserId(), advertisementIds) :
                Collections.emptyList();

        // 좋아요 정보를 Map으로 변환
        Map<Long, Boolean> likeMap = likes.stream()
                .collect(Collectors.toMap(
                        like -> like.getAdvertisement().getId(),
                        like -> true,
                        (v1, v2) -> v1
                ));
        return myAdvertisements.stream().map(ad -> {
            AdResponse adResponse = AdResponse.of(ad);
            adResponse.setIsLiked(likeMap.getOrDefault(ad.getId(), false));
            return adResponse;
        }).collect(Collectors.toList());
    }

    public List<MyCommentResponse> getMyComments(AuthUser user) {

        if(user.getUserAuthority() == Authority.ROLE_VISITOR){
            throw new AuthException(ExceptionCode.UNAUTHORIZED_USER_ACCESS);
        }

        List<Comment> comments = commentRepository.findCommentsByUserId(user.getUserId());

        List<Long> commentIds = comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toList());


        List<CommentLike> commentLikes = (user.getUserAuthority() != Authority.ROLE_VISITOR) ?
                commentLikeRepository.findByUserIdAndCommentIdIn(user.getUserId(), commentIds) :
                Collections.emptyList();

        Map<Long, Boolean> likeMap = commentLikes.stream()
                .collect(Collectors.toMap(
                        like -> like.getComment().getId(),
                        like -> true,
                        (v1, v2) -> v1
                ));
        return comments.stream().map(comment -> {
            MyCommentResponse commentResponse = MyCommentResponse.of(comment);
            commentResponse.setLiked(likeMap.getOrDefault(comment.getId(), false));
            return commentResponse;
        }).collect(Collectors.toList());
    }


    public List<CommentReplyResponse> getMyCommentReplies(AuthUser user) {

        System.out.println(user.getUserId());
        if(user.getUserAuthority() == Authority.ROLE_VISITOR){
            throw new AuthException(ExceptionCode.UNAUTHORIZED_USER_ACCESS);
        }

        List<CommentReply> commentReplies = commentReplyRepository.findCommentRepliesByUserId(user.getUserId());

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
        return commentReplies.stream().map(commentReply -> {
            CommentReplyResponse commentReplyResponse = CommentReplyResponse.of(commentReply);
            commentReplyResponse.setLiked(likeMap.getOrDefault(commentReply.getId(), false));
            return commentReplyResponse;
        }).collect(Collectors.toList());
    }
}
