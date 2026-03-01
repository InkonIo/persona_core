-- Добавляем локализованные названия в таблицу countries
ALTER TABLE countries ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE countries ADD COLUMN name_en VARCHAR(255);
ALTER TABLE countries ADD COLUMN name_kz VARCHAR(255);

-- Добавляем локализованные названия в таблицу regions
ALTER TABLE regions ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE regions ADD COLUMN name_en VARCHAR(255);
ALTER TABLE regions ADD COLUMN name_kz VARCHAR(255);

-- Добавляем локализованные названия в таблицу cities
ALTER TABLE cities ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE cities ADD COLUMN name_en VARCHAR(255);
ALTER TABLE cities ADD COLUMN name_kz VARCHAR(255);

-- Добавляем локализованные названия в таблицу work_field
ALTER TABLE work_field ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE work_field ADD COLUMN name_en VARCHAR(255);
ALTER TABLE work_field ADD COLUMN name_kz VARCHAR(255);

-- Добавляем локализованные названия в таблицу professions
ALTER TABLE professions ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE professions ADD COLUMN name_en VARCHAR(255);
ALTER TABLE professions ADD COLUMN name_kz VARCHAR(255);

-- Добавляем локализованные названия в таблицу marital_status
ALTER TABLE marital_status ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE marital_status ADD COLUMN name_en VARCHAR(255);
ALTER TABLE marital_status ADD COLUMN name_kz VARCHAR(255);

-- Добавляем локализованные названия в таблицу status
ALTER TABLE status ADD COLUMN name_ru VARCHAR(255);
ALTER TABLE status ADD COLUMN name_en VARCHAR(255);
ALTER TABLE status ADD COLUMN name_kz VARCHAR(255);

-- =============================================
-- Заполняем данные для countries
-- =============================================
UPDATE countries SET name_ru = 'Казахстан', name_en = 'Kazakhstan', name_kz = 'Қазақстан' WHERE code = 'KAZAKHSTAN';

-- =============================================
-- Заполняем данные для regions
-- =============================================
UPDATE regions SET name_ru = 'Алматинская область',            name_en = 'Almaty Region',              name_kz = 'Алматы облысы'              WHERE code = 'ALMATY_REGION';
UPDATE regions SET name_ru = 'Астана (город)',                  name_en = 'Astana (city)',               name_kz = 'Астана (қала)'              WHERE code = 'ASTANA_REGION';
UPDATE regions SET name_ru = 'Абайская область',               name_en = 'Abai Region',                 name_kz = 'Абай облысы'                WHERE code = 'ABAY';
UPDATE regions SET name_ru = 'Акмолинская область',            name_en = 'Akmola Region',               name_kz = 'Ақмола облысы'              WHERE code = 'AKMOLA';
UPDATE regions SET name_ru = 'Актюбинская область',            name_en = 'Aktobe Region',               name_kz = 'Ақтөбе облысы'              WHERE code = 'AKTOBE';
UPDATE regions SET name_ru = 'Атырауская область',             name_en = 'Atyrau Region',               name_kz = 'Атырау облысы'              WHERE code = 'ATYRAU';
UPDATE regions SET name_ru = 'Восточно-Казахстанская область', name_en = 'East Kazakhstan Region',      name_kz = 'Шығыс Қазақстан облысы'     WHERE code = 'EAST_KZ';
UPDATE regions SET name_ru = 'Жамбылская область',             name_en = 'Zhambyl Region',              name_kz = 'Жамбыл облысы'              WHERE code = 'ZHAMBYL';
UPDATE regions SET name_ru = 'Жетысуская область',             name_en = 'Zhetysu Region',              name_kz = 'Жетісу облысы'              WHERE code = 'ZHETYSU';
UPDATE regions SET name_ru = 'Западно-Казахстанская область',  name_en = 'West Kazakhstan Region',      name_kz = 'Батыс Қазақстан облысы'     WHERE code = 'WEST_KZ';
UPDATE regions SET name_ru = 'Карагандинская область',         name_en = 'Karaganda Region',            name_kz = 'Қарағанды облысы'           WHERE code = 'KARAGANDA';
UPDATE regions SET name_ru = 'Костанайская область',           name_en = 'Kostanay Region',             name_kz = 'Қостанай облысы'            WHERE code = 'KOSTANAY';
UPDATE regions SET name_ru = 'Кызылординская область',         name_en = 'Kyzylorda Region',            name_kz = 'Қызылорда облысы'           WHERE code = 'KYZYLORDA';
UPDATE regions SET name_ru = 'Мангистауская область',          name_en = 'Mangystau Region',            name_kz = 'Маңғыстау облысы'           WHERE code = 'MANGYSTAU';
UPDATE regions SET name_ru = 'Павлодарская область',           name_en = 'Pavlodar Region',             name_kz = 'Павлодар облысы'            WHERE code = 'PAVLODAR';
UPDATE regions SET name_ru = 'Северо-Казахстанская область',   name_en = 'North Kazakhstan Region',     name_kz = 'Солтүстік Қазақстан облысы' WHERE code = 'NORTH_KZ';
UPDATE regions SET name_ru = 'Туркестанская область',          name_en = 'Turkestan Region',            name_kz = 'Түркістан облысы'           WHERE code = 'TURKESTAN';
UPDATE regions SET name_ru = 'Улытауская область',             name_en = 'Ulytau Region',               name_kz = 'Ұлытау облысы'              WHERE code = 'ULYTAU';
UPDATE regions SET name_ru = 'Шымкент (город)',                name_en = 'Shymkent (city)',             name_kz = 'Шымкент (қала)'             WHERE code = 'SHYMKENT';

-- =============================================
-- Заполняем данные для cities
-- =============================================
UPDATE cities SET name_ru = 'Алматы', name_en = 'Almaty', name_kz = 'Алматы' WHERE code = 'ALMATY';
UPDATE cities SET name_ru = 'Астана', name_en = 'Astana', name_kz = 'Астана' WHERE code = 'ASTANA';