package cafeSubscription.coffee.domain.menu.repository;

import cafeSubscription.coffee.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<List<Menu>> findAllByCafeCafeId(Long cafeId);
}
