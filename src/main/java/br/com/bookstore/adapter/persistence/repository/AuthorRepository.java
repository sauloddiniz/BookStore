package br.com.bookstore.adapter.persistence.repository;

import br.com.bookstore.adapter.persistence.entity.AuthorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<AuthorEntity, Long> {
}
