DROP TABLE IF EXISTS document;

CREATE TABLE document
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    text   TEXT         NOT NULL
);