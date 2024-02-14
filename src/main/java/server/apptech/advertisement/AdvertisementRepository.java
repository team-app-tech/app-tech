package server.apptech.advertisement;


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


    @Query(value = "select a from Advertisement a where a.startDate >= :now")
    Page<Advertisement> findAllWithUpcoming(Pageable pageable, @Param("now") LocalDateTime now);

    @Query(value = "select a from Advertisement a  where :now between a.startDate and a.endDate")
    Page<Advertisement> findAllWithOngoing(Pageable pageable, @Param("now")LocalDateTime now);

    @Query(value = "select a from Advertisement a where a.endDate <= :now")
    Page<Advertisement> findAllWithFinished(Pageable pageable, @Param("now")LocalDateTime now);

    //상금순
    @Query(value = "select a from Advertisement a where a.startDate >= :now order by a.totalPrice DESC ")
    Page<Advertisement> findByUpComingOrderByPrizeDesc(PageRequest pageable, LocalDateTime now);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate order by a.totalPrice DESC ")
    Page<Advertisement> findByOnGoingOrderByPrizeDesc(PageRequest pageable, LocalDateTime now);
    @Query(value = "select a from Advertisement a where a.endDate <= :now order by a.totalPrice DESC ")
    Page<Advertisement> findByFinishedOrderByPrizeDesc(PageRequest pageable, LocalDateTime now);


    //시작순
    @Query(value = "select a from Advertisement a where a.startDate >= :now order by a.startDate ASC ")
    Page<Advertisement> findByUpComingOrderByStartDateAsc(PageRequest pageable, LocalDateTime now);

    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate order by a.startDate ASC ")
    Page<Advertisement> findByOnGoingOrderByStartDateAsc(PageRequest pageable, LocalDateTime now);

    @Query(value = "select a from Advertisement a where a.endDate <= :now order by a.startDate ASC ")
    Page<Advertisement> findByFinishedOrderByStartDateAsc(PageRequest pageable, LocalDateTime now);

    //마감순
    @Query(value = "select a from Advertisement a where a.startDate >= :now order by a.endDate ASC ")
    Page<Advertisement> findByUpComingOrderByEndDateAsc(PageRequest pageable, LocalDateTime now);
    @Query(value = "select a from Advertisement a where :now between a.startDate and a.endDate order by a.endDate ASC ")
    Page<Advertisement> findByOnGoingOrderByEndDateAsc(PageRequest pageable, LocalDateTime now);
    @Query(value = "select a from Advertisement a where a.endDate <= :now order by a.endDate ASC ")
    Page<Advertisement> findByFinishedOrderByEndDateAsc(PageRequest pageable, LocalDateTime now);

    //댓글순
//    @Query(value = "select a from Advertisement a join fetch a.comments where a.startDate >= :now order by a.comments.size desc ")
//    Page<Advertisement> findByUpComingOrderByCommentCnt(PageRequest pageable, LocalDateTime now);
//
//    @Query(value = "select a from Advertisement a join fetch a.comments where :now between a.startDate and a.endDate order by a.comments.size desc ")
//    Page<Advertisement> findByOngoingOrderByCommentCnt(PageRequest pageable, LocalDateTime now);
//
//    @Query(value = "select a from Advertisement a join fetch a.comments where a.endDate <= :now order by a.comments.size desc ")
//    Page<Advertisement> findByFinishedOrderByCommentsCnt(PageRequest pageable, LocalDateTime now);
//
//    //좋아요순
//    @Query(value = "select a from Advertisement a join fetch a.comments where a.startDate >= :now order by a.advertisementLikes.size desc ")
//    Page<Advertisement> findByUpComingOrderByLikeCnt(PageRequest pageable, LocalDateTime now);
//    @Query(value = "select a from Advertisement a join fetch a.comments where :now between a.startDate and a.endDate order by a.advertisementLikes.size desc ")
//    Page<Advertisement> findByOngoingOrderByLikeCnt(PageRequest pageable, LocalDateTime now);
//
//    @Query(value = "select a from Advertisement a join fetch a.comments where a.endDate <= :now order by a.advertisementLikes.size desc ")
//    Page<Advertisement> findByFinishedOrderByLikeCnt(PageRequest pageable, LocalDateTime now);

    //조회순
    @Query(value = "select a from Advertisement a join fetch a.comments where a.startDate >= :now order by a.viewCnt desc ")
    Page<Advertisement> findByUpComingOrderByViewCnt(PageRequest pageable, LocalDateTime now);

    @Query(value = "select a from Advertisement a join fetch a.comments where :now between a.startDate and a.endDate order by a.viewCnt desc ")
    Page<Advertisement> findByOngoingOrderByViewCnt(PageRequest pageable, LocalDateTime now);

    @Query(value = "select a from Advertisement a join fetch a.comments where a.endDate <= :now order by a.viewCnt desc ")
    Page<Advertisement> findByFinishedOrderByViewCnt(PageRequest pageable, LocalDateTime now);

}

