package br.com.bookstore.adapter.persistence.repository;

import br.com.bookstore.adapter.persistence.entity.BookEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<BookEntity, Long> {
}
