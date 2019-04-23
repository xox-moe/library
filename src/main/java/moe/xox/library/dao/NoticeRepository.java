package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

    Notice findNoticeByNoticeId(Long id);

    @Query(nativeQuery = true,value = "select notice_id as noticeId,\n" +
            "       message   as message\n" +
            "from notice\n" +
            "where current_timestamp between begin_time and end_time")
    List<JSONObject> listNowNotice();
}
