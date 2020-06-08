
package demo.custom.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.custom.adapter.MapAdp;
import demo.custom.map.CstMap;
import demo.custom.service.impl.FileServiceImpl;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * FileController
 */
    @RestController
    @RequestMapping("/file")
    public class FileController {
	
	@Resource(name="fileService")
	private FileServiceImpl fileService;
	
    /**
	 * 파일 임시 업로드 후 정보조회
	 * 
	 */
	@RequestMapping(value="/api/getUpdoadFileTemp")
	public ModelAndView getUpdoadFileTemp(MapAdp adapter, MultipartHttpServletRequest multiRequest) throws Exception {
        
        ModelAndView mav = new ModelAndView("jsonView");
    	try{
    		mav.addObject("tempFileMap", fileService.getUpdoadFileTemp(multiRequest));
    	}
    	catch (Exception e){
            e.printStackTrace();
		}
    	return mav;
	}
	
		
    /**
	 * 파일 조회
	 * 
	 */
	@RequestMapping(value="/api/selectFiles")
	public ModelAndView selectFileList(MapAdp adapter) throws Exception{
  
		ModelAndView mav = new ModelAndView("jsonView");
    	try{
    		mav.addObject("fileMap", fileService.selectFiles(adapter.getCstMap()));
    	}
    	catch (Exception e){
            e.printStackTrace();
		}
    	return mav;
    }

     /**
	 * 파일 다운로드
	 * 
	 */
	@RequestMapping(value="/fileDown")
	public void fileDown(MapAdp adapter, HttpServletRequest request, HttpServletResponse response) throws Exception{
  
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

    	try{
            CstMap cstMap = fileService.selectFile(adapter.getCstMap());

			File uFile = new File(cstMap.getString("phyPath"), cstMap.getString("realFileName"));
			int fSize = (int) uFile.length();

			if(cstMap != null && fSize > 0){
				response.setContentType("application/octet-stream;");
				response.setContentLength(fSize);

				in = new BufferedInputStream(new FileInputStream(uFile));
				out = new BufferedOutputStream(response.getOutputStream());
				FileCopyUtils.copy(in, out);
            }else{
                throw new FileNotFoundException("파일이 없습니다.");
            }
    	}
    	catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{ 
            if (in != null){ 
                in.close();
            }
            
            if (out != null) 
            {
                out.close();
            }
        }	
    }

     /**
	 * Disposition 지정
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
    public void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    
        String browser = getBrowser(request);
		
		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;
		
		if (browser.equals("MSIE")) 
		{
		    encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} 
		else if(browser.equals("Trident"))
		{
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		}
		else if (browser.equals("Firefox"))
		{
		    encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} 
		else if (browser.equals("Opera")) 
		{
		    encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} 
		else if (browser.equals("Chrome")) 
		{
		    StringBuffer sb = new StringBuffer();
		    
		    for (int i = 0; i < filename.length(); i++) 
		    {
				char c = filename.charAt(i);
				
				if (c > '~') 
				{
				    sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} 
				else 
				{
				    sb.append(c);
				}
		    }
		    
		    encodedFilename = "\"" + sb.toString() + "\"";
		}
		else if (browser.equals("Safari"))
		{
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		}
		else 
		{
		    throw new IOException("Not supported browser");
		}
		
		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
		response.setHeader("Content-Transfer-Encoding", "binary");
    }

     /**
	 * 브라우저 구분 
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
    public String getBrowser(HttpServletRequest request) 
    {
        String header = request.getHeader("User-Agent");
      
        if (header.indexOf("MSIE") > -1) 
        {
            return "MSIE";
        }
        else if (header.indexOf("Trident") > -1)
        {
        	return "Trident";
        }
        else if (header.indexOf("Chrome") > -1) 
        {
            return "Chrome";
        } 
        else if (header.indexOf("Opera") > -1) 
        {
            return "Opera";
        }
        else if (header.indexOf("Safari") > -1) 
        {
            return "Safari";
        }
        
        return "Firefox";
    }



}