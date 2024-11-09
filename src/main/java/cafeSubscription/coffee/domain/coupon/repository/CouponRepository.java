package cafeSubscription.coffee.domain.coupon.repository;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

//    @Query("SELECT c.cafe.cafeId " +
//            "FROM Coupon c " +
//            "GROUP BY c.cafe.cafeId " +
//            "HAVING COUNT(c.cafe.cafeId) = (SELECT MAX(counted) FROM (SELECT COUNT(c2.cafe.cafeId) as counted FROM Coupon c2 GROUP BY c2.cafe.cafeId) AS counts)")
//    Page<Long> findCafeIdsWithMostCoupons(Pageable pageable);


    @Query("SELECT c.cafe " +
            "FROM Coupon c " +
            "GROUP BY c.cafe " +
            "ORDER BY COUNT(c.cafe.cafeId) DESC")
    Page<Cafe> findCafesWithMostCoupons(Pageable pageable);
}
