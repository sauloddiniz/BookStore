package br.com.bookstore.adapter.persistence.entity.mapper;

import br.com.bookstore.adapter.persistence.entity.AuthorEntity;
import br.com.bookstore.core.domain.Author;

import java.util.Optional;

public class AuthorMapper {

    private AuthorMapper() {}

    public static AuthorEntity toEntity(Author author) {
        return new AuthorEntity(author.getId(), author.getName());
    }

    public static Author toModel(AuthorEntity authorEntity) {
        return new Author(authorEntity.getId(), authorEntity.getName(),
                Optional.ofNullable(authorEntity.getBooks())
                        .map(books -> books.stream()
                                .map(BookMapper::toBook)
                                .toList())
                        .orElse(null));
    }

    public static Author toModelAndBookIsNull(AuthorEntity authorEntity) {
        return new Author(authorEntity.getId(), authorEntity.getName(),null);
    }
}
