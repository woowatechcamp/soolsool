insert into liquors (id, brew_id, region_id, status_id, name, price, brand, image_url, stock,
                     alcohol, volume,
                     created_at, modified_at, expired_at)
values (1, 1, 1, 1, '새로', '3000', '롯데', '/soju-url', 100,
        12.0, 300,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 30, CURRENT_TIMESTAMP)),
       (2, 7, 1, 2, '하이트', '4000', '진로', '/beer-url', 200,
        24.0, 600,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 30, CURRENT_TIMESTAMP)),
       (3, 6, 7, 1, '얼음딸기주', '4500', '우영미', '/strawberry-url', 20,
        14.0, 400,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 30, CURRENT_TIMESTAMP));
