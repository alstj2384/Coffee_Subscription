package cafeSubscription.coffee.global.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
