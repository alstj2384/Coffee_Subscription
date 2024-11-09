package cafeSubscription.coffee.global.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // msg, Http code(200)
    /**
     * USER
     */

    // 이미 사용중인 이메일
    DUPLICATE_USER_EMAIL("이미 사용 중인 이메일입니다", HttpStatus.CONFLICT),

    // 이미 사용중인 닉네임
    DUPLICATE_USER_NICKNAME("이미 사용 중인 닉네임입니다", HttpStatus.CONFLICT),

    // 존재하지않는 유저
    NON_EXISTENT_USER("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST),


    /**
     * BUSINESS
     */

    // 이미 사용중인 사업자 번호
    BUSINESS_NUMBER_ALREADY_IN_USE("이미 사용 중인 사업자 번호입니다", HttpStatus.CONFLICT),

    // 존재하지 않는 업체명
    BUSINESS_NAME_NOT_FOUND("존재하지 않는 업체명입니다", HttpStatus.NOT_FOUND),

    // 이미 사용중인 업체명
    BUSINESS_NAME_ALREADY_IN_USE("이미 사용 중인 업체명입니다", HttpStatus.CONFLICT),

    // 존재하지 않는 사업자명
    BUSINESS_NUMBER_NOT_FOUND("존재하지 않는 사업자명입니다", HttpStatus.NOT_FOUND),


    /**
     * CAFE
     */

    INVALID_SEARCH_TYPE("검색 요청이 올바르지 않습니다", HttpStatus.BAD_REQUEST),

    // 존재하지 않는 카페
    CAFE_NOT_FOUND("존재하지 않는 카페입니다", HttpStatus.NOT_FOUND),


    /**
     * DIARY
     */

    // 존재하지 않는 글
    POST_NOT_FOUND("존재하지 않는 글입니다", HttpStatus.NOT_FOUND),

    // 게시글 작성 실패
    POST_CREATION_FAILED("게시글 작성 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 게시글 수정 실패
    POST_UPDATE_FAILED("게시글 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 게시글 삭제 실패
    POST_DELETION_FAILED("게시글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR),


    /**
     * REVIEW
     */

    // 존재하지 않는 리뷰
    REVIEW_NOT_FOUND("존재하지 않는 리뷰입니다", HttpStatus.NOT_FOUND),

    // 리뷰 작성 실패
    REVIEW_CREATION_FAILED("리뷰 작성 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 리뷰 수정 실패
    REVIEW_UPDATE_FAILED("리뷰 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 리뷰 삭제 실패
    REVIEW_DELETION_FAILED("리뷰 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR),


    /**
     * MENU
     */

    // 존재하지 않는 메뉴
    MENU_NOT_FOUND("존재하지 않는 메뉴입니다", HttpStatus.NOT_FOUND),

    // 메뉴 등록 실패
    MENU_CREATION_FAILED("메뉴 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 메뉴 수정 실패
    MENU_UPDATE_FAILED("메뉴 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 메뉴 삭제 실패
    MENU_DELETION_FAILED("메뉴 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR),


    /**
     * SUBSCRIPTION
     */

    // 존재하지 않는 구독
    SUBSCRIPTION_NOT_FOUND("존재하지 않는 구독입니다", HttpStatus.BAD_REQUEST),

    // 구독 실패
    SUBSCRIPTION_FAILED("구독에 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR ),

    // 만료된 구독,
    SUBSCRIPTION_EXPIRED("만료된 구독", HttpStatus.FORBIDDEN),

    // 이미 구독중
    SUBSCRIPTION_ALREADY_EXISTS("이미 구독중입니다", HttpStatus.BAD_REQUEST),


    /**
     * COUPON
     */

    // 이미 사용된 쿠폰
    COUPON_ALREADY_IN_USE("이미 사용된 쿠폰입니다", HttpStatus.CONFLICT),

    // 쿠폰 사용 실패
    COUPON_USE_FAILED("쿠폰 사용에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR),



    // 핀 번호 불일치
    COUPON_PIN_NOT_MATCH("핀 번호가 일치하지 않습니다", HttpStatus.NOT_FOUND);

    //쿠폰 조회실패
    COUPON_NOT_FOUND("쿠폰 조회에 실패했습니다", HttpStatus.NOT_FOUND);




    private final String msg;
    private final HttpStatus status;

    ErrorCode(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
