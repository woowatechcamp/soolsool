insert into liquors (id, brew_id, region_id, status_id, name, price, brand, image_url, stock,
                     alcohol, volume, created_at, modified_at, expired_at)
values (1, 1, 1, 1, '새로', '3000', '롯데', '/soju-url', 100, 12.0, 300,
        NOW(), NOW(), '2023-12-31 23:59:59');
