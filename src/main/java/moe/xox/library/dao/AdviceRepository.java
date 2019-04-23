package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdviceRepository extends JpaRepository<Advice,Long> {
    Advice findAdviceByAdviceId(Long adviceId);


    @Query(nativeQuery = true,value = "select advice_id as adviceId,\n" +
            "       message as message,\n" +
            "       create_time as createTime,\n" +
            "       user.user_id as userId,\n" +
            "       real_name as realName\n" +
            "from advice left join user on user.user_id = advice.user_id ",
            countQuery = "select count(*) from advice;")
    Page<JSONObject> findAllAdvice(Pageable pageable);

}
