insert into ROLES(id, name)
values (1, 'CUSTOMER');

insert into ROLES(id, name)
values (2, 'VENDOR');
insert into MEMBERS(id, address, email, mileage, name, password, phone_number, role_id)
values (3L, '잠실역', 'woowafriends@naver.com', '1000', '김배달', 'woowa', '010-1234-5678', 1L);
