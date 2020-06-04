package demo.auth.vo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserVo implements UserDetails {
  
    private static final long serialVersionUID = 1L;

    private String username; 
    private String password;
    private String regDtm;
    private String name;
    private List<GrantedAuthority> authorities; 
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true; 
    private boolean credentialsNonExpired = true; 
    private boolean enabled = true;

}