package cafeSubscription.coffee.domain.cafe.service;

import cafeSubscription.coffee.domain.cafe.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;

    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public List<Cafe> findAll() {
        return cafeRepository.findAll();
    }

    //public Optional<Cafe> findById(Integer cafeId) {
    //    return cafeRepository.findById(cafeId);
    //}

    public Cafe findById(Integer cafeId) {
        return cafeRepository.findById(cafeId).orElse(null); // 카페가 없으면 null 반환
    }

    public void delete(Integer cafeId) {
        cafeRepository.deleteById(cafeId);
    }

    public Cafe update(Integer cafeId, Cafe updatedCafe) {
        updatedCafe.setCafeId(cafeId);
        return cafeRepository.save(updatedCafe);
    }
}
