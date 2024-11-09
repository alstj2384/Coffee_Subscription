package cafeSubscription.coffee.global.file;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.menu.entity.Menu;
import cafeSubscription.coffee.domain.review.entity.Review;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.global.file.dto.ImageCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    @Enumerated(value = EnumType.STRING)
    private ImageCategory imageCategory;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public void setCafeId(Cafe cafe) {
        this.cafe = cafe;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public void setMenu_id(Menu menu) {
        this.menu = menu;
    }

    public void setReview_id(Review review) {
        this.review = review;
    }

    public void setDiary_id(Diary diary) {
        this.diary = diary;
    }

    public <T> void updateId(Object object, Class<T> type) {

        T objectByCategory = getObjectByCategory(object, type);

        matchingCategories(object, objectByCategory);


    }

    private <T> void matchingCategories(Object object, T objectByCategory) {
        if (objectByCategory.getClass() == Cafe.class) {
            this.cafe = (Cafe) object;
            this.imageCategory = ImageCategory.CAFE;
        }
        if (objectByCategory.getClass() == Menu.class) {
            this.menu = (Menu) object;
            this.imageCategory = ImageCategory.MENU;
        }

        if (objectByCategory.getClass() == Review.class) {
            this.review = (Review) object;
            this.imageCategory = ImageCategory.REVIEW;
        }

        if (objectByCategory.getClass() == Diary.class) {
            this.diary = (Diary) object;
            this.imageCategory = ImageCategory.DIARY;
        }

        if (objectByCategory.getClass() == User.class){
            this.user = (User) object;
            this.imageCategory = ImageCategory.USER;
        }
    }

    @SuppressWarnings("unchekced")
    public <T> T getObjectByCategory(Object object, Class<T> type){
        if(type.isInstance(object)){
            log.debug("object : " + type.getName());
            return (T) object;
        }else{
            throw new IllegalArgumentException("[getObjectByCategory] object와 요청 class type이 일치하지 않습니다");
        }
    }
}
