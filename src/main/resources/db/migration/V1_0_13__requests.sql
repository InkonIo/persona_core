create table requests
(
    id         bigserial primary key,
    user_id    bigint  not null references users (id),
    message    varchar not null,
    title      varchar not null,
    status     varchar not null,
    created_at timestamp with time zone default timezone('utc'::text, CURRENT_TIMESTAMP)
);
