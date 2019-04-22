package moe.xox.library.dao;

import moe.xox.library.dao.entity.BorrowInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowInfoRepository extends JpaRepository<BorrowInfo, Integer> {


    BorrowInfo findAllByUserIdAndBookIdAndIfReturnIsFalse(long userId, long bookId);



//    @Query(nativeQuery = true,value = "",countQuery = "")
//    List<BorrowInfo> findBorrowInfoByUserId(int userId, Pageable pageable);
}
