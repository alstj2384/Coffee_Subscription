package cafeSubscription.coffee.domain.menu.service;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.menu.entity.Menu;
import cafeSubscription.coffee.domain.menu.repository.MenuRepository;
import cafeSubscription.coffee.domain.menu.dto.request.MenuCreateDto;
import cafeSubscription.coffee.domain.menu.dto.request.MenuUpdateDto;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import cafeSubscription.coffee.global.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final FileStore fileStore;



    @Transactional
    // Menu Entity를 생성하는 메서드
    public Menu create(Long userId, Long cafeId, MenuCreateDto dto) throws IOException {
        log.info("[MenuCreate] Service 요청 수행, dto : {}", dto.toString());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));

        if(user.getCafe() == null){
            log.info("[MenuCreate] 카페를 개설하지 않은 유저");
            throw new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg());
        }

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg()));

        // owner가 관리하는 CafeId와 요청 cafeId 일치 여부 확인
        if(!user.getCafe().getCafeId().equals(cafeId)){
            log.info("[MenuCreate] 카페 메뉴에 접근 권한이 없음");
            throw new IllegalArgumentException(ErrorCode.UNAUTHORIZED_ACCESS.getMsg());
        }


        // Menu Entity 생성
        Menu menu = Menu.builder()
                .menuName(dto.getMenuName())
                .menuInfo(dto.getMenuInfo())
                .mPrice(dto.getMPrice())
                .cafe(cafe)
                .build();
        Menu save = menuRepository.save(menu);


        fileStore.storeFiles(save, Menu.class, dto.getImageFiles());

        log.info("[MenuCreate] Service 수행 완료");
        return save;
    }


    @Transactional
    public Menu update(Long userId, Long menuId, MenuUpdateDto dto) throws IOException {
        log.info("[MenuUpdate] Service 요청 수행, dto : {}", dto.toString());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));

        if(user.getCafe() == null){
            log.info("[MenuCreate] 카페를 개설하지 않은 유저");
            throw new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg());
        }

        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.MENU_NOT_FOUND.getMsg()));

        if(!user.getCafe().getCafeId().equals(menu.getCafe().getCafeId())){
            log.info("[MenuCreate] 카페 메뉴에 접근 권한이 없음");
            throw new IllegalArgumentException(ErrorCode.UNAUTHORIZED_ACCESS.getMsg());
        }


        // 메뉴 이름, 설명, 가격 수정
        menu.updateMenu(dto);


        fileStore.storeFiles(menu, Menu.class, dto.getImageFiles());
        dto.getDeleteFiles().forEach(l -> fileStore.deleteFile(l));


        log.info("[MenuUpdate] Service 수행 완료");

        return menu;
    }

    @Transactional
    public Menu delete(Long userId, Long menuId) {
        log.info("[MenuDelete] Service 요청 수행, userId = {}, menuId = {}", userId, menuId);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));

        if(user.getCafe() == null){
            log.info("[MenuDelete] 카페를 개설하지 않은 유저");
            throw new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg());
        }

        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.MENU_NOT_FOUND.getMsg()));

        if(!user.getCafe().getCafeId().equals(menu.getCafe().getCafeId())){
            log.info("[MenuDelete] 카페 메뉴에 접근 권한이 없음");
            throw new IllegalArgumentException(ErrorCode.UNAUTHORIZED_ACCESS.getMsg());
        }

        menuRepository.delete(menu);
        log.info("[MenuDelete] Service 요청 수행");
        return menu;
    }

    @Transactional
    public List<Menu> getList(Long cafeId){
        cafeRepository.findById(cafeId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg()));

        return menuRepository.findAllByCafeCafeId(cafeId).orElse(null);
    }
}
