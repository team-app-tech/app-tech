package server.apptech.advertisement.domain.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.apptech.advertisement.domain.Advertisement;

import java.time.LocalDateTime;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {


    @Query(value = "select a from Advertisement a where a.startDate >= :now and a.title like %:keyword%")
    Page<Advertisement> findAllWithUpcoming(Pageable pageable, @Param("now") LocalDateTime now, @Param("keyword") String keyword);

    @Query(value = "select a from Advertisement a  where :now between a.startDate and a.endDate and a.title like %:keyword%")
    Page<Advertisement> findAllWithOngoing(Pageable pageable, @Param("now")LocalDateTime now , @Param("keyword") String keyword);

    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword%")
    Page<Advertisement> findAllWithFinished(Pageable pageable, @Param("now")LocalDateTime now , @Param("keyword") String keyword);

    //상금순
    @Query(value = "select a from Advertisement a where a.startDate >= :now and a.title like %:keyword% order by a.totalPrice DESC ")
    Page<Advertisement> findByUpComingOrderByPrizeDesc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate and a.title like %:keyword% order by a.totalPrice DESC ")
    Page<Advertisement> findByOnGoingOrderByPrizeDesc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.totalPrice DESC ")
    Page<Advertisement> findByFinishedOrderByPrizeDesc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);


    //시작순
    @Query(value = "select a from Advertisement a where a.startDate >= :now and a.title like %:keyword% order by a.startDate ASC ")
    Page<Advertisement> findByUpComingOrderByStartDateAsc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate and a.title like %:keyword% order by a.startDate ASC ")
    Page<Advertisement> findByOnGoingOrderByStartDateAsc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.startDate ASC ")
    Page<Advertisement> findByFinishedOrderByStartDateAsc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);

    //마감순
    @Query(value = "select a from Advertisement a where a.startDate >= :now and a.title like %:keyword% order by a.endDate ASC ")
    Page<Advertisement> findByUpComingOrderByEndDateAsc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate and a.title like %:keyword% order by a.endDate ASC ")
    Page<Advertisement> findByOnGoingOrderByEndDateAsc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.endDate ASC ")
    Page<Advertisement> findByFinishedOrderByEndDateAsc(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);

    //조회순
    @Query(value = "select a from Advertisement a where a.startDate >= :now and a.title like %:keyword% order by a.viewCnt desc ")
    Page<Advertisement> findByUpComingOrderByViewCnt(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate and a.title like %:keyword% order by a.viewCnt desc ")
    Page<Advertisement> findByOngoingOrderByViewCnt(PageRequest pageable, @Param("now")LocalDateTime now, @Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.viewCnt desc ")
    Page<Advertisement> findByFinishedOrderByViewCnt(PageRequest pageable, @Param("now")LocalDateTime now,@Param("keyword")String keyword );

    //댓글순
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.commentCnt desc ")
    Page<Advertisement> findByUpComingOrderByCommentCnt(PageRequest pageable, @Param("now")LocalDateTime now,@Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate and a.title like %:keyword% order by a.commentCnt desc ")
    Page<Advertisement> findByOngoingOrderCommentCnt(PageRequest pageable,  @Param("now")LocalDateTime now,@Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.commentCnt desc ")
    Page<Advertisement> findByFinishedOrderCommentCnt(PageRequest pageable,  @Param("now")LocalDateTime now,@Param("keyword")String keyword);

    //좋아요순
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.likeCnt desc ")
    Page<Advertisement> findByUpComingOrderByLikeCnt(PageRequest pageable,  @Param("now")LocalDateTime now,@Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate and a.title like %:keyword% order by a.likeCnt desc ")
    Page<Advertisement> findByOngoingOrderByLikeCnt(PageRequest pageable,  @Param("now")LocalDateTime now,@Param("keyword")String keyword);
    @Query(value = "select a from Advertisement a where a.endDate <= :now and a.title like %:keyword% order by a.likeCnt desc ")
    Page<Advertisement> findByFinishedOrderByLikeCnt(PageRequest pageable,  @Param("now")LocalDateTime now,@Param("keyword")String keyword);
}

