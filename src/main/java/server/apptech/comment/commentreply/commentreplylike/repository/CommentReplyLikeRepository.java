package server.apptech.comment.commentreply.commentreplylike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.apptech.comment.commentreply.commentreplylike.CommentReplyLike;

import java.util.Optional;

@Repository
public interface CommentReplyLikeRepository extends JpaRepository<CommentReplyLike, Long> {
    boolean existsByCommentReplyIdAndUserId(Long id, Long userId);

    Optional<CommentReplyLike> findByCommentReplyIdAndUserId(Long commentReplyId, Long userId);
}
