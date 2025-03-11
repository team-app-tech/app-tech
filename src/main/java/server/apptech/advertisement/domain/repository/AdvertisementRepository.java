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
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, AdvertisementRepositoryQueryDsl{

    @Query(value = "select a from Advertisement a join fetch a.user u where a.id = :id")
    Optional<Advertisement> findWithUserById(@Param("id") Long id);

}

