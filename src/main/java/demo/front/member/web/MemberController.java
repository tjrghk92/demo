package demo.front.member.web;


import javax.annotation.Resource;

import demo.auth.adapter.UserAdp;
import demo.custom.adapter.MapAdp;
import demo.custom.map.CstMap;
import demo.custom.service.impl.FileServiceImpl;
import demo.front.member.service.impl.MemeberServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource(name = "memberService")
    private MemeberServiceImpl memberService;
  
    @Resource(name = "fileService")
	private FileServiceImpl fileService;

    /* 회원리스트 */
    @RequestMapping("/list")
    public ModelAndView list() throws Exception  {
        ModelAndView mav = new ModelAndView("front/member/index");
    
        return mav;
    }

     /* 회원상세 */
     @RequestMapping("/write")
     public ModelAndView write() throws Exception  {
         ModelAndView mav = new ModelAndView("front/member/index");
     
         return mav;
     }

     /* 회원상세 */
     @RequestMapping("/api/write")
     public ModelAndView write(MapAdp adapter) throws Exception  {
        ModelAndView mav = new ModelAndView("jsonView");
 
        try {
            CstMap rtnMap =  memberService.selectMemberDtl(adapter.getCstMap());
            mav.addObject("rtnMap", rtnMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
     }

     /* 회원리스트 */
     @RequestMapping("/api/list")
     public ModelAndView list(MapAdp adapter) throws Exception  {
         ModelAndView mav = new ModelAndView("jsonView");
 
         try {
            CstMap rtnMap =  memberService.selectMemberList(adapter.getCstMap());
            mav.addObject("rtnMap", rtnMap);
         } catch (Exception e) {
            e.printStackTrace();
         }
         return mav;
     }

     /* 회원 수정 */
     @RequestMapping("/update")
     public ModelAndView update(MapAdp adapter, MultipartHttpServletRequest multiRequest) throws Exception  {
        ModelAndView mav = new ModelAndView("front/blank/blank");
 
         try {
            fileService.deleteFiles(adapter.getCstMap(),"atchFileImg");
            fileService.insertFiles(adapter.getCstMap(),"atchFileImg", multiRequest);
            int rtnCnt =  memberService.updateMember(adapter.getCstMap());
            if(rtnCnt > 0){
                mav.addObject("url", "./write?no=" + adapter.getCstMap().getString("no"));
                mav.addObject("msg", "수정 되었습니다.");
             }else{
                mav.addObject("url", "./write?no=" + adapter.getCstMap().getString("no"));
                mav.addObject("msg", "수정이 실패하였습니다.");
             }
         } catch (Exception e) {
             mav.addObject("url", "./write?no=" + adapter.getCstMap().getString("no"));
             mav.addObject("msg", "수정이 실패하였습니다.");
         }
         return mav;
     }

    /* 회원가입 */
    @RequestMapping("/signup")
    public ModelAndView signup() throws Exception  {
        ModelAndView mav = new ModelAndView("front/member/index");
        
        try {
            if(UserAdp.isAuthenticated()){
                mav.setViewName("front/blank/blank");
                mav.addObject("url", "/member/index");
                mav.addObject("msg", "이미 로그인 되었습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

     /* 회원가입 처리*/
     @PostMapping("/signupProcess")
     public ModelAndView signupProcess(MapAdp adapter, MultipartHttpServletRequest multiRequest) throws Exception  {
         ModelAndView mav = new ModelAndView("front/blank/blank");

         try {
            fileService.insertFiles(adapter.getCstMap(), "atchFileImg", "image", multiRequest);
            int rtnCnt = memberService.insertMember(adapter.getCstMap());
        
            if(rtnCnt > 0){
               mav.addObject("url", "./login");
               mav.addObject("msg", "회원가입 되었습니다.");
            }else{
               mav.addObject("url", "./signup");
               mav.addObject("msg", "회원가입이 실패하였습니다.");
            }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return mav;
     }

    /* 로그인 */
    @RequestMapping("/login")
    public ModelAndView login(MapAdp adapter) throws Exception  {
        CstMap cstMap = adapter.getCstMap();
        ModelAndView mav = new ModelAndView("front/member/index");

        try {
            if(UserAdp.isAuthenticated()){
                mav.setViewName("front/blank/blank");
                mav.addObject("url", "/member/index");
                mav.addObject("msg", "이미 로그인 되었습니다.");
            }
    
            if(cstMap.get("error") != null){
                mav.setViewName("front/blank/blank");
                mav.addObject("url", "./login");
                mav.addObject("msg", "아이디 패스워드가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

     /* 로그아웃 */
     @RequestMapping("/logoutProcess")
     public ModelAndView logout() throws Exception  {
         ModelAndView mav = new ModelAndView("front/blank/blank");

         try {
            mav.addObject("url", "/main/index");
            mav.addObject("msg", "로그아웃 되었습니다.");
         } catch (Exception e) {
             e.printStackTrace();
         }
         return mav;
     }

     /* 접근거부 */
    @RequestMapping("/denied")
    public ModelAndView denied(MapAdp adapter) throws Exception  {
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