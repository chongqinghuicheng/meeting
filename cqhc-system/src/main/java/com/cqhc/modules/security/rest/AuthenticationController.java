package com.cqhc.modules.security.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.modules.security.repository.AuthenticationRepository;
import com.cqhc.modules.security.security.AuthenticationInfo;
import com.cqhc.modules.security.security.AuthorizationUser;
import com.cqhc.modules.security.security.JwtUser;
import com.cqhc.modules.security.utils.JwtTokenUtil;
import com.cqhc.utils.EncryptUtils;
import com.cqhc.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

/**
 * @author Jcy
 * @date 2019-4-26
 * 授权、根据token获取用户详细信息
 */
@RestController
@RequestMapping("auth")
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

    /**
     * 登录授权
     * @param authorizationUser
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "${jwt.auth.path}")
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

        if(!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))){
            throw new AccountExpiredException("密码错误!");
        }

        // RedisTemplate redisTemplate = new RedisTemplate();
        // String key = ElAdminConstant.LOGIN_COUNT + "_" + jwtUser.getUsername();
        //
        // if(jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))){
        //     //登陆成功删除缓存
        //     redisTemplate.delete(key);
        // }else{
        //     Object countObj = redisTemplate.opsForValue().get(key);
        //     //定义第一次登陆失败的次数为1
        //     int count = 1;
        //     if (countObj != null) {
        //         count = Integer.parseInt(countObj.toString());
        //         count++;
        //         if (count == ElAdminConstant.LOGIN_FAIL_COUNT) {
        //             // 禁用账号
        //             authenticationRepository.disenabledUser(jwtUser.getUsername());
        //         }
        //     }
        //     redisTemplate.opsForValue().set(key, count, 3, TimeUnit.MINUTES);
        //     throw new BadRequestException("对不起，您登录失败的次数已经超过限制，已冻结");
        // }

        // 验证是否为默认密码
        long userId = jwtUser.getId();
        long count = authenticationRepository.findByIdAndlastPass(userId);

        if(count > 0)
        {
            // 生成令牌
            final String token = jwtTokenUtil.generateToken(jwtUser);

            // 返回 token
            return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text","html",Charset.forName("utf-8")));
        return new ResponseEntity("请您修改默认的密码!",headers,HttpStatus.OK);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo(){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

}
