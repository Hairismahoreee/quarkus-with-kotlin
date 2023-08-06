create table roles
(
    role_id   varchar(36) not null,
    name       varchar(50) not null,
    created_at timestamp   null,
    updated_at timestamp   null,
    deleted_at timestamp   null,
    constraint roles_pk
        primary key (role_id)
);

