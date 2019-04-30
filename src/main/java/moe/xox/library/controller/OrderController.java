package moe.xox.library.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.OrderRepository;
import moe.xox.library.dao.entity.Order;
import moe.xox.library.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * 用于预定某本书 统计书可借出的量的时候会统计上该本书
 */

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {


    @Autowired
    OrderRepository orderRepository;


    @RequestMapping("orderBook")
    public ReturnBean orderBook(Long bookMessageId){
        Long userId = ShiroUtils.getUserId();
        String code = UUID.randomUUID().toString().substring(0, 5);

        Order oldOrder = orderRepository.findOrderByBookMessageIdAndUserIdAndStatusIsTrue(bookMessageId, userId);

        if(oldOrder != null)
            return getFailure("您已经预约过该书啦,请不要重复预约");

        Order order = new Order(null, bookMessageId, userId, LocalDateTime.now(), code, false,null,true);
        Order orders  = orderRepository.save(order);

        return getSuccess("预约成功", orders, 1);
    }

    @RequestMapping("cancelOrder")
    public ReturnBean cancelOrder(Long orderId){
        Long userId = ShiroUtils.getUserId();
        Order oldOrder = orderRepository.findOrderByOrderId(orderId);
        if (oldOrder.getUserId().equals(userId)) {
            oldOrder.setStatus(false);
            orderRepository.save(oldOrder);
            return getSuccess("取消预约成功");
        }
        return getSuccess("这不是您的预约!");
    }

    @RequestMapping("listMyOrder")
    public ReturnBean listMyOrder() {
        List<JSONObject> list = orderRepository.listOrderByUserId(ShiroUtils.getUserId());
        return getSuccess("OK", list, list.size());
    }

}
