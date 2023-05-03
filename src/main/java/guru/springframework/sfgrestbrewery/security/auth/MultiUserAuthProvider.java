package guru.springframework.sfgrestbrewery.security.auth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name="security.auth.type",
        havingValue = "map-hard-coded",
        matchIfMissing = false)
public class MultiUserAuthProvider extends CustomAuthProvider{

    private Map<String, String> internalDatabase;

    public MultiUserAuthProvider() {
        this.internalDatabase = new HashMap<>();
        for (int i = 0; i < 10; i ++){
            internalDatabase.put("user"+i,"password"+i);
        }
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        if (!isValidUser(username, password))
            throw new BadCredentialsException("Not valid credentials!!!");
        return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    }

    private boolean isValidUser(String username, String password){
        return username != null
                && password != null
                && internalDatabase.containsKey(username)
                && password.equals(internalDatabase.get(username));
    }
}
