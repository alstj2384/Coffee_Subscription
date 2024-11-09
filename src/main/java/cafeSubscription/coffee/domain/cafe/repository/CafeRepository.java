package cafeSubscription.coffee.domain.cafe.repository;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByCafeId(Long cafeId);
}
