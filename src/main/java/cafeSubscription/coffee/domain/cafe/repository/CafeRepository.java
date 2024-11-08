package cafeSubscription.coffee.domain.cafe.repository;

import cafeSubscription.coffee.domain.cafe.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CafeRepository extends JpaRepository<Cafe, Integer> {
}