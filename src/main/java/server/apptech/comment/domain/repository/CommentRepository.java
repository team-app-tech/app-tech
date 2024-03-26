package server.apptech.comment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.apptech.comment.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c left join fetch c.parent p join fetch c.user u left join fetch c.file f left join fetch c.commentLikes cl where c.advertisement.id = :advertisementId")
    List<Comment> findCommentsByAdvertisementId(@Param("advertisementId") Long advertisementId);
}
