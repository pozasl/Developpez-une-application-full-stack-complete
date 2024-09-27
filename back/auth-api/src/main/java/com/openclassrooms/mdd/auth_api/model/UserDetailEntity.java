package com.openclassrooms.mdd.auth_api.model;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@AllArgsConstructor
public class UserDetailEntity extends UserEntity implements UserDetails {


    public UserDetailEntity(@NonNull String name, @NonNull String email, @NonNull String password) {
        super(null, name, email, password, null, null);
    }

    public UserDetailEntity(UserEntity userEntity) {
        super();
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

}