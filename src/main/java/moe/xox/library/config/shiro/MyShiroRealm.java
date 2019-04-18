package moe.xox.library.config.shiro;

import moe.xox.library.dao.entity.User;
import moe.xox.library.service.dto.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String email = (String) principals.getPrimaryPrincipal();
        Set<String> roles = userService.listRoleNamesByEmail(email);
//        Set<String> permissions = sysUserService.findPermissionByUserId(userId);
        authorizationInfo.setRoles(roles);
//        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("MyShiroRealm.doGetAuthenticationInfo()");
        String email = (String)token.getPrincipal();
//        String password = new String((char[])token.getCredentials());
        User user = userService.findUserByEmail(email);
        if(user == null)
            throw new UnknownAccountException();
//        if(!user.isStatus())
//            throw new LockedAccountException();
        logger.info("----->>userInfo="+user);
        return new SimpleAuthenticationInfo(
                user.getEmail(), //用户名
                user.getPassword(), //密码
                getName()  //realm name
        );
    }
}
