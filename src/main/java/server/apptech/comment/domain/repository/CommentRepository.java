package server.apptech.comment.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.apptech.comment.domain.Comment;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.user u left join fetch c.file f where c.advertisement.id = :advertisementId")
    List<Comment> findCommentsByAdvertisementId(@Param("advertisementId") Long advertisementId);

    @Query(value = "select c from Comment c join fetch c.user where c.advertisement.id = :advertisementId order by c.likeCnt desc, c.createdAt")
    List<Comment> findTopPrizeCommentsSortedByLikes(PageRequest pageable, @Param("advertisementId") Long advertisementId);
}
