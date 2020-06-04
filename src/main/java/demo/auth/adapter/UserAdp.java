package demo.auth.adapter;

import demo.auth.vo.UserVo;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserAdp {
    private static boolean isAuthenticated = false;
    private static UserVo user = null;

    public static boolean isAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof UserVo) {
            isAuthenticated = true;
        }else{
            isAuthenticated = false;
        }

        return isAuthenticated;
    }

    public static UserVo getUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof UserVo) {
            user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        }else{
            user = null;
        }

        return user;
    }

}