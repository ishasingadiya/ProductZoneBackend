package com.bitsnbytes.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsnbytes.product.dto.UserDTO;
import com.bitsnbytes.product.entity.User;
import com.bitsnbytes.product.security.JWTUtil;
import com.bitsnbytes.product.service.MyUserDetailsService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private MyUserDetailsService userDetailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		user.setPassword(encoder.encode(user.getPassword()));
		return userDetailService.createUser(user);
	}

	@PostMapping("/login")
	public String login(@RequestBody UserDTO user) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		List<String> roles = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());

		return jwtUtil.generateToken(userDetails.getUsername(), roles);
	}
}
