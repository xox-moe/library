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


    @Query(nativeQuery = true,value = "select book_id                     as bookId,\n" +
            "       quality                     as qualityId,\n" +
            "       bookMsg.book_message_id     as bookMessageId,\n" +
            "       name                        as bookMessageName,\n" +
            "       author                      as author,\n" +
            "       publisher                   as publisher,\n" +
            "       introduction                as introduction,\n" +
            "       ISBN                        as ISBN,\n" +
            "       book_kind.kind_id           as kindId,\n" +
            "       kind_name                   as kindName,\n" +
            "       book_kind.status            as status,\n" +
            "       book_status.book_status_id  as bookStatusId,\n" +
            "       book_status_name            as statusName,\n" +
            "       IFNULL(freeNum.freeNum, 0)  as bookNum,\n" +
            "       IFNULL(bookNum.totalNum, 0) as totalNum\n" +
            "from book\n" +
            "         left join book_message bookMsg on book.book_message_id = bookMsg.book_message_id\n" +
            "         left join book_kind on book_kind.kind_id = bookMsg.kind_id\n" +
            "         left join book_status on book.book_status_id = book_status.book_status_id\n" +
            "         left join (select book_message_id, IFNULL(count(*), 0) as freeNum\n" +
            "                    from book\n" +
            "                    where book_status_id = 4\n" +
            "                      and status = true\n" +
            "                    group by book_message_id) freeNum\n" +
            "                   on bookMsg.book_message_id = freeNum.book_message_id\n" +
            "         left join (select book_message_id, IFNULL(count(*), 0) as totalNum\n" +
            "                    from book\n" +
            "                    where status = true\n" +
            "                      and (book_status_id = 4 or book_status_id = 1)\n" +
            "                    group by book_message_id) bookNum\n" +
            "                   on bookMsg.book_message_id = bookNum.book_message_id\n" +
            "where book.status = true " +
            "  and bookMsg.status = true ",
            countQuery = "select count(*)  " +
                    "from book  left join book_message bookMsg on book.book_message_id = bookMsg.book_message_id  " +
                    "where book.status = true and bookMsg.status = true")
    Page<JSONObject> listAllBookInfo(Pageable pageable);

    @Query(nativeQuery = true,value = "select\n" +
            "       bookMsg.book_message_id as bookMessageId,\n" +
            "       name as name,\n" +
            "       bookMsg.kind_id as kindId,\n" +
            "       author as author,\n" +
            "       publisher as publisher,\n" +
            "       introduction introduction,\n" +
            "       ISBN as ISBN,\n" +
            "       kind_name as kindName,\n" +
            "       IFNULL(bookNum,0) as bookNum\n" +
            "from book left join book_message bookMsg on book.book_message_id = bookMsg.book_message_id\n" +
            "left join  book_kind on book_kind.kind_id = bookMsg.kind_id\n" +
            "left join (select book_message_id, IFNULL(count(*),0) as bookNum from book where status = true group by book_message_id) bookNum\n" +
            "                   on bookMsg.book_message_id = bookNum.book_message_id\n" +
            "where book.status = true and bookMsg.status = true\n" +
            "group by bookMsg.book_message_id, name, bookMsg.kind_id, author, publisher, introduction, ISBN, kind_name,bookNum\n" +
            "order by  book.create_time limit 10\n")
    List<JSONObject> listNewBook();
}
