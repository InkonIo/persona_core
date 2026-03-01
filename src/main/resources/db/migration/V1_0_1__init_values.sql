insert into status(name, code)
values ('Новичок', 'BEGINNER'),
       ('Любитель', 'AMATEUR'),
       ('Мастер', 'MASTER'),
       ('Профи', 'PROFESSIONAL'),
       ('Меценат', 'PATRON');

insert into marital_status(name, code)
values ('Холост/не замужем', 'SINGLE'),
       ('Женат/замужем', 'MARRIED');

insert into countries(name, code)
values ('Казахстан', 'KAZAKHSTAN');

insert into cities(name, code, country_id)
values ('Алматы', 'ALMATY',1),
       ('Астана', 'ASTANA', 1);

-- Регионы (области)
insert into regions(name, code, country_id)
values ('Алматинская область', 'ALMATY_REGION', 1),
       ('Астана (город)', 'ASTANA_REGION', 1),
       ('Абайская область', 'ABAY', 1),
       ('Акмолинская область', 'AKMOLA', 1),
       ('Актюбинская область', 'AKTOBE', 1),
       ('Атырауская область', 'ATYRAU', 1),
       ('Восточно-Казахстанская область', 'EAST_KZ', 1),
       ('Жамбылская область', 'ZHAMBYL', 1),
       ('Жетысуская область', 'ZHETYSU', 1),
       ('Западно-Казахстанская область', 'WEST_KZ', 1),
       ('Карагандинская область', 'KARAGANDA', 1),
       ('Костанайская область', 'KOSTANAY', 1),
       ('Кызылординская область', 'KYZYLORDA', 1),
       ('Мангистауская область', 'MANGYSTAU', 1),
       ('Павлодарская область', 'PAVLODAR', 1),
       ('Северо-Казахстанская область', 'NORTH_KZ', 1),
       ('Туркестанская область', 'TURKESTAN', 1),
       ('Улытауская область', 'ULYTAU', 1),
       ('Шымкент (город)', 'SHYMKENT', 1);

-- Города привязываем к регионам
update cities set region_id = (select id from regions where code = 'ALMATY_REGION') where code = 'ALMATY';
update cities set region_id = (select id from regions where code = 'ASTANA_REGION') where code = 'ASTANA';

insert into professions(name, code)
values ('Java Разработчик', 'JAVA_DEVELOPER'),
       ('IT предприниматель', 'IT_ENTREPRENEUR');

insert into work_field(name, code)
values ('IT', 'IT'),
       ('CS', 'CS');
