CREATE TABLE relatos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(80) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    categoria VARCHAR(40) NOT NULL,
    logradouro VARCHAR(120) NOT NULL,
    bairro VARCHAR(80) NOT NULL,
    status VARCHAR(24) NOT NULL,
    criado_em DATETIME(6) NOT NULL
);
