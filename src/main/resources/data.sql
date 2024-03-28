INSERT INTO users (auth_id, created_at, email, name, nickname, role, social_type, modified_at)
VALUES
    ('12345', NOW(), 'john.doe@example.com', '김영상', '영사미', 'ROLE_USER',  'KAKAO', NOW()),
    ('67890', NOW(), 'jane.smith@example.com', '한승헌', 'hhh', 'ROLE_USER', 'KAKAO', NOW()),
    ('29308', NOW(), 'jane.smith@example.com', '유환', 'hwan', 'ROLE_USER', 'KAKAO', NOW());


INSERT INTO advertisement (user_id, title, content, view_cnt, total_price, prize_winner_cnt, company_name, start_date, end_date, created_at, modified_at)
VALUES
    (1, '김채원으로 3행시', '김채원으로 3행시 지어주세요', 100, 50000, 3, '아이즈원', '2022-02-14T10:30:00', '2023-02-20T18:00:00', NOW(), NOW()),
    (1, '김채원으로 3행시', '김채원으로 3행시 지어주세요', 100, 50000, 3, '아이즈원', '2023-02-14T10:30:00', '2024-02-20T18:00:00', NOW(), NOW()),
    (2, '이디야 3행시', '이디야로 3행시 지어주세요', 150, 75000, 2, '이디야커피', '2022-02-15T12:00:00', '2023-02-22T20:00:00', NOW(), NOW()),
    (2, '이디야 3행시', '이디야로 3행시 지어주세요', 150, 75000, 2, '이디야커피', '2023-02-15T12:00:00', '2024-02-22T20:00:00', NOW(), NOW()),
    (3, '삼성전자 이벤트', '삼성전자 제품 체험 이벤트', 200, 100000, 5, '삼성전자', '2022-02-16T14:30:00', '2023-02-24T22:00:00', NOW(), NOW()),
    (3, '삼성전자 이벤트', '삼성전자 제품 체험 이벤트', 200, 100000, 5, '삼성전자', '2023-02-16T14:30:00', '2024-02-24T22:00:00', NOW(), NOW()),
    (1, '컴퓨터 할인 특가', '컴퓨터 및 주변기기 할인 특가 이벤트', 120, 80000, 3, '전자마트', '2022-02-17T16:00:00', '2023-02-26T00:00:00', NOW(), NOW()),
    (2, '컴퓨터 할인 특가', '컴퓨터 및 주변기기 할인 특가 이벤트', 120, 80000, 3, '전자마트', '2023-02-17T16:00:00', '2024-02-26T00:00:00', NOW(), NOW()),
    (1, '운동화 신상 출시', '신상 운동화 출시 이벤트', 180, 90000, 4, '스포츠마트', '2022-02-18T18:30:00', '2023-02-28T02:30:00', NOW(), NOW()),
    (2, '운동화 신상 출시', '신상 운동화 출시 이벤트', 180, 90000, 4, '스포츠마트', '2023-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (3, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2025-02-18T18:30:00', '2026-02-28T02:30:00', NOW(), NOW()),

    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2024-02-18T18:30:00', '2025-02-28T02:30:00', NOW(), NOW()),

    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW()),
    (2, '트레비로 3행시를 지어주세요', '트레비로 3행시를 지어주세요', 300, 1000000, 6, '트레비', '2022-02-18T18:30:00', '2024-02-28T02:30:00', NOW(), NOW());


-- Inserting data into File table
INSERT INTO file (advertisement_id, comment_id, file_type, uuid, url, created_at, modified_at)
VALUES
    (1, null, 'IMAGE_JPEG', '12345678', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4019.jpeg', now(), now()),
    (2, null, 'IMAGE_JPEG', 'abcdefgh', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4019.jpeg', now(), now()),
    (3, null, 'IMAGE_JPEG', '87654321', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4019.jpeg', now(), now()),
    (4, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4019.jpeg', now(), now()),
    (5, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4019.jpeg', now(), now()),
    (6, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4019.jpeg', now(), now()),
    (7, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4273.jpeg', now(), now()),
    (8, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4273.jpeg', now(), now()),
    (9, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4273.jpeg', now(), now()),
    (10, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/IMG_4273.jpeg', now(), now()),
    (11, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (12, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (13, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (14, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (15, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (16, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (17, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (18, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (19, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (20, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (21, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (22, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (23, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (24, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (25, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (26, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (27, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (28, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (29, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (30, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (31, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (32, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (33, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (34, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (35, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (36, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (37, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (38, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (39, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (40, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now()),
    (41, null, 'IMAGE_JPEG', 'ijklmnop', 'https://pangta-bucket.s3.ap-northeast-2.amazonaws.com/Screenshot%202024-03-04%20at%207.24.43%20PM.png', now(), now());

INSERT INTO comment (advertisement_id, file_id, user_id, content, created_at, modified_at)
VALUES
    (1,null,1,'1번광고 1번댓글',now(),now()),
    (1,null,1,'1번광고 2번댓글',now(),now()),
    (1,null,1,'1번광고 3번댓글',now(),now()),
    (1,null,1,'1번광고 4번댓글',now(),now()),

    (2,null,1,'2번광고 1번댓글',now(),now()),
    (2,null,1,'2번광고 2번댓글',now(),now()),
    (2,null,1,'2번광고 3번댓글',now(),now()),
    (2,null,1,'2번광고 4번댓글',now(),now());

