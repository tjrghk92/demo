package demo.custom.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.auth.adapter.UserAdp;
import demo.config.PropertiesConfig;
import demo.custom.util.FilterUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PageInterceptor implements HandlerInterceptor {

	@Autowired
	public PropertiesConfig propertiesConfig;
	
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		return true;
    }
    
    @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String viewUrl = modelAndView.getViewName();
        String requestUri = "";
        String strPam = "";

        if (viewUrl != null && !"".equals(viewUrl) && viewUrl.indexOf("error") == -1 && viewUrl.indexOf("blank") == -1 && viewUrl.indexOf("jsonView") == -1){
            requestUri = request.getRequestURI();
            strPam = GetParam(request);
            request.setAttribute("strPam", strPam);
            request.setAttribute("viewUrl", viewUrl);
			request.setAttribute("requestUri", requestUri);

			String index[] = requestUri.split("/");
			request.setAttribute("index", index[1]);
			
			//회원 인증정보
			request.setAttribute("auth", UserAdp.isAuthenticated());
			request.setAttribute("member", UserAdp.getUser());
			
			//공통 프로퍼티
			request.setAttribute("common", propertiesConfig.getCommon());
			
        }
    }

    private String GetParam(HttpServletRequest request){
		Enumeration<String> params = request.getParameterNames();
		
		String strPam = "", name = "";
		int comma = 0;
		
		while (params.hasMoreElements())
		{
			if (comma > 0)
			{
				strPam += "&";
			}

		    name = (String) params.nextElement();

		    strPam += FilterUtil.removeCRLF(FilterUtil.clearXSSMinimum(name)) + "=" +  FilterUtil.removeCRLF(FilterUtil.clearXSSMinimum(request.getParameter(name)));

		    comma++;
		}
		
		return strPam;
	}
	
}