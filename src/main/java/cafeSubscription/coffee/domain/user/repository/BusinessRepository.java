package cafeSubscription.coffee.domain.user.repository;

import cafeSubscription.coffee.domain.user.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {

}
