package server.apptech.comment.commentreply.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.apptech.comment.commentreply.domain.CommentReply;

import java.util.List;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

    @Query(value = "select c from CommentReply c join fetch c.user u where c.comment.id = :commentId")
    List<CommentReply> findCommentRepliesByCommentId(@Param("commentId") Long commentId);
}

//left join fetch c.commentLikes cl