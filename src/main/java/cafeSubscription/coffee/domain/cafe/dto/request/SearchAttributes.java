package cafeSubscription.coffee.domain.cafe.dto.request;

import lombok.Data;

@Data
public class SearchAttributes {
    private Integer page;
    private Double lat;
    private Double lon;
}
