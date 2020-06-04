package demo.front.main.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/index")
    public ModelAndView list() throws Exception  {
        ModelAndView mav = new ModelAndView("front/main/index");

        try {
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }
}