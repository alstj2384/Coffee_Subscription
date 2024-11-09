package cafeSubscription.coffee.domain.cafe.repository;

import cafeSubscription.coffee.domain.cafe.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CouponRepository  extends JpaRepository<Coupon, Integer> {

    @Query(value = "SELECT COUNT(*) FROM coupon WHERE cafe_id = :cafeId AND used = true AND DATE(used_time) = :date", nativeQuery = true)
    int countDailyUsage(@Param("cafeId") Long cafeId, @Param("date") LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM coupon WHERE cafe_id = :cafeId AND used = true AND used_time BETWEEN :startDate AND :endDate", nativeQuery = true)
    int countUsageBetweenDates(@Param("cafeId") Long cafeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

