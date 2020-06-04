package demo.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import demo.custom.adapter.MapAdp;
import demo.custom.map.CstMap;
import demo.custom.util.FilterUtil;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ArgumentResolver implements HandlerMethodArgumentResolver  {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MapAdp.class.isAssignableFrom(parameter.getParameterType()); 
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        
         CstMap cstMap = new CstMap();
         HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
         Enumeration<?> enumeration = request.getParameterNames();

         while (enumeration.hasMoreElements()) 
         {
            String key = (String) enumeration.nextElement();
            String[] values = request.getParameterValues(key);
            if (values != null) 
            {
                for (int q = 0; q < values.length; q++)
                {
                    if(key.indexOf("cntn") > -1 ){
                        values[q] = FilterUtil.clearXSSMinimum(values[q]);
                    }
                    values[q] = FilterUtil.filterXSS(values[q]);
                    values[q] = FilterUtil.filterSqlInjection(values[q]);
                }
                
                cstMap.put(key, (values.length > 1) ? values : values[0]);
            }
         }

        if(request.getAttribute("ip") != null)
        cstMap.put("ip", request.getAttribute("ip"));

        return new MapAdp(cstMap);
    }

}