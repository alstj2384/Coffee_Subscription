package cafeSubscription.coffee.domain.Image.controller;

import cafeSubscription.coffee.domain.Image.DTO.AddImageRequest;
import cafeSubscription.coffee.domain.Image.DTO.ImageResponse;
import cafeSubscription.coffee.domain.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/api/banners") // 배너 리스트 조회
    public ResponseEntity<Map<String, Object>> findAllBanner() {
        List<ImageResponse> imageList = imageService.findAll()
                .stream()
                .map(ImageResponse::new)
                .toList();
        return createResponseEntity(imageList, "배너 조회요청에 성공했습니다.", HttpStatus.OK);
    }


    @PostMapping("/api/manager/store") // 배너 저장
    public ResponseEntity<Map<String, Object>> saveBanner(AddImageRequest request) {
        return createResponseEntity(imageService.save(request), "배너 저장 요청에 성공했습니다.", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> createResponseEntity(Object data, String message, HttpStatus status) { //요청값 확인용
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", status);
        response.put("timeStamp", LocalDateTime.now());
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }
}
