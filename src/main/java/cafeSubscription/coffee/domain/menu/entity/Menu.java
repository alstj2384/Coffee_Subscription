package cafeSubscription.coffee.domain.menu.entity;


import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.menu.dto.request.MenuUpdateDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer mPrice;

    @Column(nullable = true)
    private String menuInfo;

    public void updateMenu(MenuUpdateDto dto){
        if(dto.getMenuInfo() != null) this.menuInfo = dto.getMenuInfo();
        if(dto.getMenuName() != null) this.menuName = dto.getMenuName();
        if(dto.getMPrice() != null) this.mPrice = dto.getMPrice();
    }

}
