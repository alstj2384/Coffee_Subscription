package cafeSubscription.coffee.domain.image.repository;

import cafeSubscription.coffee.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImageId(Long cafeId);
}
