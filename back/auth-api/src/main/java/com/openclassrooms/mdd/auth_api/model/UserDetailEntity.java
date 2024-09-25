package com.openclassrooms.mdd.auth_api.model;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.NonNull;

public class UserDetailEntity extends UserEntity implements UserDetails {

    public UserDetailEntity(@NonNull String name, @NonNull String email, @NonNull String password) {
        super(name, email, password);
        // TODO Auto-generated constructor stub
    }

    public UserDetailEntity(UserEntity userEntity) {
        super(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword());
        BeanUtils.copyProperties(userEntity, this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    // @Override
    // public boolean isAccountNonExpired() {
    //     return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    //     return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return true;
    // }

    // @Override
    // public boolean isEnabled() {
    //     return true;
    // }

}