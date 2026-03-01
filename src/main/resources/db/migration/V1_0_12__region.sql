create table regions
(
    id         bigserial,
    name       varchar not null,
    code       varchar not null,
    country_id bigint,
    primary key (id),
    foreign key (country_id) references countries
);

alter table cities drop column country_id;

alter table cities add column region_id bigint references regions(id);