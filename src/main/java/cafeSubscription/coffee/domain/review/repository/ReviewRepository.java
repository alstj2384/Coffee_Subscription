package cafeSubscription.coffee.domain.review.repository;

import cafeSubscription.coffee.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    int countByCafe_CafeId(Long cafeId);
}