drop table if exists cart_items CASCADE;
drop table if exists liquor_regions CASCADE;
drop table if exists liquor_status CASCADE;
drop table if exists liquor_types CASCADE;
drop table if exists liquors CASCADE;
drop table if exists members CASCADE;
drop table if exists order_items CASCADE;
drop table if exists orders CASCADE;
drop table if exists roles CASCADE;
create table cart_items (
                            id bigint not null,
                            created_at timestamp,
                            modified_at timestamp,
                            member_id bigint not null,
                            quantity integer default 1 not null,
                            liquor_id bigint not null,
                            primary key (id)
);
create table liquor_regions (
                                id bigint not null,

                                name varchar(20) not null,
                                primary key (id)
);

create table liquor_status (
                               id bigint not null,
                               name varchar(20) not null,
                               primary key (id)
);
create table liquor_types (
                              id bigint not null,
                              name varchar(20) not null,
                              primary key (id)
);
create table liquors (
                         id bigint not null,
                         created_at timestamp,
                         modified_at timestamp,
                         alcohol double not null,
                         brand varchar(20) not null,
                         image_url varchar(255) not null,
                         name varchar(30) not null,
                         price numeric(19,2) not null,
                         stock integer not null,
                         volume integer not null,
                         region_id bigint not null,
                         status_id bigint not null,
                         type_id bigint not null,
                         primary key (id)
);

create table members (
                         id bigint not null,
                         created_at timestamp,
                         modified_at timestamp,
                         address varchar(100) not null,
                         email varchar(255) not null,
                         mileage numeric(19,2) not null,
                         name varchar(20) not null,
                         password varchar(60) not null,
                         phone_number varchar(13) not null,
                         role bigint not null,
                         primary key (id)
);
create table order_items (
                             id bigint not null,
                             created_at timestamp,
                             modified_at timestamp,
                             liquor_alcohol double not null,
                             liquor_brand varchar(20) not null,
                             liquor_image_url varchar(255) not null,
                             liquor_region varchar(30) not null,
                             liquor_category varchar(20) not null,
                             liquor_name varchar(30) not null,
                             liquor_original_price numeric(19,2) not null,
                             liquor_purchased_price numeric(19,2) not null,
                             quantity integer not null,
                             liquor_volume integer not null,
                             order_id bigint not null,
                             primary key (id)
);
create table orders (
                        id bigint not null,
                        created_at timestamp,
                        modified_at timestamp,
                        member_id bigint not null,
                        status varchar(20) not null,
                        total_price numeric(19,2) not null,
                        total_quantity integer not null,
                        primary key (id)
);
create table roles (
                       id bigint not null,
                       name varchar(20) not null,
                       primary key (id)
);


alter table members
    add constraint UK_9d30a9u1qpg8eou0otgkwrp5d unique (email);

alter table cart_items
    add constraint FKnlm31atwdp3854332genviw4a
        foreign key (liquor_id)
            references liquors;
alter table liquors
    add constraint FKq6qx4e2e6t3ajsfyvnqydjgf4
        foreign key (region_id)
            references liquor_regions;
alter table liquors
    add constraint FKmqckknt07a2ervkdixfni449w
        foreign key (status_id)
            references liquor_status;
alter table liquors
    add constraint FKhas3e985lhg49s9yi5s618x1r
        foreign key (type_id)
            references liquor_types;
alter table members
    add constraint FKea1g9gk4nmghj9cvg5h6282rp
        foreign key (role)
            references roles;
alter table order_items
    add constraint FKbioxgbv59vetrxe0ejfubep1w
        foreign key (order_id)
            references orders;
