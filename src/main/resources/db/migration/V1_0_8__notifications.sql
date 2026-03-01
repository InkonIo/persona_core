create table user_device_tokens
(
    id         bigserial,
    user_id    bigint  not null,
    token      varchar not null,
    enabled    bool    not null         default false,
    created_at timestamp with time zone default timezone('utc'::text, CURRENT_TIMESTAMP),
    primary key (id),
    foreign key (user_id) references users,
    unique (token)
);
