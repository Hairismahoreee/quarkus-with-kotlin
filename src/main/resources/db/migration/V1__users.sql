create table users
(
    user_id    varchar(36)  not null,
    email      varchar(50)  null,
    username   varchar(50)  not null,
    password   varchar(191) not null,
    created_at timestamp    null,
    updated_at timestamp    null,
    deleted_at timestamp    null,
    constraint users_pk
        primary key (user_id),
    constraint users_pk2
        unique (email),
    constraint users_pk3
        unique (username)
);

