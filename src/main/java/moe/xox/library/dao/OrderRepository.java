package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(nativeQuery = true,value = "select *\n" +
            "from `order` where book_message_id = :bookMessageId and user_id = :userId and if_take_away is false and status is true;")
    Order findOrderByBookMessageIdAndUserIdAndStatusIsTrue(@Param("bookMessageId") Long bookMessageId,@Param("userId") Long userId);


    Order findOrderByOrderId(Long orderId);

    Order findOrderByUserIdAndCodeAndStatusIsTrue(long userId, String code);

    @Query(nativeQuery = true,value = "select order_id,\n" +
            "       `order`.book_message_id as bookMessageId,\n" +
            "       user_id as userId,\n" +
            "       order_time as orderTime,\n" +
            "       code as code,\n" +
            "       if_take_away ifTakeAway,\n" +
            "       name as name,\n" +
            "       kind_name as kindName,\n" +
            "       author as author,\n" +
            "       publisher as  publisher,\n" +
            "       introduction as  introduction\n" +
            "from `order`\n" +
            "         left join `book_message` on `order`.book_message_id = book_message.book_message_id\n" +
            "         left join book_kind on book_kind.kind_id = book_message.kind_id\n" +
            "where user_id = :userId order by ifTakeAway, orderTime desc ")
    List<JSONObject> listOrderByUserId(@Param("userId") Long userId);
}
