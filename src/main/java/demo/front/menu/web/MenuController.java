package demo.front.menu.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @RequestMapping("/write")
    public ModelAndView write() throws Exception  {
        ModelAndView mav = new ModelAndView("front/menu/index");
        return mav;
    }
}