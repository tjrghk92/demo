package demo.auth.provider;

import demo.auth.adapter.AuthorizationAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    AuthorizationAdapter authorizationAdater;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String memberId = authentication.getPrincipal().toString();
        String memberPass = authentication.getCredentials().toString();
        
        return authorizationAdater.login(memberId, memberPass);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authorizationAdater.supportsAuthClass(authentication);
    }
    
}