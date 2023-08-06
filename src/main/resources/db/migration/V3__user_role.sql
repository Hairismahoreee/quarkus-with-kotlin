create table user_role
(
    user_id varchar(36) not null,
    role_id varchar(36) not null,
    constraint user_role_roles_role_id_fk
        foreign key (role_id) references roles (role_id),
    constraint user_role_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

