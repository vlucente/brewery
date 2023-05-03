package guru.springframework.sfgrestbrewery.security.auth;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import guru.springframework.sfgrestbrewery.services.UserService;

@Component
@ConditionalOnProperty(
        name="security.auth.type",
        havingValue = "database",
        matchIfMissing = false)
public class DatabaseAuthProvider extends CustomAuthProvider{

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        UserDetails storedUser = userService.loadUserByUsername(username);
        if (!isValidUser(storedUser, password))
            throw new BadCredentialsException("Invalid credentials!!!");
        return new UsernamePasswordAuthenticationToken(username,password, storedUser.getAuthorities());
    }

    private boolean isValidUser(UserDetails storedUser, String password){
        return password != null
                && storedUser.getPassword() != null
                && passwordEncoder.matches(password, storedUser.getPassword());
    }
}
