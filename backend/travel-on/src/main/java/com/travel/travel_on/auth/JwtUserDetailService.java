package com.travel.travel_on.auth;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
public class JwtUserDetailService implements UserDetailsService{
	@Autowired
	UserRepository repo;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = repo.findByRealId(username);
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
