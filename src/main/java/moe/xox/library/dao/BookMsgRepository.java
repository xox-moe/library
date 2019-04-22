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
}
