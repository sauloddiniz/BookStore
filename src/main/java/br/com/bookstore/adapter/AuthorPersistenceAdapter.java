package br.com.bookstore.adapter;

import br.com.bookstore.adapter.output.AuthorPersistencePort;
import br.com.bookstore.adapter.persistence.entity.AuthorEntity;
import br.com.bookstore.adapter.persistence.entity.mapper.AuthorMapper;
import br.com.bookstore.adapter.persistence.repository.AuthorRepository;
import br.com.bookstore.core.domain.Author;
import br.com.bookstore.core.exception.AuthorNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorPersistenceAdapter implements AuthorPersistencePort {

    private final AuthorRepository authorRepository;

    public AuthorPersistenceAdapter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getListAuthors() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        return authorEntities.stream().map(AuthorMapper::toModel).toList();
    }

    @Override
    public Author getAuthorById(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id.toString()));
        return AuthorMapper.toModel(authorEntity);
    }

    @Override
    public Author saveAuthor(Author author) {
        AuthorEntity authorEntity = AuthorMapper.toEntity(author);
        authorEntity = authorRepository.save(authorEntity);
        return AuthorMapper.toModel(authorEntity);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
