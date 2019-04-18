package moe.xox.library.service;

import moe.xox.library.dao.UserRepository;
import moe.xox.library.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<User> listAllUser(int page, int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        return userRepository.findAll(pageable);
    }

}
