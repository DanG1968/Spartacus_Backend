package novare.com.hk.spartacus.service.wireless;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import novare.com.hk.spartacus.Repo.UsersRepository;
import novare.com.hk.spartacus.domain.Role;
import novare.com.hk.spartacus.domain.Users;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsersRepository usersRepository;


	@Override
	public UserDetails loadUserByUsername(String email) {
		Users userDomain = usersRepository.findByEmail(email);
		if (userDomain == null) {
			throw new UsernameNotFoundException(email);
		}
		
		List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
		
		for(Role role: userDomain.getRoles()) {
			auths.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new User(userDomain.getEmail(), userDomain.getPassword(), auths);
	}
}
