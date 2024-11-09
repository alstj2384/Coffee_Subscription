package cafeSubscription.coffee.domain.operatingHour.repository;

import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatingHourRepository extends JpaRepository<OperatingHour, Integer> {
}
