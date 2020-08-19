package novare.com.hk.spartacus.config;


import novare.com.hk.spartacus.Repo.UsersRepository;
import novare.com.hk.spartacus.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


  @Autowired
  UsersRepository usersRepository;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {

    String username = auth.getName();
    String password = auth.getCredentials().toString();

    Users user = usersRepository.findByName(username);

    if (user == null) {

      throw new BadCredentialsException("User doesn't exist");

    } else if (user.getPassword().equals(password)) {


//            user.setStampLastLogin(new LocalDateTime());

      return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

    } else {

      throw new BadCredentialsException("External system authentication failed");

    }

  }

  @Override
  public boolean supports(Class<?> auth) {

    return auth.equals(UsernamePasswordAuthenticationToken.class);

  }

}
