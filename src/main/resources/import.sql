insert into person (document, name) values (00763817066, 'Joaquim Jose da Silva Xavier');
insert into person (document, name) values (72567167617, 'Aline Tavares Dias');
insert into person (document, name) values (15558807970, 'João da Silva Lima');
insert into person (document, name) values (42538264405, 'Pedro Alvares de Cabrau');

--CAPTURE USER (PASS 123)
insert into user (id, name, login, password, user_permission, status) values (1, 'Bruno Konzen Stahl', 'bruno', '$2a$10$5BtUNmUjbtRaUmoCDWEgvOBWwmI0/8NTR74/Ast2u517ApTu3283G', 0, true);

--ANALYST USER (PASS 123)
insert into user (id, name, login, password, user_permission, status) values (2, 'João do Amaral', 'joao', '$2a$10$5BtUNmUjbtRaUmoCDWEgvOBWwmI0/8NTR74/Ast2u517ApTu3283G', 1, true);

--APPROVED
insert into credit_proposal (id, person_document, capture_user_id, capture_date, status_user_id, status_date, status, credit_Value, advice) values (1, 15558807970, 1, '2020-06-20', 2, '2020-06-21', 1, 1100.00, 'Great score');

--DISAPPROVED
insert into credit_proposal (id, person_document, capture_user_id, capture_date, status_user_id, status_date, status, credit_Value, advice) values (2, 42538264405, 1, '2020-12-05', 2, '2020-12-06', 2, 32000.00, 'Bad score');

--CANCELED
insert into credit_proposal (id, person_document, capture_user_id, capture_date, status_user_id, status_date, status, credit_Value, advice) values (3, 00763817066, 1, '2021-07-07', null, null, 3, 750.00, 'Gave up');

--ON_APPROVAL
insert into credit_proposal (id, person_document, capture_user_id, capture_date, status_user_id, status_date, status, credit_Value, advice) values (4, 763817066, 1, '2021-09-12', null, null, 0, 1500.00, null);
insert into credit_proposal (id, person_document, capture_user_id, capture_date, status_user_id, status_date, status, credit_Value, advice) values (5, 15558807970, 1, '2021-09-13', null, null, 0, 500.00, null);