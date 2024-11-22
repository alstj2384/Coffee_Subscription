package cafeSubscription.coffee.domain.subscription.controller;

import cafeSubscription.coffee.domain.subscription.mapper.SubscriptionMapper;
import cafeSubscription.coffee.domain.subscription.service.SubscriptionService;
import cafeSubscription.coffee.domain.subscription.dto.SubscriptionType;
import cafeSubscription.coffee.domain.subscription.dto.response.SubscriptionViewDto;
import cafeSubscription.coffee.domain.subscription.entity.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Stream;

@Tag(name = "구독 API", description = "구독 조회, 구독종류 조회 api입니다")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Operation(summary = "구독 타입 조회 API")
    @GetMapping("/types")
    public ResponseEntity<List<SubscriptionViewDto>> getList(){
        // TODO 유저 확인?

        SubscriptionType[] values = SubscriptionType.values();

        List<SubscriptionViewDto> list = Stream.of(values).map(SubscriptionMapper::toSubscriptionViewDto).toList();


        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "customer 조회 API", description = "사용자가 어떤 구독을 하고있는지 조회api")
    @GetMapping("/{userId}")
    public ResponseEntity<?> createSubscription(@PathVariable Long userId, @RequestParam(value = "type") Long typeId){
        // TODO 결제 확인 이후 요청해야함
        //  유저 검증 필요
        //  이것저것 검증은 의논 후 결정해야할듯

        // 구독 타입 검증
        SubscriptionType type = SubscriptionType.findByTypeId(typeId);


        Subscription subscription = subscriptionService.subscription(userId, type);

        return ResponseEntity.ok().body(subscription);
    }
}
