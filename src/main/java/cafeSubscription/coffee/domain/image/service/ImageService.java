package cafeSubscription.coffee.domain.image.service;

import cafeSubscription.coffee.domain.image.DTO.AddImageRequest;
import cafeSubscription.coffee.domain.image.entity.Image;
import cafeSubscription.coffee.domain.image.repository.ImageRepository;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
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

    public Image save2(Image image) {
        return imageRepository.save(image);
    }
}
