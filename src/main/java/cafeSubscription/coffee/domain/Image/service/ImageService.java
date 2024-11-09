package cafeSubscription.coffee.domain.Image.service;

import cafeSubscription.coffee.domain.Image.DTO.AddImageRequest;
import cafeSubscription.coffee.domain.Image.Image;
import cafeSubscription.coffee.domain.Image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Transactional
    public Image save(AddImageRequest request) {
        return imageRepository.save(request.toEntity());
    }
}
