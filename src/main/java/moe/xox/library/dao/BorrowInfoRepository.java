package moe.xox.library.dao;

import moe.xox.library.dao.entity.BorrowInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowInfoRepository extends JpaRepository<BorrowInfo, Integer> {


    BorrowInfo findAllByUserIdAndBookIdAndIfReturnIsFalse(long userId, long bookId);
}
