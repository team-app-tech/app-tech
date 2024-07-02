package server.apptech.comment.commentlike.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;
import server.apptech.comment.commentlike.domain.CommentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
    List<CommentLike> findByUserIdAndCommentIdIn(Long userId, List<Long> commentsId);

}
