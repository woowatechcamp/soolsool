drop table if exists cart_items;
drop table if exists hibernate_sequence;
drop table if exists liquor_regions;
drop table if exists liquor_types;
drop table if exists liquors;
drop table if exists members;
drop table if exists order_items;
drop table if exists orders;
drop table if exists roles;

create table cart_items (
    id bigint not null,
    created_at datetime(6),
    modified_at datetime(6),
    member_id bigint not null,
    quantity integer default 1 not null,
    liquor_id bigint not null,
    primary key (id)
) engine=InnoDB;
create table hibernate_sequence (
    next_val bigint
) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table liquor_regions (
    id bigint not null,
    created_at datetime(6),
    modified_at datetime(6),
    name varchar(20) not null,
    primary key (id)
) engine=InnoDB;
create table liquor_types (
    id bigint not null,
    created_at datetime(6),
    modified_at datetime(6),
    name varchar(20) not null,
    primary key (id)
) engine=InnoDB;
create table liquors (
     id bigint not null,
     created_at datetime(6),
     modified_at datetime(6),
     alcohol double precision not null,
     brand varchar(20) not null,
     image_url varchar(255) not null,
     name varchar(30) not null,
     price decimal(19,2) not null,
     stock integer not null,
     volume integer not null,
     region_id bigint not null,
     status_id bigint not null,
     type_id bigint not null,
     primary key (id)
) engine=InnoDB;
create table members (
     id bigint not null,
     created_at datetime(6),
     modified_at datetime(6),
     address varchar(100) not null,
     email varchar(255) not null,
     mileage decimal(19,2) not null,
     name varchar(20) not null,
     password varchar(60) not null,
     phone_number varchar(13) not null,
     role_id bigint not null,
     primary key (id)
) engine=InnoDB;
create table order_items (
     id bigint not null,
     created_at datetime(6),
     modified_at datetime(6),
     liquor_alcohol double precision not null,
     liquor_brand varchar(20) not null,
     liquor_image_url varchar(255) not null,
     liquor_region varchar(30) not null,
     liquor_category varchar(20) not null,
     liquor_name varchar(30) not null,
     liquor_original_price decimal(19,2) not null,
     liquor_purchased_price decimal(19,2) not null,
     quantity integer not null,
     liquor_volume integer not null,
     order_id bigint not null,
     primary key (id)
) engine=InnoDB;
create table orders (
    id bigint not null,
    created_at datetime(6),
    modified_at datetime(6),
    member_id bigint not null,
    status varchar(20) not null,
    total_price decimal(19,2) not null,
    total_quantity integer not null,
    primary key (id)
) engine=InnoDB;
create table roles (
    id bigint not null,
    name varchar(20) not null,
    primary key (id)
) engine=InnoDB;
