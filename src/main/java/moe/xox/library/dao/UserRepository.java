package moe.xox.library.dao;


import com.alibaba.fastjson.JSONObject;
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

    User findByUserId(Integer id);


    @Query(nativeQuery = true,value = "select collection.create_time as collectionTime,\n" +
            "       book_message.book_message_id as bookMessageTime,\n" +
            "       name as bookMessageName,\n" +
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
            "where user_id = :userId;")
    List<JSONObject> listUserCollection(Long userId);
}
