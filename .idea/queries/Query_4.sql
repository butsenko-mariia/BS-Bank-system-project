CREATE TABLE client (
                        id UUID PRIMARY KEY,
                        full_name CHARACTER VARYING(100),
                        date_of_birth DATE,
                        sex CHARACTER VARYING(10),
                        nationality CHARACTER VARYING(100),
                        mobile_phone CHARACTER VARYING(50),
                        individual_tax_number CHARACTER VARYING(50),
                        passport_number CHARACTER VARYING(50),
                        legal_address CHARACTER VARYING(255),
                        place_of_birth CHARACTER VARYING(255),
                        record_number CHARACTER VARYING(50),
                        place_of_work_or_study CHARACTER VARYING(255),
                        status CHARACTER VARYING(50)
);

CREATE TABLE card (
                      id UUID PRIMARY KEY,
                      client_id UUID,
                      card_number CHARACTER VARYING(50),
                      card_type CHARACTER VARYING(50),
                      balance DECIMAL(19, 4),
                      currency CHARACTER VARYING(10),
                      status CHARACTER VARYING(50),
                      FOREIGN KEY (client_id) REFERENCES client (id)
);

CREATE TABLE deposit (
                         id UUID PRIMARY KEY,
                         client_id UUID,
                         original_sum DECIMAL(19, 4),
                         profit DECIMAL(19, 4),
                         open_date DATE,
                         close_date DATE,
                         interest_rate DECIMAL(19, 4),
                         currency CHARACTER VARYING(10),
                         status CHARACTER VARYING(50),
                         tax_rate DECIMAL(19, 4),
                         military_rate DECIMAL(19, 4),
                         deposit_type CHARACTER VARYING(30),
                         FOREIGN KEY (client_id) REFERENCES client (id)
);

CREATE TABLE loan (
                      id UUID PRIMARY KEY,
                      client_id UUID,
                      original_sum DECIMAL(19, 4),
                      current_balance DECIMAL(19, 4),
                      open_date DATE,
                      close_date DATE,
                      next_payment_date DATE,
                      term_month BIGINT,
                      payment_day INTEGER,
                      monthly_payment DECIMAL(19, 4),
                      interest_rate DECIMAL(19, 4),
                      monthly_rate DECIMAL(19, 4),
                      currency CHARACTER VARYING(10),
                      status CHARACTER VARYING(50),
                      overdue_sum DECIMAL(19, 4),
                      change DECIMAL(19, 4),
                      FOREIGN KEY (client_id) REFERENCES client (id)
);

CREATE TABLE transaction (
                             id UUID PRIMARY KEY,
                             open_date DATE,
                             open_time TIME,
                             sum DECIMAL(19, 4),
                             currency CHARACTER VARYING(10),
                             operation_info CHARACTER VARYING(255),
                             sign CHAR(1),
                             account_id_from UUID,
                             account_id_to UUID,
                             status CHARACTER VARYING(50)
);



-- =============================================
-- ЧАСТИНА 1: КЛІЄНТИ (15 осіб)
-- =============================================
INSERT INTO client (id, full_name, date_of_birth, sex, nationality, mobile_phone, individual_tax_number, passport_number, legal_address, place_of_birth, record_number, place_of_work_or_study, status)
VALUES
-- 1. Тарас
('11111111-1111-1111-1111-111111111111', 'Шевченко Тарас Григорович', '1990-03-09', 'M', 'Українець', '+380991234567', '1234567890', 'AB123456', 'м. Київ, вул. Хрещатик, 1', 'с. Моринці', 'REC-001', 'IT компанія GlobalLogic', 'ACTIVE'),
-- 2. Олена
('22222222-2222-2222-2222-222222222222', 'Коваленко Олена Петрівна', '1985-04-12', 'W', 'Українка', '+380501112233', '2121212121', 'ME123456', 'м. Львів, вул. Городоцька, 15', 'м. Львів', 'REC-002', 'Львівська Політехніка', 'ACTIVE'),
-- 3. Андрій
('33333333-3333-3333-3333-333333333333', 'Бондаренко Андрій Васильович', '1992-08-23', 'M', 'Українець', '+380672223344', '3131313131', 'KC654321', 'м. Одеса, вул. Дерибасівська, 10', 'м. Одеса', 'REC-003', 'ФОП Бондаренко', 'ACTIVE'),
-- 4. Ірина
('44444444-4444-4444-4444-444444444444', 'Мельник Ірина Сергіївна', '1999-11-30', 'W', 'Українка', '+380933334455', '4141414141', 'OO987654', 'м. Харків, вул. Сумська, 5', 'м. Харків', 'REC-004', 'Студентка ХНУ', 'ACTIVE'),
-- 5. Дмитро
('55555555-5555-5555-5555-555555555555', 'Ткаченко Дмитро Олексійович', '1978-02-15', 'M', 'Українець', '+380634445566', '5151515151', 'KM112233', 'м. Дніпро, пр. Яворницького, 100', 'м. Запоріжжя', 'REC-005', 'Завод Інтерпайп', 'ACTIVE'),
-- 6. Наталія
('66666666-6666-6666-6666-666666666666', 'Кравчук Наталія Володимирівна', '1988-06-20', 'W', 'Українка', '+380975556677', '6161616161', 'TT445566', 'м. Вінниця, вул. Соборна, 44', 'м. Вінниця', 'REC-006', 'Лікарня №1', 'ACTIVE'),
-- 7. Сергій
('77777777-7777-7777-7777-777777777777', 'Бойко Сергій Іванович', '2001-01-10', 'M', 'Українець', '+380666667788', '7171717171', 'AA778899', 'м. Київ, вул. Антоновича, 3', 'м. Біла Церква', 'REC-007', 'IT компанія SoftServe', 'ACTIVE'),
-- 8. Вікторія
('88888888-8888-8888-8888-888888888888', 'Лисенко Вікторія Олександрівна', '1995-09-09', 'W', 'Українка', '+380507778899', '8181818181', 'CC223344', 'м. Полтава, вул. Європейська, 22', 'м. Полтава', 'REC-008', 'Школа №5', 'ACTIVE'),
-- 9. Олег
('99999999-9999-9999-9999-999999999999', 'Руденко Олег Миколайович', '1982-12-12', 'M', 'Українець', '+380688889900', '9191919191', 'MM556677', 'м. Чернігів, пр. Миру, 12', 'м. Чернігів', 'REC-009', 'Приватний підприємець', 'ACTIVE'),
-- 10. Юлія (Blocked)
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Савченко Юлія Андріївна', '1990-05-25', 'W', 'Українка', '+380939990011', '1010101010', 'PP889900', 'м. Черкаси, б-р Шевченка, 200', 'м. Умань', 'REC-010', 'Тимчасово не працює', 'BLOCKED'),
-- 11. Олександр
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Мороз Олександр Віталійович', '1975-07-07', 'M', 'Українець', '+380990001122', '2020202020', 'EE112233', 'м. Суми, вул. Харківська, 9', 'м. Охтирка', 'REC-011', 'АТБ Маркет', 'ACTIVE'),
-- 12. Анна
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Поліщук Анна Ігорівна', '1993-03-03', 'W', 'Українка', '+380671112233', '3030303030', 'RR445566', 'м. Житомир, вул. Київська, 77', 'м. Коростень', 'REC-012', 'Салон краси Beauty', 'ACTIVE'),
-- 13. Максим
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Вовк Максим Тарасович', '1989-10-18', 'M', 'Українець', '+380632223344', '4040404040', 'UU778899', 'м. Рівне, вул. Соборна, 15', 'м. Дубно', 'REC-013', 'Нова Пошта', 'ACTIVE'),
-- 14. Тетяна
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Гончар Тетяна Юріївна', '1997-01-28', 'W', 'Українка', '+380953334455', '5050505050', 'II001122', 'м. Луцьк, пр. Волі, 5', 'м. Ковель', 'REC-014', 'Кав''ярня Aroma', 'ACTIVE'),
-- 15. Євген
('ffffffff-ffff-ffff-ffff-ffffffffffff', 'Павленко Євген Анатолійович', '1980-08-08', 'M', 'Українець', '+380984445566', '6060606060', 'OO334455', 'м. Ужгород, пл. Шандора Петефі, 2', 'м. Мукачево', 'REC-015', 'Будівельна фірма', 'ACTIVE');


-- =============================================
-- ЧАСТИНА 2: КАРТКИ (Card)
-- =============================================
INSERT INTO card (id, client_id, card_number, card_type, balance, currency, status) VALUES
-- Тарас (id 1111)
('card0001-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', '4149 4999 9999 9999', 'CREDIT', 5000.00, 'UAH', 'ACTIVE'),
-- Олена (id 2222)
('card0002-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', '5168 0000 0000 2222', 'DEBIT', 15400.50, 'UAH', 'ACTIVE'),
-- Андрій (id 3333) - Валютна
('card0003-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', '4149 0000 0000 3333', 'CREDIT', 2500.00, 'USD', 'ACTIVE'),
-- Ірина (id 4444)
('card0004-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', '5168 0000 0000 4444', 'DEBIT', 800.00, 'UAH', 'ACTIVE'),
-- Дмитро (id 5555)
('card0005-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', '5375 0000 0000 5555', 'CREDIT', 50000.00, 'UAH', 'ACTIVE'),
-- Наталія (id 6666)
('card0006-6666-6666-6666-666666666666', '66666666-6666-6666-6666-666666666666', '5168 0000 0000 6666', 'DEBIT', 12500.00, 'UAH', 'ACTIVE'),
-- Сергій (id 7777)
('card0007-7777-7777-7777-777777777777', '77777777-7777-7777-7777-777777777777', '5375 0000 0000 7777', 'DEBIT', 99000.00, 'UAH', 'ACTIVE'),
-- Вікторія (id 8888)
('card0008-8888-8888-8888-888888888888', '88888888-8888-8888-8888-888888888888', '5168 8888 8888 8888', 'DEBIT', 0.00, 'UAH', 'ACTIVE'),
-- Олег (id 9999)
('card0009-9999-9999-9999-999999999999', '99999999-9999-9999-9999-999999999999', '5168 9999 9999 9999', 'DEBIT', 100.00, 'UAH', 'ACTIVE'),
-- Юлія (id aaaa) - Blocked
('card0010-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '4149 0000 0000 0010', 'DEBIT', 120.00, 'UAH', 'BLOCKED'),
-- Олександр (id bbbb)
('card0011-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '5168 0000 0000 1111', 'DEBIT', 3200.00, 'UAH', 'ACTIVE'),
-- Анна (id cccc)
('card0012-cccc-cccc-cccc-cccccccccccc', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '5168 1212 1212 1212', 'DEBIT', 5500.00, 'UAH', 'ACTIVE'),
-- Максим (id dddd)
('card0013-dddd-dddd-dddd-dddddddddddd', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '5375 0000 0000 3313', 'CREDIT', 0.00, 'UAH', 'ACTIVE'),
-- Тетяна (id eeee)
('card0014-eeee-eeee-eeee-eeeeeeeeeeee', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '5168 1414 1414 1414', 'DEBIT', 0.00, 'UAH', 'ACTIVE'),
-- Євген (id ffff)
('card0015-ffff-ffff-ffff-ffffffffffff', 'ffffffff-ffff-ffff-ffff-ffffffffffff', '5168 1515 1515 1515', 'DEBIT', 20000.00, 'UAH', 'ACTIVE');

-- =============================================
-- ЧАСТИНА 3: ДЕПОЗИТИ (Deposit)
-- =============================================
INSERT INTO deposit (id, client_id, original_sum, profit, open_date, close_date, interest_rate, currency, status, tax_rate, military_rate, deposit_type) VALUES
-- Тарас (id 1111)
('depo0001-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 10000.00, 0.00, '2023-01-01', '2024-01-01', 0.14, 'UAH', 'ACTIVE', 0.18, 0.015, 'StandardDeposit'),
-- Наталія (id 6666)
('depo0006-6666-6666-6666-666666666666', '66666666-6666-6666-6666-666666666666', 50000.00, 0.00, '2023-05-20', '2024-05-20', 0.12, 'UAH', 'ACTIVE', 0.18, 0.015, 'StandardDeposit'),
-- Вікторія (id 8888) - Капіталізація
('depo0008-8888-8888-8888-888888888888', '88888888-8888-8888-8888-888888888888', 100000.00, 5200.00, '2022-10-10', '2024-10-10', 0.11, 'UAH', 'ACTIVE', 0.18, 0.015, 'CapitalizationDeposit'),
-- Тетяна (id eeee) - Валютний
('depo0014-eeee-eeee-eeee-eeeeeeeeeeee', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 2000.00, 0.00, '2023-09-01', '2024-09-01', 0.03, 'USD', 'ACTIVE', 0.18, 0.015, 'StandardDeposit');


-- =============================================
-- ЧАСТИНА 4: КРЕДИТИ (Loan)
-- =============================================
INSERT INTO loan (id, client_id, original_sum, current_balance, open_date, close_date, next_payment_date, term_month, payment_day, monthly_payment, interest_rate, monthly_rate, currency, status, overdue_sum, change) VALUES
-- Тарас (id 1111)
('loan0001-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 20000.00, 18000.00, '2023-06-01', '2024-06-01', '2023-11-01', 12, 1, 1800.50, 0.25, 0.0208, 'UAH', 'ACTIVE', 0.00, 0.00),
-- Сергій (id 7777)
('loan0007-7777-7777-7777-777777777777', '77777777-7777-7777-7777-777777777777', 40000.00, 35000.00, '2023-08-01', '2024-08-01', '2023-11-01', 12, 1, 3800.00, 0.20, 0.016, 'UAH', 'ACTIVE', 0.00, 0.00),
-- Олег (id 9999) - з прострочкою
('loan0009-9999-9999-9999-999999999999', '99999999-9999-9999-9999-999999999999', 10000.00, 9500.00, '2023-01-15', '2024-01-15', '2023-10-15', 12, 15, 950.00, 0.30, 0.025, 'UAH', 'ACTIVE', 500.00, 0.00),
-- Євген (id ffff)
('loan0015-ffff-ffff-ffff-ffffffffffff', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 200000.00, 190000.00, '2023-07-20', '2026-07-20', '2023-11-20', 36, 20, 8500.00, 0.18, 0.015, 'UAH', 'ACTIVE', 0.00, 0.00);


-- =============================================
-- ЧАСТИНА 5: ТРАНЗАКЦІЇ (Transaction)
-- =============================================
INSERT INTO transaction (id, open_date, open_time, sum, currency, operation_info, sign, account_id_from, account_id_to, status) VALUES
-- Поповнення картки Тараса
('tr000000-1111-1111-1111-111111111111', '2023-10-25', '14:30:00', 500.00, 'UAH', 'Поповнення через термінал', '+', NULL, 'card0001-1111-1111-1111-111111111111', 'SUCCESS'),
-- Зарплата Олени
('tr000001-2222-2222-2222-222222222222', '2023-10-20', '10:00:00', 5000.00, 'UAH', 'Зарплата', '+', NULL, 'card0002-2222-2222-2222-222222222222', 'SUCCESS'),
-- Витрата Олени
('tr000002-2222-2222-2222-222222222222', '2023-10-21', '18:30:00', 1200.00, 'UAH', 'Сільпо', '-', 'card0002-2222-2222-2222-222222222222', NULL, 'SUCCESS'),
-- Переказ від Ірини до Олександра
('tr000003-4444-4444-4444-444444444444', '2023-10-22', '12:15:00', 200.00, 'UAH', 'За обід', '-', 'card0004-4444-4444-4444-444444444444', 'card0011-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'SUCCESS'),
-- Невдала транзакція Ірини
('tr000004-4444-4444-4444-444444444444', '2023-10-23', '02:00:00', 10000.00, 'UAH', 'Купівля iPhone', '-', 'card0004-4444-4444-4444-444444444444', NULL, 'FAILED'),
-- Погашення кредиту Дмитра
('tr000005-5555-5555-5555-555555555555', '2023-10-24', '09:00:00', 1500.00, 'UAH', 'Оплата кредиту', '-', 'card0005-5555-5555-5555-555555555555', NULL, 'SUCCESS');