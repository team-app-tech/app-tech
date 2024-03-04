package server.apptech.login.domain.repository;

import org.springframework.stereotype.Repository;
import server.apptech.login.domain.RefreshToken;

@Repository
public interface RefreshTokenRepository {

    public void save(RefreshToken refreshToken);
}
