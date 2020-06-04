package demo.auth.adapter;

import javax.annotation.Resource;

import demo.auth.vo.UserVo;
import demo.front.member.service.impl.MemeberServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationAdapter {

    @Resource(name = "memberService")
    private MemeberServiceImpl memberService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    public UsernamePasswordAuthenticationToken login(String username, String password) {
        UserVo user = memberService.loadUserByUsername(username);
        
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("password not equal");
        }
        
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        user.setPassword("");
        result.setDetails(user);
       
        return result;
    }

    public boolean supportsAuthClass(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}