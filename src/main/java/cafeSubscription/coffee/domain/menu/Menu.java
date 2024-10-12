package cafeSubscription.coffee.domain.menu;


import cafeSubscription.coffee.domain.cafe.Cafe;
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
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cafe cafe;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer mPrice;

    @Column(nullable = true)
    private String menuInfo;

}
