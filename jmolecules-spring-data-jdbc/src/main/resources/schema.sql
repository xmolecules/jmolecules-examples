drop table if exists address cascade
drop table if exists annotated_aggregate cascade
drop table if exists customer cascade
drop table if exists line_item cascade
drop table if exists sample_order cascade

create table address (customer uuid, customer_key int, id uuid not null, city varchar(255), street varchar(255), zip_code varchar(255), primary key (id))
create table annotated_aggregate(id uuid not null, text varchar(255), primary key (id))
create table customer (id uuid not null, firstname varchar(255), lastname varchar(255), primary key (id))
create table line_item (id uuid not null, line_items_order_id uuid, primary key (id))
create table sample_order (customer uuid, id uuid not null, primary key (id))

alter table if exists address add constraint FKcm7j748xlfp1n0i2wisx0swoi foreign key (customer) references customer
alter table if exists line_item add constraint FKfxik44eoyiy4w7xsb09lrtlhe foreign key (line_items_order_id) references sample_order
