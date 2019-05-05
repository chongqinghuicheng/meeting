package com.cqhc.modules.security.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.modules.security.repository.AuthenticationRepository;
import com.cqhc.modules.security.security.AuthenticationInfo;
import com.cqhc.modules.security.security.AuthorizationUser;
import com.cqhc.modules.security.security.JwtUser;
import com.cqhc.modules.security.utils.JwtTokenUtil;
import com.cqhc.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author Jcy
 * @date 2019-4-26
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(value = "/登录界面", description = "登录界面")
public class AuthenticationController {


    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录授权
     * @param authorizationUser
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    @ApiOperation(value = "用户登录")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser){

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        // 查询单位状态是否被禁用
        Long unitId = jwtUser.getUnitId();
        Boolean unitEnable = authenticationRepository.findEnableById(unitId);

        if(unitEnable == false){
            throw new AccountExpiredException("用户所属单位被停用，登录失败!");
        }

        if(!jwtUser.isEnabled()){
            throw new AccountExpiredException("账号已停用，请联系管理员!");
        }

        // if(!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))){
        //     throw new AccountExpiredException("密码错误!");
        // }

        // 限制登录失败5次，达到次数等待（默认3分钟）
        // 获取request
        HttpServletRequest request = RequestHolder.getHttpServletRequest();

        String key = ElAdminConstant.LOGIN_COUNT + "_" + jwtUser.getUsername()
                     + "," + ElAdminConstant.IP + "_" + StringUtils.getIP(request);
        String loginCount = (String)redisTemplate.opsForValue().get(key);

        if(jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))){
            // 登陆成功删除缓存
            redisTemplate.delete(key);
        }else if (StringUtils.isEmpty(loginCount)) {
            // 定义第一次登陆失败的次数为1
            redisTemplate.opsForValue().set(key,1);
            throw new AccountExpiredException("对不起，密码错误，你还有4次机会！");
        }else {
            int errCount = Integer.parseInt(loginCount);
            // 错误达到5次
            if(errCount >= ElAdminConstant.LOGIN_FAIL_COUNT){
                redisTemplate.expire(key,3,TimeUnit.MINUTES);
                throw new AccountExpiredException("对不起，密码错误，没有机会了，请等3分钟再试！");
            }
            // 自增失败次数
            redisTemplate.opsForValue().increment(key,1);
            throw new AccountExpiredException("对不起，密码错误，你还有" + (4 - errCount) + "次机会！");
        }

        // 验证是否为默认密码
        long userId = jwtUser.getId();
        long defCount = authenticationRepository.findByIdAndlastPass(userId);

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        if(defCount > 0)
        {
            return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
        }else{
            return new ResponseEntity<>(new AuthenticationInfo(token,jwtUser),HttpStatus.TEMPORARY_REDIRECT);
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    @ApiOperation(value = "获取用户信息")
    public ResponseEntity getUserInfo(){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

}
