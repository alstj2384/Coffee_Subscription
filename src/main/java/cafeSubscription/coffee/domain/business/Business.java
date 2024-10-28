package cafeSubscription.coffee.domain.business;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer businessId;

    // TODO User 연관시키기

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String bName;

    @Column(nullable = false)
    private Boolean verified;

    @Column(nullable = false)
    private String bankAccount;

    @Column(nullable = false)
    private Date openingDate;

}
