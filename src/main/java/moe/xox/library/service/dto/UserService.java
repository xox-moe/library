package moe.xox.library.service.dto;

import moe.xox.library.dao.UserRepository;
import moe.xox.library.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Set<String> listRoleNamesByEmail(String email) {
        return userRepository.listRoleNamesByEmail(email);
    }
}
