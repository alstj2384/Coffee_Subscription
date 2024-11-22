package cafeSubscription.coffee.global.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@Tag(name="이미지 조회 API")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileStore fileStore;

    @Operation(summary = "이미지 조회 API", description = "일기, 메뉴, 리뷰에 이미지 조회->이미지 조회 통합 api")
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
