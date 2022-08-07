package com.travel.travel_on.auth;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.model.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtUserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = userRepository.findByRealId(username);
		if (result.isPresent()) {
			User user = result.get();
			if(user != null) {
				JwtUserDetails userDetails = new JwtUserDetails(user);
				return userDetails;
			}
		}
    		return null;
    }
}
