insert into ROLES(id, name)
values (1, 'CUSTOMER');

insert into ROLES(id, name)
values (2, 'VENDOR');

insert into LIQUOR_BREWS(id, name) values (1, 'SOJU');
insert into LIQUOR_REGIONS(id, name) values (1, 'GYEONGSANGNAM_DO');
insert into LIQUOR_STATUS(id, name) values (1, 'ON_SALE');

insert into LIQUORS(id, brew_id, region_id, status_id, name, price, brand, image_url, stock, alcohol, volume)
values (1, 1, 1, 1, '안동 소주', '12000', '안동', '/soju.jpg', 120, 21.7, 400);