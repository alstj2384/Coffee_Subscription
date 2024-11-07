package cafeSubscription.coffee.domain.cafe.repository;

import cafeSubscription.coffee.domain.image.entity.ImageAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageAll, Long> {
    Optional<ImageAll> findByImageId(Long cafeId);
}