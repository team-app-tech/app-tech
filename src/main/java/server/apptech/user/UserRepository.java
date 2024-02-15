package server.apptech.user;

import org.springframework.data.jpa.repository.JpaRepository;
import server.apptech.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
