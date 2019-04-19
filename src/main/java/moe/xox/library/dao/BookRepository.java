package moe.xox.library.dao;

import moe.xox.library.dao.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    Page<Book> findAllByStatusIsTrue(Pageable pageable);


    Book findByBookId(Long id);
}
