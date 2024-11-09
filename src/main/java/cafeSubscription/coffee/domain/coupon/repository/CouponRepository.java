package cafeSubscription.coffee.domain.coupon.repository;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

//    @Query("SELECT c.cafe.cafeId " +
//            "FROM Coupon2 c " +
//            "GROUP BY c.cafe.cafeId " +
//            "HAVING COUNT(c.cafe.cafeId) = (SELECT MAX(counted) FROM (SELECT COUNT(c2.cafe.cafeId) as counted FROM Coupon2 c2 GROUP BY c2.cafe.cafeId) AS counts)")
//    Page<Long> findCafeIdsWithMostCoupons(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM coupon WHERE cafe_id = :cafeId AND used = true AND DATE(used_time) = :date", nativeQuery = true)
    int countDailyUsage(@Param("cafeId") Long cafeId, @Param("date") LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM coupon WHERE cafe_id = :cafeId AND used = true AND used_time BETWEEN :startDate AND :endDate", nativeQuery = true)
    int countUsageBetweenDates(@Param("cafeId") Long cafeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT c.cafe " +
            "FROM Coupon2 c " +
            "GROUP BY c.cafe " +
            "ORDER BY COUNT(c.cafe.cafeId) DESC")
    Page<Cafe> findCafesWithMostCoupons(Pageable pageable);
}
