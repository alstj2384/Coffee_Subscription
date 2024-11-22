package cafeSubscription.coffee.domain.image.controller;

import cafeSubscription.coffee.domain.image.DTO.AddImageRequest;
import cafeSubscription.coffee.domain.image.DTO.ImageResponse;
import cafeSubscription.coffee.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name= "이미지 API", description = "이미지 조회 및 저장 API")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "배너 리스트 조회 API")
    @GetMapping("/api/banners") // 배너 리스트 조회
    public ResponseEntity<Map<String, Object>> findAllBanner() {
        List<ImageResponse> imageList = imageService.findAll()
                .stream()
                .map(ImageResponse::new)
                .toList();
        return createResponseEntity(imageList, "배너 조회요청에 성공했습니다.", HttpStatus.OK);
    }

    @Operation(summary = "배너 이미지저장 API")
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
