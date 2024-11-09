package cafeSubscription.coffee.domain.custom;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ImageTypeConverter implements AttributeConverter<ImageType, String> {

    @Override
    public String convertToDatabaseColumn(ImageType status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public ImageType convertToEntityAttribute(String value) {
        return value == null ? null : ImageType.fromEventStatus(value);
    }
}
