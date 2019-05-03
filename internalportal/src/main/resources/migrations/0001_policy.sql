--liquibase formatted sql

--changeset policy_admin:1
CREATE TABLE application_underwriting (
     id varchar PRIMARY KEY ,
     version varchar,
     application_id varchar ,
     data varchar,
     status varchar,
     creation_time TIMESTAMP,
     updation_time TIMESTAMP,
     updated_by TIMESTAMP
);

insert into application_underwriting values('1', 'abcd', 'app1', 'underwrtiting data', 'in_progress', LOCALTIMESTAMP(2), null, null);