insert into orders (id, member_id, order_status_id, receipt_id, created_at, modified_at)
values (1, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into payment_info (id, order_id, approved_id, bin, card_mid, install_month, payment_method_type, purchase_corp)
values ('1', '1', '43623044', '56369819', '379113825', '6', 'CARD', '우리카드');
