package com.cqhc.modules.security.service;

import com.cqhc.modules.security.security.AuthorizationUser;
import com.cqhc.modules.security.security.JwtUser;
import com.cqhc.modules.system.domain.Permission;
import com.cqhc.modules.system.domain.Role;
import com.cqhc.modules.system.domain.User;
import com.cqhc.modules.system.repository.PermissionRepository;
import com.cqhc.modules.system.repository.RoleRepository;
import com.cqhc.modules.system.service.UserService;
import com.cqhc.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "role")
public class JwtPermissionService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Cacheable(key = "'loadPermissionByUser:' + #p0.username")
    public Collection<GrantedAuthority> mapToGrantedAuthorities(User user) {

        System.out.println("--------------------loadPermissionByUser:" + user.getUsername() + "---------------------");

        Set<Role> roles = roleRepository.findByUsers_Id(user.getId());

        Set<Permission> permissions = new HashSet<>();

        permissions.addAll(permissionRepository.findByRoles(roles));

        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }


    //判断该用户是否拥有这个权限
    @Cacheable(key = "'loadPermissionByUser:' + #p0.username")
    public boolean getPermission(String name) {
        //获取该用户
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
        //获取该用户的所有角色
        Set<Role> roles = roleRepository.findByUsers_Id(jwtUser.getId());

        //获取该用户的所有权限
        Set<Permission> permissions = new HashSet<>();
        permissions.addAll(permissionRepository.findByRoles(roles));

        //获取权限表中该字段的权限对象
        Permission permission=permissionRepository.findByName(name);

        //循环判断
        if(permissions.contains(permission)){
            return true;
        }
        return false;
    }
}
