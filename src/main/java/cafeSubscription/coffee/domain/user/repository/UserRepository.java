package cafeSubscription.coffee.domain.user.repository;

import cafeSubscription.coffee.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
