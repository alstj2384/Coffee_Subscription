package cafeSubscription.coffee.domain.review.custom;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class KeywordConverter implements AttributeConverter<Keyword, String> {

    @Override
    public String convertToDatabaseColumn(Keyword status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public Keyword convertToEntityAttribute(String value) {
        return value == null ? null : Keyword.fromEventStatus(value);
    }
}
