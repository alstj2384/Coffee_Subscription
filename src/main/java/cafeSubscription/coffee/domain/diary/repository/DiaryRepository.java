package cafeSubscription.coffee.domain.diary.repository;

import cafeSubscription.coffee.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {

}
