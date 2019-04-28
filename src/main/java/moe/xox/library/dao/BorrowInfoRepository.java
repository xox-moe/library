package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.BorrowInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowInfoRepository extends JpaRepository<BorrowInfo, Integer> {


    BorrowInfo findAllByUserIdAndBookIdAndIfReturnIsFalse(long userId, long bookId);

    @Query(nativeQuery = true,value = "\n" +
            "select borrow_id as borrowId,\n" +
            "       user_id as userId,\n" +
            "       if_return as ifReturn,\n" +
            "       out_time as outTime,\n" +
            "       out_quality as outQuality,\n" +
            "       back_time as backTime,\n" +
            "       bakc_quality as backQuality,\n" +
            "       book.book_id as bookId,\n" +
            "       book_message.book_message_id as  bookMessageId,\n" +
            "       name as bookName,\n" +
            "       book_kind.kind_id as kindId,\n" +
            "       book_kind.kind_name as kindName,\n" +
            "       author as author,\n" +
            "       publisher as publisher,\n" +
            "       introduction as introduction,\n" +
            "       ISBN as ISBN\n" +
            "from borrow_info left join book on book.book_id = borrow_info.book_id left join book_message on book.book_message_id = book_message.book_message_id\n" +
            "left join book_kind on book_kind.kind_id = book_message.kind_id where user_id = '1' ",countQuery = "\n" +
            "select count(*)\n" +
            "from borrow_info left join book on book.book_id = borrow_info.book_id left join book_message on book.book_message_id = book_message.book_message_id\n" +
            "left join book_kind on book_kind.kind_id = book_message.kind_id where user_id = '1' ")
    Page<JSONObject> findBorrowInfoByUserId(@Param("userId") long userId, Pageable pageable);

    BorrowInfo findBorrowInfoByBookId(Long bookId);

    BorrowInfo findBorrowInfoByBookIdAndIfReturnIsFalse(Long bookId);


//    @Query(nativeQuery = true,value = "",countQuery = "")
//    List<BorrowInfo> findBorrowInfoByUserId(int userId, Pageable pageable);
}
