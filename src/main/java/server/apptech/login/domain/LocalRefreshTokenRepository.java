package server.apptech.login.domain;

import org.springframework.stereotype.Repository;
import server.apptech.login.domain.repository.RefreshTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class LocalRefreshTokenRepository implements RefreshTokenRepository {

    private Map<String, Long> map =  new HashMap<>();

    @Override
    public void save(RefreshToken refreshToken) {
        map.put(refreshToken.getRefreshToken(), refreshToken.getUserId());
    }

    @Override
    public Optional<Long> findById(String refreshToken) {
        return Optional.of(map.get(refreshToken));
    }

    @Override
    public void deleteById(String refreshToken) {
        map.remove(refreshToken);
    }
}
