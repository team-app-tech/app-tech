package server.apptech.login.domain.repository;

import org.springframework.stereotype.Repository;
import server.apptech.login.domain.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository {

    public void save(RefreshToken refreshToken);
    public Optional<Long> findById(String refreshToken);
    public void deleteById(String refreshToken);
}
