package cafeSubscription.coffee.domain.cafe;

import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cafeId;

    @Column(nullable = false)
    private String cafeName;

    @Column(nullable = false)
    private String description;

    @Column
    private Integer pin;

    @Column
    private Double cLatitude;

    @Column
    private Double cLongitude;

    @Column(nullable = false)
    private String location;

    @OneToOne(cascade = CascadeType.PERSIST) // 카페 저장 시 운영시간도 저장하도록 설정
    @JoinColumn(name = "operating_hour_id")
    private OperatingHour operatingHour;

    @Builder
    public Cafe(String cafeName, String description, Integer pin, String location, OperatingHour operatingHour) {
        this.cafeName = cafeName;
        this.description = description;
        this.pin = pin;
        this.location = location;
        this.operatingHour = operatingHour;
    }

}
