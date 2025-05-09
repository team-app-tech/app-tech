package server.apptech.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.apptech.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthId(String authId);

    boolean existsByNickName(String nickName);
}
