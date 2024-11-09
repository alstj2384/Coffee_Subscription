package cafeSubscription.coffee.global.file;

import cafeSubscription.coffee.global.file.dto.ImageCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;
    private final UploadFileRepository uploadFileRepository;

    public String getFullPath(String filename){
        return fileDir + filename;
    }

    @Transactional
    public <T> void storeFiles(Object object, Class<T> type, List<MultipartFile> multipartFiles) throws IOException {
        if(multipartFiles == null) {
            log.info("이미지 파일 없음");
            return;
        }
        List<UploadFile> storeFileResult = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(object, type, multipartFile));
            }
        }
    }

    @Transactional
    public <T> UploadFile storeFile(Object object, Class<T> type, MultipartFile multipartFile) throws IOException {
        if(multipartFile == null) return null;
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        UploadFile build = UploadFile.builder()
                .storeFileName(storeFilename)
                .uploadFileName(originalFilename)
                .build();

        build.updateId(object, type);
        return uploadFileRepository.save(build);
    }

    public void deleteFile(Long uploadFileId){
        uploadFileRepository.deleteById(uploadFileId);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
