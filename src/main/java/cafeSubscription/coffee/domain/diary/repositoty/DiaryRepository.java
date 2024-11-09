package cafeSubscription.coffee.domain.diary.repositoty;

import cafeSubscription.coffee.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    int countByCafe_CafeId(Long cafeId);
}