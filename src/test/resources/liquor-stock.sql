insert into liquor_stocks (id, liquor_id, stock,
                           created_at, modified_at, expired_at)
values (1, 1, 100,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 30, CURRENT_TIMESTAMP)),
       (2, 1, 200,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 40, CURRENT_TIMESTAMP)),
       (3, 2, 100,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 30, CURRENT_TIMESTAMP)),
       (4, 2, 200,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 40, CURRENT_TIMESTAMP)),
       (5, 2, 300,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 50, CURRENT_TIMESTAMP)),
       (6, 3, 100,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 30, CURRENT_TIMESTAMP)),
       (7, 3, 200,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 40, CURRENT_TIMESTAMP)),
       (8, 3, 300,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 50, CURRENT_TIMESTAMP)),
       (9, 3, 400,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 60, CURRENT_TIMESTAMP)),
       (10, 3, 500,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TIMESTAMPADD('MINUTE', 70, CURRENT_TIMESTAMP));
