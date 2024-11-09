package cafeSubscription.coffee.domain.user.repository;

import cafeSubscription.coffee.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByNickName(String nickName);
    Optional<User> findByOauthProviderId(String oauthProviderId);
}
