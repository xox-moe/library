package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.BookKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookKindRepository extends JpaRepository<BookKind, Integer> {

    List<BookKind> findAllByStatusIsTrue();

    @Query(nativeQuery = true,value = "select role_id as roleId, role_name as roleName " +
            "from role where status = true ")
    List<JSONObject> listAllRole();
}
