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

insert into MEMBERS(id, address, email, mileage, name, password, phone_number, role_id)
values (3L, '잠실역', 'woowafriends@naver.com', '1000', '김배달', 'woowa', '010-1234-5678', 1L);

insert into MEMBERS(id, address, email, mileage, name, password, phone_number, role_id)
values (4L, '잠실역', 'test@email.com', '1000', '김배민', 'test_password', '010-1234-5678', 2L);


insert into receipt_status (id, name)
values (1L, 'CANCELED'),
       (2L, 'COMPLETED'),
       (3L, 'INPROGRESS'),
       (4L, 'EXPIRED');

insert into liquors (id, alcohol, brand, image_url, name, price, stock, volume, brew_id, region_id,
                     status_id)
values (3L, 10, '새로', '/sero.jpeg', '새로', 4500, 10, 250, 1L, 2L, 1L);
insert into cart_items (id, member_id, liquor_id, quantity)
values (3L, 3L, 3L, 1);

