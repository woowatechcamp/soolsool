insert into receipts (id, member_id, receipt_status_id,
                      original_total_price, mileage_usage, purchased_total_price,
                      total_quantity,
                      created_at, modified_at, expired_date)
values (1, 1, 1,
        '100.00', '10.00', '90.00',
        5,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('DAY', 30, CURRENT_TIMESTAMP));

INSERT INTO receipt_items (id, receipt_id, liquor_id, brew_id, region_id,
                           liquor_name, liquor_original_price, liquor_purchased_price, liquor_brand,
                           liquor_image_url, liquor_alcohol, liquor_volume,
                           quantity,
                           created_at, modified_at, expired_at)

VALUES (1, 1, 1, 1, 1,
        '새로', '3000', '2700', '롯데',
        '/soju-url', 12.0, 300,
        2,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('DAY', 30, CURRENT_TIMESTAMP)),
       (2, 1, 3, 6, 7,
        '얼음딸기주', '4500', '4050', '진로',
        '/strawberry-url', 14.0, 400,
        3,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('DAY', 30, CURRENT_TIMESTAMP));
