-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

CREATE
DATABASE db_sistema_operacoes;

USE
db_sistema_operacoes;

CREATE TABLE TB_USUARIOS
(
    id    bigint PRIMARY KEY AUTO_INCREMENT,
    nome  varchar(100) not null,
    idade integer      not null,
    email varchar(150) not null,
    senha varchar(100) not null
);

CREATE TABLE TB_OPERACOES
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    nome         varchar(100) not null,
    descricao    varchar(150) not null,
    categoria    varchar(100) not null,
    autenticacao varchar(100) not null,
    permissao    varchar(100) not null,
    usuario_id   bigint       not null references TB_USUARIOS (id),
    dateTime     timestamp    not null
);

CREATE TABLE TB_REQUISICAO
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    usuario_id bigint    not null references TB_USUARIOS (id),
    dateTime   timestamp not null
);

CREATE TABLE TB_RESPOSTA
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    statusCode   integer      not null,
    mensagem     varchar(100) not null,
    transicao_id bigint       not null references TB_REQUISICAO (id),
    dateTime     timestamp    not null
)