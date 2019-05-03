DROP DATABASE internalportal;

DROP ROLE internalportal ;

CREATE ROLE internalportal LOGIN PASSWORD 'internalportal' SUPERUSER CREATEDB VALID UNTIL 'infinity';

CREATE DATABASE internalportal WITH ENCODING='UTF8' OWNER = internalportal;