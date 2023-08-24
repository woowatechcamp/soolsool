insert into liquors (id, brew_id, region_id, status_id, name, price, brand, image_url,
                     alcohol, volume, total_stock,
                     created_at, modified_at)
values (1, 1, 1, 1, '새로', '3000', '롯데', '/soju-url',
        12.0, 300, 100,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 7, 1, 2, '하이트', '4000', '진로', '/beer-url',
        24.0, 600, 100,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 6, 7, 1, '얼음딸기주', '4500', '우영미', '/strawberry-url',
        14.0, 400, 100,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

