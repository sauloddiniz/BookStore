CREATE TABLE bookstore.book
(
    ID          BIGSERIAL PRIMARY KEY,
    TITLE       VARCHAR(255) NOT NULL,
    DESCRIPTION TEXT,
    CATEGORY    VARCHAR(255),
    AUTHOR_ID   BIGINT       NOT NULL,
    CONSTRAINT FK_BOOK_AUTHOR FOREIGN KEY (AUTHOR_ID)
        REFERENCES bookstore.author (ID) ON DELETE CASCADE
);

COMMENT ON COLUMN bookstore.book.ID IS 'Identificador único do livro';
COMMENT ON COLUMN bookstore.book.TITLE IS 'Título do livro';
COMMENT ON COLUMN bookstore.book.DESCRIPTION IS 'Descrição do conteúdo do livro';
COMMENT ON COLUMN bookstore.book.CATEGORY IS 'Categoria do livro';
COMMENT ON COLUMN bookstore.book.AUTHOR_ID IS 'Identificador único do autor referente ao livro';
