package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.BookMsgVo;
import moe.xox.library.dao.entity.BookMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookMsgRepository extends JpaRepository<BookMessage,Long> {


    BookMessage getBookMessageByBookMessageId(Long bookMessageId);


    @Query(nativeQuery = true,value = "select bookMsg.book_message_id as bookMessageId,  " +
            "       name as name ,  " +
            "       bookMsg.kind_id as kindId,  " +
            "       author as author,  " +
            "       publisher as publisher,  " +
            "       introduction as introduction,  " +
            "       bookMsg.status as status,  " +
            "       creator_id as creatorId,  " +
            "       create_time as createTime,  " +
            "       ISBN as ISBN,  " +
            "       kind_name as kindName,  " +
            "       bookNum as bookNum  " +
            "from book_message bookMsg  " +
            "         left join book_kind on book_kind.kind_id = bookMsg.kind_id  " +
            "         left join (select book_message_id, count(*) as bookNum from book where book.status = true group by book_message_id) bookNum  " +
            "                   on bookMsg.book_message_id = bookNum.book_message_id " +
            " where bookMsg.status = true ",
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

    @Query(nativeQuery = true,value = "select book_message_id as bookMessageId, name bookMassageName\n" +
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
}
