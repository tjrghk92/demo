package demo.custom.controller;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";
 
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap modelMap) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        modelMap.addAttribute("code", status.toString());
        modelMap.addAttribute("msg", httpStatus.getReasonPhrase());
        modelMap.addAttribute("timestamp", new Date());
        return "front/error/error";
    }
}