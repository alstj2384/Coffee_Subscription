package cafeSubscription.coffee.domain.cafe.entity;

import cafeSubscription.coffee.domain.image.entity.ImageAll;
import cafeSubscription.coffee.domain.review.Review;
import cafeSubscription.coffee.domain.user.entity.Business;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private ImageAll imageAll;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
