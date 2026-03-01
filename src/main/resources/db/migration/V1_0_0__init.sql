create table countries
(
    id   bigserial,
    name varchar not null,
    code varchar not null,
    primary key (id)
);

create table cities
(
    id         bigserial,
    name       varchar not null,
    code       varchar not null,
    country_id bigint,
    primary key (id),
    foreign key (country_id) references countries
);

create table work_field
(
    id   bigserial,
    name varchar not null,
    code varchar not null,
    primary key (id)
);

create table professions
(
    id   bigserial,
    name varchar not null,
    code varchar not null,
    primary key (id)
);

create table status
(
    id   bigserial,
    name varchar not null,
    code varchar not null,
    primary key (id)
);

create table marital_status
(
    id   bigserial,
    name varchar not null,
    code varchar not null,
    primary key (id)
);

create table users
(
    id                bigserial,
    login             varchar not null,
    keycloak_id       varchar not null,
    email             varchar not null,
    full_name         varchar not null,
    date_of_birth     date    not null,
    city_id           bigint,
    work_field_id     bigint,
    status_id         bigint,
    marital_status_id bigint,
    education         varchar(1000),
    skills            varchar(1000),
    salary            jsonb,
    dream_work        varchar,
    hobby             varchar,
    social_links      varchar,
    created_at        timestamp with time zone default timezone('utc'::text, CURRENT_TIMESTAMP),
    primary key (id),
    foreign key (city_id) references cities,
    foreign key (work_field_id) references work_field,
    foreign key (status_id) references status,
    foreign key (marital_status_id) references marital_status
);

create table users_professions
(
    user_id       bigint not null,
    profession_id bigint not null,
    foreign key (user_id) references users,
    foreign key (profession_id) references professions
)
