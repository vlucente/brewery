package guru.springframework.sfgrestbrewery.security.auth;

import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name="security.auth.type",
        havingValue = "hard-coded",
        matchIfMissing = true)
public class HardCodedAuthProvider extends CustomAuthProvider {
    private static final String HARD_CODED_USER = "pippo";
    private static final String HARD_CODED_PWD = "academy";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (!isValidUser(username, password))
            throw new BadCredentialsException("Not valid credentials!!!");
        return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    }

    private boolean isValidUser(String username, String password){
        return HARD_CODED_USER.equals(username) && HARD_CODED_PWD.equals(password);
    }
}
