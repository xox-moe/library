package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    Page<Book> findAllByStatusIsTrue(Pageable pageable);


    Book findByBookId(Long id);


    @Query(nativeQuery = true,value = "select *\n" +
            "from book left join book_message on book.book_message_id = book_message.book_message_id;",
            countQuery = "select count(*) from book;")
    Page<JSONObject> listAllBookInfo(Pageable pageable);
}
