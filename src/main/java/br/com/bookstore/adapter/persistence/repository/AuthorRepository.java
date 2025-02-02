package br.com.bookstore.adapter.persistence.repository;

import br.com.bookstore.adapter.persistence.entity.AuthorEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends R2dbcRepository<AuthorEntity, Long> {
}
