package moe.xox.library.dao;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.dao.entity.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

//    User findByStudentId(long id);

    @Query(nativeQuery = true, value = "select * from USER where nickname like concat('%',:nickname,'%') ")
    List<User> findUserByNickname(@Param("nickname") String nickname);

    List<User> findAllByNickNameLike(String nickName);

    User findByEmailAndPassword(String userName, String password);

    @Query(nativeQuery = true, value = "select distinct role_name  " +
            "from user  " +
            "         left join user_role on user.user_id = user_role.user_id  " +
            "         left join role on role.role_id = user_role.role_id  " +
            "where email = :email ")
    Set<String> listRoleNamesByEmail(@Param("email") String email);

    User findByUserId(Long id);


    @Query(nativeQuery = true, value = "select collection.create_time as collectionTime,\n" +
            "       book_message.book_message_id as bookMessageTime,\n " +
            "       collection_id                as collectionId," +
            "       name as bookMessageName,\n" +
            "       collection.book_message_id as bookMessageId,\n" +
            "       book_message.img_name as imgName,\n" +
            "       kind_name as kindName,\n" +
            "       author as author,\n" +
            "       publisher as publisher,\n" +
            "       introduction as intorduction,\n" +
            "       bookNum.bookNum as bookNum\n" +
            "from collection\n" +
            "         left join book_message on collection.book_message_id = book_message.book_message_id\n" +
            "         left join book_kind on book_message.kind_id = book_kind.kind_id\n" +
            "             left join\n" +
            " (select book_message_id, IFNULL(count(*), 0) as bookNum\n" +
            "                    from book\n" +
            "                    where book_status_id = 4\n" +
            "                    group by book_message_id) bookNum\n" +
            "                   on book_message.book_message_id = bookNum.book_message_id " +
            "where user_id = :userId  ")
    List<JSONObject> listUserCollection(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select user.user_id as userId,\n" +
            "       email        as email,\n" +
            "       nick_name    as nickName,\n" +
            "       birthday     as birthday,\n" +
            "       real_name    as realName,\n" +
            "       grade        as grade,\n" +
            "       department   as department,\n" +
            "       major        as major,\n" +
            "       sex          as sex,\n" +
            "       role.role_id as roleId,\n" +
            "       role_name as roleName\n" +
            "from user left join user_role on user.user_id = user_role.user_id left join role on  role.role_id = user_role.role_id " +
            " where  `user`.user_id like concat('%', :userId, '%') and real_name like concat('%', :realName, '%') and nick_name  like concat('%', :nickName, '%')" +
            "  and email like concat('%', :email, '%') and grade like concat('%', :grade, '%') and department like concat('%', :department, '%') and " +
            " major like concat('%', :major, '%') and role.role_id like concat('%', :roleId, '%') ",
            countQuery = "select count(*) from user " +
                    " where  `user`.user_id like concat('%', :userId, '%') and real_name like concat('%', :realName, '%') and nick_name  like concat('%', :nickName, '%')" +
                    "  and email like concat('%', :email, '%') and grade like concat('%', :grade, '%') and department like concat('%', :department, '%') and " +
                    " major like concat('%', :major, '%') and role.role_id like concat('%', :roleId, '%') ")
    Page<JSONObject> listAllUser(@Param("userId") String userId,
                                 @Param("realName") String realName,
                                 @Param("nickName") String nickName,
                                 @Param("email") String email,
                                 @Param("grade") String grade,
                                 @Param("department") String department,
                                 @Param("major") String major,
                                 @Param("roleId") String roleId, Pageable pageable);
    @Query(nativeQuery = true, value = "select distinct user_role.role_id  " +
            "from user  " +
            "         left join user_role on user.user_id = user_role.user_id  " +
            "         left join role on role.role_id = user_role.role_id  " +
            "where email = :email ")
    Set<String> listRoleIdNamesByEmail(String email);

//    @Update(value = "update  user_role  set  role_id =  :roleId , status = 1  where user_id = :userId ")
//    void updateUserRole(Long userId, Long roleId);
}
