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

insert into receipt_status(id, name)
values (1, 'INPROGRESS'),
       (2, 'CANCELED'),
       (3, 'EXPIRED'),
       (4, 'COMPLETED');

insert into order_status(id, name)
values (1, 'COMPLETED'),
       (2, 'REFUND'),
       (3, 'CANCELED');

insert into members(id, address, email, mileage, name, password, phone_number, role_id)
values (1, '잠실역', 'admin', '10000', '관리자', 'admin', '010-1234-5678', 2);

insert into members(id, address, email, mileage, name, password, phone_number, role_id)
values (2, '잠실역', 'woowafriends@naver.com', '1000', '김배달', 'woowa', '010-1234-5678', 1);

-- liquors
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('1', '안동소주[안동소주400ml(45%)]', '45.0', '브랜드A', 'image', '25000', '1000', '400', '8', '1', '1',
        '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('2', '선운산복분자주[500ml*2병(와인병)]', '0.0', '브랜드A', 'image', '34000', '1000', '500', '8', '1',
        '6', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('3', '한산소곡주[소곡주700ml(도자기병.잔2개18%)]', '18.0', '브랜드A', 'image', '27000', '1000', '700', '8',
        '1', '4', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('4', '한산소곡주[소곡주1.8L(18%)]', '18.0', '브랜드A', 'image', '36900', '1000', '1800', '8', '1', '4',
        '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('5', '한산소곡주[불소곡주700ml(도자기병.잔2개43%)]', '43.0', '브랜드A', 'image', '41000', '1000', '700', '8',
        '1', '4', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('6', '내장산복분자주[복분자주(360mlx3병+미니어처50mlx2병.16%)]', '16.0', '브랜드A', 'image', '26600', '1000',
        '360', '8', '1', '6', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('7', '2016년산 산머루와인 하미앙 깊은맛 드라이[드라이 750ml 12% 1병]', '12.0', '브랜드A', 'image', '38000', '1000',
        '750', '8', '1', '6', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('8', '"명가원복분자술[명가원 복분자술 750ml, 16%]"', '16.0', '브랜드A', 'image', '25000', '1000', '750', '8',
        '1', '4', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('9', '[대통령취임식만찬주] 붉은진주머루와인[머루와인스위트750ml(12%)]', '12.0', '브랜드A', 'image', '24000', '1000',
        '750', '8', '1', '6', '2023-12-12 0:00:00');
INSERT INTO liquors (id, name, alcohol, brand, image_url, price, stock, volume, region_id,
                     status_id, brew_id, expired_at)
VALUES ('10', '[대통령취임식만찬주] 붉은진주머루와인[500ml]', '0.0', '브랜드A', 'image', '16000', '1000', '500', '8',
        '1', '6', '2023-12-12 0:00:00');

INSERT INTO cart_items (id, member_id, quantity, liquor_id)
VALUES (1, 1, 2, 1);
INSERT INTO cart_items (id, member_id, quantity, liquor_id)
VALUES (2, 1, 3, 2);

INSERT INTO receipts(id, member_id, receipt_status_id, original_total_price, mileage_usage,
                     purchased_total_price, total_quantity, expiration_date)
VALUES (1, 1, 1, 172000, 2000, 170000, 5, '2021-12-31');

INSERT INTO receipt_items(id, receipt_id, liquor_id, liquor_name, liquor_original_price,
                          liquor_alcohol, liquor_brand, liquor_purchased_price, quantity,
                          expired_at, liquor_image_url, liquor_volume, brew_id, region_id)
VALUES (1, 1, 1, '안동소주[안동소주400ml(45%)]', 25000, 45, '브랜드A', 50000, 2, '2021-12-31', 'image', 300, 1,
        2);
INSERT INTO receipt_items(id, receipt_id, liquor_id, liquor_name, liquor_original_price,
                          liquor_alcohol, liquor_brand, liquor_purchased_price, quantity,
                          expired_at, liquor_image_url, liquor_volume, brew_id, region_id)
VALUES (2, 1, 2, '2번 아이템', 34000, 30, '브랜드B', 102000, 3, '2021-12-31', 'image', 300, 1, 2);
