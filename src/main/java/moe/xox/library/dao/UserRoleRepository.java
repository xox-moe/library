package moe.xox.library.dao;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository  extends JpaRepository<UserRole,Long> {

    UserRole findUserRoleByUserId(Long userId);


    /**
     * 此处等待修改
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true,value = "select * from book_message;",countQuery = "select count(*) from book_message;")
    Page<JSONObject> listUserHistory(Pageable pageable);
}
