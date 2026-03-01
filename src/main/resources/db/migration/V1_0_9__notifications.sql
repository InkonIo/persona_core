create table notifications
(
    id          bigserial,
    user_id     bigint  not null,
    title       varchar not null,
    description varchar not null,
    is_active   bool    not null         default true,
    deleted     bool    not null         default false,
    created_at  timestamp with time zone default timezone('utc'::text, CURRENT_TIMESTAMP),
    primary key (id),
    foreign key (user_id) references users
);
