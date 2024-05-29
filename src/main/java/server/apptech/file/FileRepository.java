package server.apptech.file;

import org.springframework.data.jpa.repository.JpaRepository;
import server.apptech.file.domain.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByUrl(String url);

}
