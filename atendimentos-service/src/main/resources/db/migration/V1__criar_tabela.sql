CREATE TABLE atendimentos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    relato_id BIGINT NOT NULL,
    equipe VARCHAR(100) NOT NULL,
    responsavel VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    prioridade VARCHAR(30) NOT NULL,
    status VARCHAR(24) NOT NULL,
    criado_em DATETIME(6) NOT NULL
);
