package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String telephone;

    @Column
    private String email;

    @Column
    private Boolean gender;

    @Column(name = "college_id")
    private Long collegeId;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_err_code")
    private Integer accessTokenErrCode;

    @Column(name = "return_url")
    private String returnUrl;

    @Column(name = "data_token")
    private String dataToken;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "api_key_expire")
    private Date apiKeyExpire;

    @Column(name = "is_apply")
    private Boolean isApply;

    @Column(name = "oper_user_id")
    private Long operUserId;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}