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

    public Cafe findById(long cafeId) {
        return cafeRepository.findById((int) cafeId).orElse(null); // 카페가 없으면 null 반환
    }

    public void delete(long cafeId) {
        cafeRepository.deleteById((int) cafeId);
    }

    public Cafe update(long cafeId, Cafe updatedCafe) {
        updatedCafe.setCafeId(cafeId);
        return cafeRepository.save(updatedCafe);
    }
}
