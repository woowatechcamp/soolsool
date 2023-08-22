insert into roles(id, name)
values (1, 'CUSTOMER'),
       (2, 'VENDOR');

insert into liquor_brews(id, name)
values (1, 'SOJU'),
       (2, 'DISTILLED'),
       (3, 'MAKGEOLLI'),
       (4, 'PURE'),
       (5, 'RICE'),
       (6, 'BERRY'),
       (7, 'ETC');

insert into liquor_regions(id, name)
values (1, 'GYEONGGI_DO'),
       (2, 'GANGWON_DO'),
       (3, 'CHUNGCHEONGBUK_DO'),
       (4, 'CHUNGCHEONGNAM_DO'),
       (5, 'GYEONGSANGBUK_DO'),
       (6, 'GYEONGSANGNAM_DO'),
       (7, 'JEOLLABUK_DO'),
       (8, 'JEOLLANAM_DO'),
       (9, 'JEJU_DO');

insert into liquor_status(id, name)
values (1, 'ON_SALE'),
       (2, 'STOPPED');

insert into order_status(id, name)
values (1, 'COMPLETED'),
       (2, 'CANCELED');

insert into receipt_status(id, name)
values (1, 'INPROGRESS'),
       (2, 'CANCELED'),
       (3, 'COMPLETED'),
       (4, 'EXPIRED');

insert into members(id, address, email, mileage, name, password, phone_number, role_id)
values (3, '잠실역', 'woowafriends@naver.com', '1000', '김배달', 'woowa', '010-1234-5678', 1),
       (4, '잠실역', 'test@email.com', '1000', '김배민', 'test_password', '010-1234-5678', 2),
       (5, '잠실역', 'hello@email.com', '1000', '김배민', 'test_password', '010-1234-5678', 1);

insert into receipts(id, member_id, receipt_status_id, original_total_price, mileage_usage,
                     purchased_total_price, total_quantity)
values (1, 3, 1, 10000, 1000, 9000, 3)

