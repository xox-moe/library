package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.BookMsgVo;
import moe.xox.library.dao.entity.BookMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookMsgRepository extends JpaRepository<BookMessage,Long> {


    BookMessage getBookMessageByBookMessageId(Long bookMessageId);


    @Query(nativeQuery = true,value = "select bookMsg.book_message_id     as bookMessageId,\n" +
            "       name                        as name,\n" +
            "       bookMsg.kind_id             as kindId,\n" +
            "       author                      as author,\n" +
            "       publisher                   as publisher,\n" +
            "       introduction                as introduction,\n" +
            "       bookMsg.status              as status,\n" +
            "       creator_id                  as creatorId,\n" +
            "       create_time                 as createTime,\n" +
            "       ISBN                        as ISBN,\n" +
            "       kind_name                   as kindName,\n" +
            "       IFNULL(freeNum.freeNum, 0)  as bookNum,\n" +
            "       IFNULL(bookNum.totalNum, 0) as totalNum\n" +
            "from book_message bookMsg\n" +
            "         left join book_kind on book_kind.kind_id = bookMsg.kind_id\n" +
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
            "where bookMsg.status = true ",
            countQuery = "select count(*) from book_message where book_message.status = true ;")
    Page<JSONObject> listBookMsgManageInfo(Pageable pageable);

    @Query(nativeQuery = true,value = "\n" +
            "select topTen.book_message_id as bookMessageId,\n" +
            "       borrowCount,\n" +
            "       name,\n" +
            "       kind_id as kindId,\n" +
            "       author,\n" +
            "       publisher,\n" +
            "       introduction,\n" +
            "       status,\n" +
            "       creator_id as creatorId,\n" +
            "       create_time as createTime,\n" +
            "       ISBN,\n" +
            "       bookNum.bookNum\n" +
            "from (\n" +
            "         select book.book_message_id, count(*) borrowCount\n" +
            "         from borrow_info\n" +
            "                  left join book on book.book_id = borrow_info.book_id\n" +
            "                  left join book_message on book.book_message_id = book_message.book_message_id\n" +
            "         where out_time > date_sub(current_time, interval 7 day)\n" +
            "         group by book_message.book_message_id\n" +
            "         order by borrowCount desc\n" +
            "         limit 10) topTen\n" +
            "         left join book_message on topTen.book_message_id = book_message.book_message_id\n" +
            "left join (select book_message_id, IFNULL(count(*), 0) as bookNum\n" +
            "                             from book\n" +
            "                             where status = true\n" +
            "                             group by book_message_id) bookNum\n" +
            "                            on book_message.book_message_id = bookNum.book_message_id")
    List<JSONObject> listTopTenBook();

    @Query(nativeQuery = true,value = "select book_message_id as bookMessageId, name bookMassageName , author as author,publisher as publisher  \n" +
            "from book_message\n" +
            "where status = true;")
    List<JSONObject> listAllBookMsgIdAndName();

    @Query(nativeQuery = true,value = "select *\n" +
            "from book_message where status = true order by rand() limit :limit ;")
    List<JSONObject> listBookMsgRandom(int limit);

    @Query(nativeQuery = true,value = "select book_message_id as bookMessageId,\n" +
            "       name as bookMassageName,\n" +
            "       author,\n" +
            "       publisher,\n" +
            "       introduction\n" +
            "from book_message\n" +
            "where (name like concat('%', '', '%') or publisher like concat('%', '', '%') or\n" +
            "       introduction like concat('%', '', '%') or author like concat('%', '', '%'))\n" +
            "  and status = true;")
    List<JSONObject> listBookMsgHomePage();

    @Query(nativeQuery = true,value = "select author , publisher ,book_message_id as bookMessageId from  book_message where book_message_id = :bookMessageId ")
    JSONObject getAuthorAndPublisherByBookMsgId(@Param("bookMessageId") Long bookMessageId);
}
