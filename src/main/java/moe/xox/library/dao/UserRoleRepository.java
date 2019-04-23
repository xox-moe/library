package moe.xox.library.dao;

import moe.xox.library.dao.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole,Long> {

    UserRole findUserRoleByUserId(Long userId);

}
