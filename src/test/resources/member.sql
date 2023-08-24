insert into members (id, role_id, email, password, name, phone_number, mileage, address,
                     created_at, modified_at)

values (1, 1, 'kim@email.com', 'baedal', '김배달', '010-0000-0000', '0', '잠실역',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 2, 'choi@email.com', 'minjok', '최민족', '010-1111-1111', '0', '구로디지털단지역',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
