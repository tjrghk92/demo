package demo.config;

import java.util.Arrays;
import java.util.List;

import demo.custom.interceptor.IpInterceptor;
import demo.custom.interceptor.PageInterceptor;
import demo.resolver.ArgumentResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
    
    @Autowired
    PageInterceptor pageInterceptor;

    @Autowired
    IpInterceptor ipInterceptor;
    
    @Autowired
    public PropertiesConfig propertiesConfig;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/main/index");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + propertiesConfig.getData("file", "uploadPath") + "upload\\");
                //.addResourceLocations("file:/DATA/video/"); //리눅스 root에서 시작하는 폴더 경로
    }

    @Override  
	public void addInterceptors(InterceptorRegistry registry) {
        List<String> URL_PATTERNS = Arrays.asList("/member/**", "/main/**", "/menu/**");
        //List<String> URL_EXCLUDE_PATTERNS = Arrays.asList("/member/login");
        registry.addInterceptor(pageInterceptor)  
                .addPathPatterns(URL_PATTERNS);
                //.excludePathPatterns(URL_EXCLUDE_PATTERNS);

        registry.addInterceptor(ipInterceptor)  
                .addPathPatterns("/**");
    }
    
    @Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }

}