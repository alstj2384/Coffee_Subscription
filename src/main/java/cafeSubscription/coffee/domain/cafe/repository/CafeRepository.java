package cafeSubscription.coffee.domain.cafe.repository;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    @Query("SELECT c FROM Cafe c " +
            "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(c.cLatitude)) * " +
            "cos(radians(c.cLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(c.cLatitude)))) ASC")
    Page<Cafe> findCafesByProximity(@Param("latitude") Double latitude, @Param("longitude") Double longitude, Pageable pageable);


}
