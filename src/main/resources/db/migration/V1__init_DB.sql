create table message (
  id  bigserial not null,
  date timestamp,
  subject varchar(500),
  text varchar(2500),
  primary key (id));

create table user_role (
  user_id bigserial not null,
  roles varchar(255));

create table usr (
  id  bigserial not null,
  active boolean not null,
  password varchar(255),
  username varchar(255),
  primary key (id));

create table visit (
  id  bigserial not null,
  date timestamp,
  message_id varchar(255),
  user_id varchar(255),
  primary key (id));

alter table if exists user_role add constraint user_role_usr foreign key (user_id) references usr