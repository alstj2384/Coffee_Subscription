package cafeSubscription.coffee.domain.diary.repositoty;

import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    int countByCafe_CafeId(Long cafeId);
    //List<Diary> findById(Long diaryId);
    Optional<List<Diary>> findAllByCafeCafeId(Long cafeId);
    void deleteByCafeCafeId(Long cafeId);
}