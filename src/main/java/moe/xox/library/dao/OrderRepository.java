package moe.xox.library.dao;

import moe.xox.library.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Order findOrderByBookMessageIdAndUserIdAndStatusIsTrue(Long bookId,Long userId);


    Order findOrderByOrderId(Long orderId);

    Order findOrderByUserIdAndCodeAndStatusIsTrue(long userId, String code);
}
