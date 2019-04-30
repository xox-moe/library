package moe.xox.library.service;

import moe.xox.library.dao.*;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.BorrowInfo;
import moe.xox.library.dao.entity.Order;
import moe.xox.library.dao.entity.User;
import moe.xox.library.project.BookStatusEnum;
import moe.xox.library.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class BorrowService {
    Logger logger = LoggerFactory.getLogger(BorrowService.class);

    @Autowired
    BorrowInfoRepository borrowInfoRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;



    @Transactional
    public int borrowBook(String email, long bookId, String code) {

        //根据学号查询教师
        User user = userRepository.findByEmail(email);
        //借出图书
        Long userId = user.getUserId();
        Order order = orderRepository.findOrderByUserIdAndCodeAndStatusIsTrue(userId,code);
        Book book = bookRepository.findByBookId(bookId);

        if (!book.getBookMessageId().equals(order.getBookMessageId())) {
            return -1; //预约与取出的书的类别不符
        }
        if(order.isIfTakeAway()){
            return -2; //该预约已经取走书本了
        }
        if(!book.getBookStatusId().equals(BookStatusEnum.NORMAL.id)){
            return -3;//数据显示本书已经借出,请管理员进行后台检查
        }

        order.setIfTakeAway(true);
        book.setBookStatusId(BookStatusEnum.OUT.id);


        BorrowInfo borrowInfo = new BorrowInfo(null, userId, bookId,false, LocalDateTime.now(), book.getQuality(),false, null, null);


        bookRepository.save(book);
        orderRepository.save(order);
        borrowInfoRepository.save(borrowInfo);
        return 0;

    }

    public int returnBook(long userId,long bookId,long quality ){
        BorrowInfo borrowInfo = borrowInfoRepository.findAllByUserIdAndBookIdAndIfReturnIsFalse(userId, bookId);
        if(borrowInfo == null)
            return -1;//查无此借书记录
        borrowInfo.setBackTime(LocalDateTime.now());
        borrowInfo.setBackQuality(quality);
        borrowInfo.setIfReturn(true);
        Book book = bookRepository.findByBookId(bookId);
        book.setBookStatusId(BookStatusEnum.NORMAL.getId());
        bookRepository.save(book);
        borrowInfoRepository.save(borrowInfo);
        return 0;
    }



    public int backBook(Long bookId, Long quality) {
        Book book = bookRepository.findByBookId(bookId);
        BorrowInfo borrowInfo = borrowInfoRepository.findBorrowInfoByBookIdAndIfReturnIsFalse(bookId);
        if(book == null)
            return -1;
        if(borrowInfo == null)
            return -2;
        book.setBookStatusId(BookStatusEnum.NORMAL.getId());
        bookRepository.save(book);
        borrowInfo.setBackTime(LocalDateTime.now());
        borrowInfo.setBackQuality(quality);
        borrowInfo.setIfReturn(true);
        borrowInfoRepository.save(borrowInfo);
        return 0;
    }
}
