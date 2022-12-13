INSERT INTO USERS (LOGIN, PASSWORD, ROLE)
VALUES ('admin', 'pass', 'ADMIN');

INSERT INTO USERS (LOGIN, PASSWORD, ROLE)
VALUES ('Vadzim', 'pass', 'STAFF');

------------------------------------------------------------------------------------------------------------------------
INSERT INTO BANKS (AMOUNT, TITLE)
VALUES (12000000, 'ADMIN BANK');

INSERT INTO CUSTOMERS (DATE_OF_BIRTH, NAME, SURNAME, PHONE_NUMBER, PASSWORD)
VALUES ('2000-02-20', 'ADMIN', 'ADMIN', '1 23 1234567', 'pass');

INSERT INTO BANK_ACCOUNTS (AMOUNT, CURRENCY, IBAN, TITLE, TYPE, BANK_ID, CUSTOMER_ID)
VALUES (12000000, 'USD', 'US23AA1234567', 'ADMIN ADMIN', 'DEBIT', 1, 1);

INSERT INTO BANK_CARDS (CVS, EXPIRATION_DATE, NUMBER, BANK_ACCOUNT_ID)
VALUES ('111', '2111-11-11', '1234567890987654', 1);

UPDATE BANK_ACCOUNTS
SET BANK_CARD_ID = 1
WHERE ID = 1;

-- INSERT INTO BANK_PAYMENTS (AMOUNT, CURRENCY, RECIPIENT_BANK_ACCOUNT_ID, BANK_ACCOUNT_ID) VALUES (0, 12000000, 'USD', 1, 1);

------------------------------------------------------------------------------------------------------------------------
-- BELARUS
INSERT INTO BANKS (AMOUNT, TITLE)
VALUES (1500000, 'CJSC ALFA-BANK');

-- SWEDEN
INSERT INTO BANKS (AMOUNT, TITLE)
VALUES (1234567, 'BNP PARIBAS OSLO BRANCH');

-- USA
INSERT INTO BANKS (AMOUNT, TITLE)
VALUES (999999999, 'WESTERN BANK OF LAS CRUCES');

------------------------------------------------------------------------------------------------------------------------
INSERT INTO CUSTOMERS (DATE_OF_BIRTH, NAME, SURNAME, PHONE_NUMBER, PASSWORD)
VALUES ('2000-05-25', 'VADZIM', 'MATSIUSHONAK', '375 44 1452003', 'pass');

INSERT INTO CUSTOMERS (DATE_OF_BIRTH, NAME, SURNAME, PHONE_NUMBER, PASSWORD)
VALUES ('1988-12-17', 'Walter', 'Rodriguez', '46 0739 5066448', 'pass');

INSERT INTO CUSTOMERS (DATE_OF_BIRTH, NAME, SURNAME, PHONE_NUMBER, PASSWORD)
VALUES ('1982-11-24', 'Bradley', 'Cameron', '1 773 8009725', 'pass');

------------------------------------------------------------------------------------------------------------------------
INSERT INTO BANK_ACCOUNTS (AMOUNT, CURRENCY, IBAN, TITLE, TYPE, BANK_ID, CUSTOMER_ID)
VALUES (150000, 'BYN', 'BY44VM1452003', 'VADZIM MATSIUSHONAK', 'DEBIT', 2, 2);

INSERT INTO BANK_ACCOUNTS (AMOUNT, CURRENCY, IBAN, TITLE, TYPE, BANK_ID, CUSTOMER_ID)
VALUES (4136.6, 'EUR', 'SE0739WR5066448', 'Walter Rodriguez', 'DEBIT', 3, 3);

INSERT INTO BANK_ACCOUNTS (AMOUNT, CURRENCY, IBAN, TITLE, TYPE, BANK_ID, CUSTOMER_ID)
VALUES (137474.91, 'USD', 'US773BC8009725', 'Bradley Cameron', 'DEBIT', 4, 4);

------------------------------------------------------------------------------------------------------------------------
-- 1234 VISA CJSC ALFA-BANK
INSERT INTO BANK_CARDS (CVS, EXPIRATION_DATE, NUMBER, BANK_ACCOUNT_ID)
VALUES ('123', '2026-10-01', '4585227889907279', 2);
UPDATE BANK_ACCOUNTS
SET BANK_CARD_ID = 2
WHERE ID = 2;

-- 4192 VISA BNP PARIBAS OSLO BRANCH
INSERT INTO BANK_CARDS (CVS, EXPIRATION_DATE, NUMBER, BANK_ACCOUNT_ID)
VALUES ('620', '2023-06-01', '4253453975037505', 3);
UPDATE BANK_ACCOUNTS
SET BANK_CARD_ID = 3
WHERE ID = 3;

-- 6330 MASTERCARD WESTERN BANK OF LAS CRUCES
INSERT INTO BANK_CARDS (CVS, EXPIRATION_DATE, NUMBER, BANK_ACCOUNT_ID)
VALUES ('255', '2028-11-01', '5445896103585199', 4);
UPDATE BANK_ACCOUNTS
SET BANK_CARD_ID = 4
WHERE ID = 4;

------------------------------------------------------------------------------------------------------------------------
INSERT INTO BANK_PAYMENTS (AMOUNT, CURRENCY, RECIPIENT_BANK_ACCOUNT_ID, BANK_ACCOUNT_ID)
VALUES (-150000, 'BYN', 2, 1);

INSERT INTO BANK_PAYMENTS (AMOUNT, CURRENCY, RECIPIENT_BANK_ACCOUNT_ID, BANK_ACCOUNT_ID)
VALUES (-4136.6, 'EUR', 3, 1);

INSERT INTO BANK_PAYMENTS (AMOUNT, CURRENCY, RECIPIENT_BANK_ACCOUNT_ID, BANK_ACCOUNT_ID)
VALUES (-137474.91, 'USD', 4, 1);

------------------------------------------------------------------------------------------------------------------------
