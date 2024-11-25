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

    private String mondayBreakStart;
    private String mondayBreakEnd;

    private String tuesdayOpen;
    private String tuesdayClose;
    private String tuesdayBreakStart;
    private String tuesdayBreakEnd;

    private String wednesdayOpen;
    private String wednesdayClose;
    private String wednesdayBreakStart;
    private String wednesdayBreakEnd;

    private String thursdayOpen;
    private String thursdayClose;
    private String thursdayBreakStart;
    private String thursdayBreakEnd;

    private String fridayOpen;
    private String fridayClose;
    private String fridayBreakStart;
    private String fridayBreakEnd;

    private String saturdayOpen;
    private String saturdayClose;
    private String saturdayBreakStart;
    private String saturdayBreakEnd;

    private String sundayOpen;
    private String sundayClose;
    private String sundayBreakStart;
    private String sundayBreakEnd;

    @Builder
    public OperatingHour(String mondayOpen, String mondayClose, String mondayBreakStart, String mondayBreakEnd,
                         String tuesdayOpen, String tuesdayClose, String tuesdayBreakStart, String tuesdayBreakEnd,
                         String wednesdayOpen, String wednesdayClose, String wednesdayBreakStart, String wednesdayBreakEnd,
                         String thursdayOpen, String thursdayClose, String thursdayBreakStart, String thursdayBreakEnd,
                         String fridayOpen, String fridayClose, String fridayBreakStart, String fridayBreakEnd,
                         String saturdayOpen, String saturdayClose, String saturdayBreakStart, String saturdayBreakEnd,
                         String sundayOpen, String sundayClose, String sundayBreakStart, String sundayBreakEnd) {
        this.mondayOpen = mondayOpen;
        this.mondayClose = mondayClose;
        this.mondayBreakStart = mondayBreakStart;
        this.mondayBreakEnd = mondayBreakEnd;

        this.tuesdayOpen = tuesdayOpen;
        this.tuesdayClose = tuesdayClose;
        this.tuesdayBreakStart = tuesdayBreakStart;
        this.tuesdayBreakEnd = tuesdayBreakEnd;

        this.wednesdayOpen = wednesdayOpen;
        this.wednesdayClose = wednesdayClose;
        this.wednesdayBreakStart = wednesdayBreakStart;
        this.wednesdayBreakEnd = wednesdayBreakEnd;

        this.thursdayOpen = thursdayOpen;
        this.thursdayClose = thursdayClose;
        this.thursdayBreakStart = thursdayBreakStart;
        this.thursdayBreakEnd = thursdayBreakEnd;

        this.fridayOpen = fridayOpen;
        this.fridayClose = fridayClose;
        this.fridayBreakStart = fridayBreakStart;
        this.fridayBreakEnd = fridayBreakEnd;

        this.saturdayOpen = saturdayOpen;
        this.saturdayClose = saturdayClose;
        this.saturdayBreakStart = saturdayBreakStart;
        this.saturdayBreakEnd = saturdayBreakEnd;

        this.sundayOpen = sundayOpen;
        this.sundayClose = sundayClose;
        this.sundayBreakStart = sundayBreakStart;
        this.sundayBreakEnd = sundayBreakEnd;
    }
}