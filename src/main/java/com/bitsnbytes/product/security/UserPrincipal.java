package com.bitsnbytes.product.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bitsnbytes.product.entity.User;

public class UserPrincipal implements UserDetails{
	
	private User user;
	
	public UserPrincipal(User user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
    public boolean isAccountNonExpired() {
        return true; // or fetch from user field if you track expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or fetch from user field
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // or fetch from user field
    }

    @Override
    public boolean isEnabled() {
        return true; // or fetch from user.isEnabled()
    }
}
