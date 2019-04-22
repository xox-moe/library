package moe.xox.library.service;

import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.BorrowInfoRepository;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.BorrowInfo;
import moe.xox.library.project.BookStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BorrowService {
    Logger logger = LoggerFactory.getLogger(BorrowService.class);

    @Autowired
    BorrowInfoRepository borrowInfoRepository;

    @Autowired
    BookRepository bookRepository;

    public int borrowBook(long userId,long bookId){
        BorrowInfo oldBorrowInfo = borrowInfoRepository.findAllByUserIdAndBookIdAndIfReturnIsFalse(userId, bookId);
        if(oldBorrowInfo != null)
            return -1; //该书已被这位同学借走 不要重复提交
        Book book = bookRepository.findByBookId(bookId);
        if(book.getBookStatusId() != BookStatusEnum.NORMAL.getId()){
            return -2;//书已经借出
        }
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setUserId(userId);
        borrowInfo.setBookId(bookId);
        borrowInfo.setIfReturn(false);
        borrowInfo.setOutQuality(book.getQuality());
        borrowInfo.setOutTime(new Timestamp(System.currentTimeMillis()));

        book.setBookStatusId(1L);

        bookRepository.save(book);
        borrowInfoRepository.save(borrowInfo);
        return 0;
    }

    public int returnBook(long userId,long bookId,long quality ){
        BorrowInfo borrowInfo = borrowInfoRepository.findAllByUserIdAndBookIdAndIfReturnIsFalse(userId, bookId);
        if(borrowInfo == null)
            return -1;//查无此借书记录
        borrowInfo.setBackTime(new Timestamp(System.currentTimeMillis()));
        borrowInfo.setBackQuality(quality);
        Book book = bookRepository.findByBookId(bookId);
        book.setBookStatusId(BookStatusEnum.NORMAL.getId());
        bookRepository.save(book);
        borrowInfoRepository.save(borrowInfo);
        return 0;
    }

    public Page<BorrowInfo> findBorrowInfoByUsrId(long userId, int page, int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
//        return null;
        return borrowInfoRepository.findBorrowInfoByUserId(userId,pageable);
    }

}
