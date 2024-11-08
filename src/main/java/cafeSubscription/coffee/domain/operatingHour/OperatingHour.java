package cafeSubscription.coffee.domain.operatingHour;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operating_hours")
@Getter
@Setter
@NoArgsConstructor
public class OperatingHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operatingId;

    private String mondayOpen;
    private String mondayClose;
    private String tuesdayOpen;
    private String tuesdayClose;
    private String wednesdayOpen;
    private String wednesdayClose;
    private String thursdayOpen;
    private String thursdayClose;
    private String fridayOpen;
    private String fridayClose;
    private String saturdayOpen;
    private String saturdayClose;
    private String sundayOpen;
    private String sundayClose;

    //@OneToOne(mappedBy = "operatingHour") // Cafe에서 관리하는 연관관계
    //rivate Cafe cafe;

    @Builder
    public OperatingHour(String mondayOpen, String mondayClose, String tuesdayOpen, String tuesdayClose,
                         String wednesdayOpen, String wednesdayClose, String thursdayOpen, String thursdayClose,
                         String fridayOpen, String fridayClose, String saturdayOpen, String saturdayClose,
                         String sundayOpen, String sundayClose) {
        this.mondayOpen = mondayOpen;
        this.mondayClose = mondayClose;
        this.tuesdayOpen = tuesdayOpen;
        this.tuesdayClose = tuesdayClose;
        this.wednesdayOpen = wednesdayOpen;
        this.wednesdayClose = wednesdayClose;
        this.thursdayOpen = thursdayOpen;
        this.thursdayClose = thursdayClose;
        this.fridayOpen = fridayOpen;
        this.fridayClose = fridayClose;
        this.saturdayOpen = saturdayOpen;
        this.saturdayClose = saturdayClose;
        this.sundayOpen = sundayOpen;
        this.sundayClose = sundayClose;
    }
}