package server.apptech.advertisementlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.apptech.advertisementlike.domain.AdvertisementLike;

import java.util.Optional;

@Repository
public interface AdvertisementLikeRepository extends JpaRepository<AdvertisementLike, Long> {

    boolean existsByAdvertisementIdAndUserId(Long advertisementId, Long userId);
    Optional<AdvertisementLike> findByAdvertisementIdAndUserId(Long advertisementId, Long userId);
}
