CREATE SCHEMA bookstore;

CREATE TABLE bookstore.author
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

COMMENT
ON COLUMN bookstore.author.ID IS 'Identificador único do autor';
COMMENT
ON COLUMN bookstore.author.NAME IS 'Nome do autor';
