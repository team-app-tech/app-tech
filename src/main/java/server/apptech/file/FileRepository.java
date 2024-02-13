package server.apptech.file;

import org.springframework.data.jpa.repository.JpaRepository;
import server.apptech.file.domain.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
