package demo.front.blank.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import demo.custom.adapter.MapAdp;

@RestController
@RequestMapping("/blank")
public class BlankController {

    @RequestMapping("/")
    public ModelAndView blank(MapAdp adapter) throws Exception  {
        ModelAndView mav = new ModelAndView("front/blank/blank");

        try {
            mav.addObject("url", adapter.getCstMap().getString("url"));
            mav.addObject("msg", adapter.getCstMap().getString("msg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }
}