-- 유저 생성
INSERT INTO USERS(role, email, name, nick_name, password, username) VALUES ('2', 'thd@naver.com', '송손님', 'osnie', '$2a$10$mMRwy2uSVgykxtdOGTtoiOUkIyrtPOGrnAtTUBzJPvF04eVAY6/Ci', 'song');
INSERT INTO USERS(role, email, name, nick_name, password, username) VALUES ('2', 'rla@naver.com', '김손님', 'kim', '$2a$10$ZIKh24k2fgUMzaHy2BxSg.wSA5Jj/WHsPQnl5WQ0/TS33JvR6vndW', 'kim');
INSERT INTO USERS(role, email, name, nick_name, password, username) VALUES ('0', 'qkr@naver.com', '박사장', 'park', '$2a$10$DHb865ORx56.3w563BblVuHE2Ur4pexqIgMHJWL87SUAYkzpmJV0S', 'park');
INSERT INTO USERS(role, email, name, nick_name, password, username) VALUES ('0', 'ska@naver.com', '남사장', 'nam', '$2a$10$tZenbPSh6tZ93MCdhbfH1uatzjPh.stwYPta1ylgR5axQ6vgwh7Ku', 'nam');


-- 구독 정보 [송손님(userid= 1) = 일주일 기간 구독중] [김손님(userid = 2) = 미구독자)]
INSERT INTO SUBSCRIPTION(used_count, start_date, end_date, last_used_date, user_id, subscription_type) VALUES('1', '2024-11-10', '2024-11-17', '2024-11-10', 1, 'WEEK');
UPDATE USERS SET subscription_id = 1 WHERE user_id = 1;

-- 사장 사업자
INSERT INTO BUSINESS(opening_date, user_id, b_name, bank_account, business_number) VALUES ('2023-7-12', 3, '박사장의 츄러스 카페', '1234-1234', '123-12-12345');
INSERT INTO BUSINESS(opening_date, user_id, b_name, bank_account, business_number) VALUES ('2017-3-22', 4, '남사장의 원조 국밥', '1234-1234', '123-12-12345');

-- 운영 시간
INSERT INTO operating_hours (
    MONDAY_OPEN, MONDAY_CLOSE,
    TUESDAY_OPEN, TUESDAY_CLOSE,
    WEDNESDAY_OPEN, WEDNESDAY_CLOSE,
    THURSDAY_OPEN, THURSDAY_CLOSE,
    FRIDAY_OPEN, FRIDAY_CLOSE,
    SATURDAY_OPEN, SATURDAY_CLOSE,
    SUNDAY_OPEN, SUNDAY_CLOSE
)
VALUES (
    '06:00', '17:00', -- 월요일
    '06:00', '17:00', -- 화요일
    '06:00', '17:00', -- 수요일
    '06:00', '17:00', -- 목요일
    '06:00', '17:00', -- 금요일
    '10:00', '21:00', -- 토요일
    '10:00', '21:00'  -- 일요일
);

INSERT INTO operating_hours (
    MONDAY_OPEN, MONDAY_CLOSE,
    TUESDAY_OPEN, TUESDAY_CLOSE,
    WEDNESDAY_OPEN, WEDNESDAY_CLOSE,
    THURSDAY_OPEN, THURSDAY_CLOSE,
    FRIDAY_OPEN, FRIDAY_CLOSE,
    SATURDAY_OPEN, SATURDAY_CLOSE,
    SUNDAY_OPEN, SUNDAY_CLOSE
)
VALUES (
    '09:00', '20:00', -- 월요일
    '09:00', '20:00', -- 화요일
    '09:00', '20:00', -- 수요일
    '09:00', '20:00', -- 목요일
    '09:00', '20:00', -- 금요일
    '10:00', '20:00', -- 토요일
    '10:00', '21:00'  -- 일요일
);

-- Image
INSERT INTO IMAGE_ALL(image_path, image_type, i_create_at) VALUES ('', 0, '2024-05-12');
INSERT INTO IMAGE_ALL(image_path, image_type, i_create_at) VALUES ('', 1, '2024-03-21');


-- 카페
INSERT INTO CAFE (
    C_LATITUDE, C_LONGITUDE, OPERATING_HOUR_ID, PIN, BUSINESS_ID, IMAGE_ID, CAFE_ID, CAFE_NAME, DESCRIPTION, LOCATION
)
VALUES
    (36.0, 126.0, 1, '1234', 1, 1, 1, '박사장의 츄러스 카페', '츄러스와 다양한 음료를 제공하는 아늑한 카페입니다.', '충남 부여군 ~~읍'),
    (37.0, 127.0, 2, '5678', 2, 2, 2, '남사장의 원조 국밥', '국밥과 함께 편안한 시간을 보낼 수 있는 식당입니다.', '경기 ~~');

-- diary
INSERT INTO DIARY(
    VIEW, CAFE_CAFE_ID, ENTRY_DATE, UPDATED_AT, DIARY_CONTENT, TITLE
)
VALUES
    -- cafe_cafe_id 1에 대한 다이어리 엔트리
    (100, 1, '2024-10-01', '2024-10-05', '츄러스와 커피의 완벽한 조합을 즐겼어요!', '츄러스의 매력'),
    (150, 1, '2024-10-15', '2024-10-17', '친구들과 함께한 오후의 휴식. 최고의 츄러스!', '오후의 나른함'),
    (200, 1, '2024-11-01', '2024-11-03', '주말에 방문한 카페에서 여유로운 시간을 보냈습니다.', '주말의 여유'),

    -- cafe_cafe_id 2에 대한 다이어리 엔트리
    (80, 2, '2024-09-12', '2024-09-14', '국밥이 정말 맛있었어요. 따뜻한 국물이 일품!', '국밥의 정석'),
    (130, 2, '2024-10-10', '2024-10-12', '가족들과 함께한 저녁. 맛과 분위기 모두 좋았습니다.', '가족과의 저녁'),
    (170, 2, '2024-11-05', '2024-11-08', '한 달에 한 번씩 방문하는 단골집. 늘 만족합니다.', '단골의 즐거움');


-- REVIEW
INSERT INTO REVIEW (
    REPORT_COUNT, CAFE_ID, CREATED_AT, USER_USER_ID, KEYWORD, R_CONTENT
)
VALUES
    -- CAFE_ID 1에 대한 리뷰 엔트리
    (1, 1, '2024-11-01', 1, 2, '츄러스가 바삭하고 맛있었지만 음료가 다소 평범했어요.'),
    (2, 1, '2024-11-03', 2, 4, '카페 분위기가 좋아서 친구들과 재밌게 시간을 보냈습니다.'),
    (1, 1, '2024-11-05', 1, 1, '주차 공간이 협소해 불편함이 있었어요.'),

    -- CAFE_ID 2에 대한 리뷰 엔트리
    (3, 2, '2024-10-15', 1, 5, '국밥이 뜨겁고 깊은 맛이 나서 좋았습니다.'),
    (1, 2, '2024-10-20', 2, 0, '식당이 너무 붐벼서 조용히 식사하기 어려웠습니다.'),
    (2, 2, '2024-11-01', 2, 3, '친절한 서비스 덕분에 기분 좋은 식사를 할 수 있었습니다.');


-- MENU
INSERT INTO MENU (cafe_id, menu_name, m_price, menu_info)
VALUES
    -- cafe_id 1에 대한 메뉴
    (1, '츄러스', 5000, '바삭하고 달콤한 스페인식 디저트'),
    (1, '아메리카노', 3000, '신선한 원두로 내린 커피'),
    (1, '핫초코', 4000, '달콤하고 진한 초콜릿 음료'),

    -- cafe_id 2에 대한 메뉴
    (2, '소고기 국밥', 8000, '진한 국물과 푸짐한 고기가 들어간 국밥'),
    (2, '김치찌개', 7000, '매콤한 김치와 돼지고기가 들어간 찌개'),
    (2, '모듬전', 10000, '다양한 전이 포함된 모듬 요리');