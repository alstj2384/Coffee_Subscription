package cafeSubscription.coffee.domain.menu.controller;

import cafeSubscription.coffee.domain.menu.entity.Menu;
import cafeSubscription.coffee.domain.menu.dto.request.MenuCreateDto;
import cafeSubscription.coffee.domain.menu.dto.request.MenuUpdateDto;
import cafeSubscription.coffee.domain.menu.dto.response.MenuViewDto;
import cafeSubscription.coffee.domain.menu.mapper.MenuMapper;
import cafeSubscription.coffee.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
@Slf4j
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/{cafeId}/add")
    public ResponseEntity<MenuViewDto> create(@PathVariable Long cafeId, @ModelAttribute MenuCreateDto dto) throws IOException {
        // TODO - 컨트롤러 검증
        //  1. user 권한이 owner 인지 체크
        //  2. dto 검증


        Menu menu = menuService.create(cafeId, dto);

        MenuViewDto response = MenuMapper.toMenuViewDto(menu);

        return ResponseEntity.ok().body(response);
    }


    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuViewDto> update(@PathVariable Long menuId, @ModelAttribute MenuUpdateDto dto){
        // TODO - 컨트롤러 검증
        //  1. user 권한이 owner 인지 체크
        //  2. dto 검증


        Menu menu = menuService.update(menuId, dto);

        MenuViewDto response = MenuMapper.toMenuViewDto(menu);

        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/{menuId}")
    public ResponseEntity<MenuViewDto> delete(@PathVariable Long menuId){
        // TODO - 컨트롤러 검증
        //  1. user 권한이 owner 인지 체크

        Menu delete = menuService.delete(menuId);
        MenuViewDto response = MenuMapper.toMenuViewDto(delete);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{cafeId}")
    public ResponseEntity<?> read(@PathVariable Long cafeId){
        List<Menu> list = menuService.getList(cafeId);

        List<MenuViewDto> response = list.stream().map(MenuMapper::toMenuViewDto).toList();
        return ResponseEntity.ok().body(response);
    }
}
