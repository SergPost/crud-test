package io.github.SergPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query("select b from Book b where lower(b.title) like concat('%', lower(?1), '%')")
    Page<Book> findByTitle(String search, Pageable pageable);
}
