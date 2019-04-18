package moe.xox.library.dao;


import moe.xox.library.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

//    User findByStudentId(long id);

    @Query(nativeQuery = true,value = "select * from USER where nickname like concat('%',:nickname,'%') ")
    List<User> findUserByNickname(@Param("nickname") String nickname);

    List<User> findAllByNickNameLike(String nickName);

    User findByEmailAndPassword(String userName, String password);

    @Query(nativeQuery = true,value = "select distinct role_name  " +
            "from user  " +
            "         left join user_role on user.user_id = user_role.user_id  " +
            "         left join role on role.role_id = user_role.role_id  " +
            "where email = :email ")
    Set<String> listRoleNamesByEmail(String email);
}
