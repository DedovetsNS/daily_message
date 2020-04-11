create table message (
  id  bigserial not null,
  date timestamp,
  subject varchar(255),
  text varchar(255),
  primary key (id));

create table user_role (
  user_id int8 not null,
  roles varchar(255));

create table usr (
  id  bigserial not null,
  active boolean not null,
  password varchar(255),
  username varchar(255),
  primary key;

create table visit (
  id  bigserial not null,
  date timestamp,
  message_id varchar(255),
  user_id varchar(255),
  primary key (id));

alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr