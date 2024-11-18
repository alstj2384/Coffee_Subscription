package cafeSubscription.coffee.domain.cafe.entity;

import cafeSubscription.coffee.domain.image.entity.Image;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import cafeSubscription.coffee.domain.review.entity.Review;
import cafeSubscription.coffee.domain.user.entity.Business;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
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

    @Column
    private Integer pin;

    @Column
    private Double cLatitude;

    @Column
    private Double cLongitude;

    @Column(nullable = false)
    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = true)
    private Business business;

    @OneToOne(cascade = CascadeType.PERSIST) // 카페 저장 시 운영시간도 저장하도록 설정
    @JoinColumn(name = "operating_hour_id")
    private OperatingHour operatingHour;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Builder
    public Cafe(String cafeName, String description, Integer pin, String location, OperatingHour operatingHour) {
        this.cafeName = cafeName;
        this.description = description;
        this.pin = pin;
        this.location = location;
        this.operatingHour = operatingHour;
    }

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
