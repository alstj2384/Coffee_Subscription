package cafeSubscription.coffee.domain.menu.controller;

import cafeSubscription.coffee.domain.menu.entity.Menu;
import cafeSubscription.coffee.domain.menu.dto.request.MenuCreateDto;
import cafeSubscription.coffee.domain.menu.dto.request.MenuUpdateDto;
import cafeSubscription.coffee.domain.menu.dto.response.MenuViewDto;
import cafeSubscription.coffee.domain.menu.mapper.MenuMapper;
import cafeSubscription.coffee.domain.menu.service.MenuService;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
@Tag(name = "카페 메뉴 CRUD API",description = "카페메뉴 삭제, 조회, 수정 , 추가 api입니다")
@Slf4j
public class MenuController {
    private final MenuService menuService;
    private final RegisterRepository registerRepository;

    @Operation(summary = "카페메뉴 추가 api")
    @PreAuthorize("hasRole('owner')")
    @PostMapping("/{cafeId}/add")
    public ResponseEntity<MenuViewDto> MenuCreate(@AuthenticationPrincipal User requestUser, @PathVariable Long cafeId, @ModelAttribute MenuCreateDto dto) throws IOException {
        //  user 권한 체크
        log.info("[MenuCreate] 메뉴 생성 요청");


        // 요청 유저 검증
        cafeSubscription.coffee.domain.user.entity.User userEntity = getUserEntity(requestUser);

        Long userId = userEntity.getUserId();

        Menu menu = menuService.create(userId, cafeId, dto);

        MenuViewDto response = MenuMapper.toMenuViewDto(menu);

        log.info("[MenuCreate] 메뉴 생성 요청 응답 완료");
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize("hasRole('owner')")
    @Operation(summary = "카페메뉴 수정 api")
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuViewDto> update(@AuthenticationPrincipal User user, @PathVariable Long menuId, @ModelAttribute MenuUpdateDto dto) throws IOException {
        //  user 권한이 owner 인지 체크
        log.info("[MenuUpdate] 메뉴 수정 요청");

        cafeSubscription.coffee.domain.user.entity.User userEntity = getUserEntity(user);


        Long userId = userEntity.getUserId();
        Menu menu = menuService.update(userId, menuId, dto);

        MenuViewDto response = MenuMapper.toMenuViewDto(menu);
        log.info("[MenuUpdate] 메뉴 수정 요청 응답 완료");

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "카페메뉴 삭제 api")
    @PreAuthorize("hasRole('owner')")
    @DeleteMapping("/{menuId}")
    public ResponseEntity<MenuViewDto> delete(@AuthenticationPrincipal User user, @PathVariable Long menuId){
        log.info("[MenuDelete] 메뉴 삭제 요청");

        // user 권한이 owner 인지 체크
        cafeSubscription.coffee.domain.user.entity.User userEntity = getUserEntity(user);

        Long userId = userEntity.getUserId();

        Menu delete = menuService.delete(userId, menuId);
        MenuViewDto response = MenuMapper.toMenuViewDto(delete);
        log.info("[MenuDelete] 메뉴 삭제 요청 응답 완료");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "카페메뉴 조회 API")
    @PreAuthorize("hasAnyRole('customer', 'owner')")
    @GetMapping("/{cafeId}")
    public ResponseEntity<?> list(@PathVariable Long cafeId){
        log.info("[MenuList] 메뉴 조회 요청");

        List<Menu> list = menuService.getList(cafeId);

        List<MenuViewDto> response = list.stream().map(MenuMapper::toMenuViewDto).toList();
        log.info("[MenuList] 메뉴 조회 요청 응답 완료");
        return ResponseEntity.ok().body(response);
    }

    private cafeSubscription.coffee.domain.user.entity.User getUserEntity(User user){

        String username = user.getUsername(); // 요청 유저의 username 반환

        return  registerRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));
    }
}
