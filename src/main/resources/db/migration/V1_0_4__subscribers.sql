create table subscribers
(
    id         bigserial,
    from_user  bigint not null,
    to_user    bigint not null,
    is_active  bool   not null,
    created_at timestamp with time zone default timezone('utc'::text, CURRENT_TIMESTAMP),
    primary key (id),
    foreign key (from_user) references users,
    foreign key (to_user) references users,
    unique (from_user, to_user)
);
