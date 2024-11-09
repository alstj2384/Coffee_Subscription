package cafeSubscription.coffee.domain.menu.service;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.menu.entity.Menu;
import cafeSubscription.coffee.domain.menu.repository.MenuRepository;
import cafeSubscription.coffee.domain.menu.dto.request.MenuCreateDto;
import cafeSubscription.coffee.domain.menu.dto.request.MenuUpdateDto;
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
    private final FileStore fileStore;



    @Transactional
    // Menu Entity를 생성하는 메서드
    public Menu create(Long cafeId, MenuCreateDto dto) throws IOException {
        // TODO - 검증
        //  1. user와 cafeId 유효성 검사(null?)
        //  2. user의 businessId와, cafe의 businessId가 일치하는 지 확인(Authti.. 사용 불가로 매개변수로 넘기지 못하는 관계로 추후 수정)

        // !!아래는 임시코드!! cafeService에서 부르는게 더 나을듯 함(예외처리도 cafeService에서)
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg())); // TODO 예외 종류 및 예외 메세지 관리 방법 선정해야함
        // -- 검증 끝

        // Menu Entity 생성
        Menu menu = Menu.builder()
                .menuName(dto.getMenuName())
                .menuInfo(dto.getMenuInfo())
                .mPrice(dto.getMPrice())
                .cafe(cafe)
                .build();
        Menu save = menuRepository.save(menu);

        fileStore.storeFiles(save, Menu.class, dto.getImageFiles());
        return save;
    }


    @Transactional
    public Menu update(Long menuId, MenuUpdateDto dto) {
        // TODO - 서비스 검증
        //  1. user와 menuId 유효성 검사(null?)
        //  2. [user의 businessId로 검색한 cafe객체의 id]와 [menu의 cafeId]가 일치하는지 확인

        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.MENU_NOT_FOUND.getMsg()));
        // -- 검증 끝


        // 메뉴 이름, 설명, 가격 수정
        menu.updateMenu(dto);

        // TODO 이미지 삭제 및 수정


        return menu;
    }

    public Menu delete(Long menuId) {
        // TODO - 서비스 검증

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.MENU_NOT_FOUND.getMsg()));
        menuRepository.delete(menu);
        return menu;
    }

    public List<Menu> getList(Long cafeId){
        cafeRepository.findById(cafeId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg()));

        return menuRepository.findAllByCafeCafeId(cafeId).orElse(null);
    }
}
