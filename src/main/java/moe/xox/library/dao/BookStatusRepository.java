package moe.xox.library.dao;

import moe.xox.library.dao.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookStatusRepository extends JpaRepository<BookStatus,Long> {


    @Query(nativeQuery = true,value = "select book_status_id, book_status_name, status\n" +
            "from book_status\n" +
            "where book_status.status = true\n" +
            "order by book_status_id desc; ")
    List<BookStatus> findAllOrderByBookStatusIdDesc();

}
