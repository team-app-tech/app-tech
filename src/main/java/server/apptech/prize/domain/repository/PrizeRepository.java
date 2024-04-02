package server.apptech.prize.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.apptech.prize.domain.Prize;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
}
