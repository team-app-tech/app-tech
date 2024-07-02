package server.apptech.advertisement.advertisementlike.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementLikeRepository extends JpaRepository<AdvertisementLike, Long> {

    boolean existsByAdvertisementIdAndUserId(Long advertisementId, Long userId);
    Optional<AdvertisementLike> findByAdvertisementIdAndUserId(Long advertisementId, Long userId);
    List<AdvertisementLike> findByUserIdAndAdvertisementIdIn(Long userId, List<Long> advertisementIds);

}
