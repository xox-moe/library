package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findNoticeByNoticeId(Long id);

    @Query(nativeQuery = true, value = "select notice_id as noticeId,\n" +
            "       message   as message\n" +
            "from notice\n" +
            "where current_timestamp between begin_time and end_time")
    List<JSONObject> listNowNotice();

    @Query(nativeQuery = true, value = "select * from notice where notice_id like concat('%', :id, '%') ",
            countQuery = "select count(*) from where notice_id like  concat('%', :id, '%') ")
    Page<Notice> findAll(Pageable pageable, @Param("id") String id);

    @Query(nativeQuery = true, value = "select * from notice where notice_id like  concat('%', :id, '%')  and create_time between :beginTime and :endTime",
            countQuery = "select count(*) from where notice_id like  concat('%', :id, '%')  and create_time between :beginTime and :endTime")
    Page<Notice> findAll(Pageable pageable,
                         @Param("id") String id,
                         @Param("beginTime") LocalDateTime beginTime,
                         @Param("endTime") LocalDateTime endTime);
}
