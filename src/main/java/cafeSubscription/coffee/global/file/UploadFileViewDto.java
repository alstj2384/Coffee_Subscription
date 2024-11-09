package cafeSubscription.coffee.global.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class UploadFileViewDto {
    private String uploadFileName;
    private String storeFileName;

    public static UploadFileViewDto toDto(UploadFile uploadFile){
        return UploadFileViewDto.builder()
                .uploadFileName(uploadFile.getUploadFileName())
                .storeFileName(uploadFile.getStoreFileName())
                .build();
    }

    public static List<UploadFileViewDto> toDtoList(List<UploadFile> uploadFiles){
         return uploadFiles.stream()
                .map(UploadFileViewDto::toDto)
                 .collect(Collectors.toList());
    }
}
