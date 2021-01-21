create table attributes (
    id bigserial not null,
    name varchar(100) unique not null,
    primary key (id)
);

create table document (
    id bigserial not null,
    document_data oid,
    name varchar(100),
    user_id integer,
    primary key (id)
);

create table document_attributes (
    document_id integer not null,
    attribute_id integer not null
);

create table privileges (
    id bigserial not null,
    name varchar(100) not null,
    primary key (id)
);

create table roles (
    id  bigserial not null,
    name varchar(100) unique not null,
    primary key (id)
);

create table roles_privileges (
    role_id integer not null,
    privileges_id integer not null
);

create table users (
    id  bigserial not null,
    password varchar(30) not null,
    username varchar(30) unique not null,
    primary key (id)
);

create table users_roles (
    user_id integer not null,
    role_id integer not null
);

create sequence hibernate_sequence start 1 increment 1;

alter table if exists document add constraint fk_document_users
foreign key (user_id) references users;

alter table if exists document_attributes add constraint fk_doc_attr_attributes
foreign key (attribute_id) references attributes;

alter table if exists document_attributes add constraint fk_doc_attr_document
foreign key (document_id) references document;

alter table if exists roles_privileges add constraint fk_roles_priveleges_priveleges
foreign key (privileges_id) references privileges;

alter table if exists roles_privileges add constraint fk_roles_priveleges_roles
foreign key (role_id) references roles;

alter table if exists users_roles add constraint fk_users_roles_roles
foreign key (role_id) references roles;

alter table if exists users_roles add constraint fk_users_roles_users
foreign key (user_id) references users;
