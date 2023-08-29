CREATE TABLE LIVROS(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    NOME_LIVRO VARCHAR(255) NOT NULL,
    AUTOR VARCHAR(255) NOT NULL,
    ISBN INTEGER NOT NULL,
    VALOR DOUBLE NOT NULL,
    ID_VENDEDOR BIGINT NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE COMPRA(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_COMPRADOR BIGINT NOT NULL,
    ID_VENDEDOR BIGINT NOT NULL,
    ID_LIVRO BIGINT NOT NULL,
    VALOR DOUBLE NOT NULL,
    PRIMARY KEY(ID)
);
