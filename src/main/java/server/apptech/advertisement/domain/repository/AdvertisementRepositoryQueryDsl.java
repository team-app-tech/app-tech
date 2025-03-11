package server.apptech.advertisement.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.type.EventStatus;
import server.apptech.advertisement.domain.type.SortOption;

import java.time.LocalDateTime;

public interface AdvertisementRepositoryQueryDsl {


    Page<Advertisement> findAdvertisements(PageRequest pageable, EventStatus eventStatus, SortOption sortOption, @Param("now") LocalDateTime now, @Param("keyword")String keyword);


}
