package cafeSubscription.coffee.domain.cafe.entity;

import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId;

    // TODO Business,OperatingHour 연관시키기

    @Column(nullable = false)
    private String cafeName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer pin;

    @Column(nullable = false)
    private Double cLatitude;

    @Column(nullable = false)
    private Double cLongitude;

    @Column(nullable = false)
    private String location;
}
