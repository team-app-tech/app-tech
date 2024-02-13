package server.apptech.advertisement;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.apptech.advertisement.domain.Advertisement;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

}
