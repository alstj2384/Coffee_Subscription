package cafeSubscription.coffee.domain.user.repository;

import cafeSubscription.coffee.domain.user.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByUser_Username(String name); // user.id로 Business 조회
}
