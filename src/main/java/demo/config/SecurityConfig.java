package demo.config;

import java.net.URLEncoder;

import demo.auth.handler.accessDeniedHandler;
import demo.auth.handler.authenticateHandler;
import demo.auth.provider.AuthProvider;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AuthProvider authProvider;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/js/**","/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                //.antMatchers("/admin/**").hasRole("ADMIN")
                //.antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/member/list").hasRole("SPECIAL")
                .antMatchers("/member/write").hasRole("SPECIAL")
                .anyRequest().permitAll()
                .and()
               
                .csrf()
                //csrf 설정
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    //.disable()
                .and()
                .formLogin()
                //  로그인 설정
                    .loginPage("/member/login")
                    .loginProcessingUrl("/member/loginProcess")
                    .failureUrl("/member/login?error") 
                    .defaultSuccessUrl("/blank/?url=/main/index&msg=" + URLEncoder.encode("로그인 되었습니다.", "UTF-8"))
                    .usernameParameter("memberId")
                    .passwordParameter("memberPass")
                    .permitAll()
                    .and()
                //  로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/member/logoutProcess")
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling()
                    .accessDeniedHandler(new accessDeniedHandler())
                    .authenticationEntryPoint(new authenticateHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}