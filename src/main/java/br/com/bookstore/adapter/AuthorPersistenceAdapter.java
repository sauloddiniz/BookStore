package br.com.bookstore.adapter;

import br.com.bookstore.adapter.output.AuthorPersistencePort;
import br.com.bookstore.adapter.persistence.entity.mapper.AuthorMapper;
import br.com.bookstore.adapter.persistence.repository.AuthorRepository;
import br.com.bookstore.core.domain.Author;
import br.com.bookstore.core.exception.AuthorNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorPersistenceAdapter implements AuthorPersistencePort {

    private final AuthorRepository authorRepository;

    public AuthorPersistenceAdapter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<Author> getListAuthors() {
        return authorRepository.findAll().map(AuthorMapper::toModel);
    }

    @Override
    public Mono<Author> getAuthorById(Long id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(id.toString())))
                .map(AuthorMapper::toModel);

    }

    @Override
    public Mono<Author> saveAuthor(Author author) {
        return authorRepository
                .save(AuthorMapper.toEntity(author))
                .map(AuthorMapper::toModel);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
