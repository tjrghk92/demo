package demo.custom.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import demo.config.PropertiesConfig;
import demo.custom.map.CstMap;
import demo.custom.mapper.FileMapper;
import demo.custom.service.FileService;
import demo.custom.util.CommonUtil;
import demo.custom.util.FilterUtil;

import com.google.common.collect.ImmutableMap;

import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Resource(name="seqGenService")
    SeqGenServiceImpl seqGenService;

    @Autowired
    public PropertiesConfig propertiesConfig;

    @Override
    public CstMap selectFile(CstMap cstMap) throws Exception {
        return fileMapper.selectFile(cstMap);
    }

    @Override
    public List<CstMap> selectFiles(CstMap cstMap) throws Exception {
        return fileMapper.selectFiles(cstMap);
    }
    
    @Override
    public void insertFiles(CstMap cstMap, MultipartHttpServletRequest multiRequest) throws Exception {
		String fileId = cstMap.getString("fileId");
		
		int maxFileSeq = 0;
        List<CstMap> fileList = null;
       
        Map<String, MultipartFile> files = multiRequest.getFileMap();
        if(!files.isEmpty()){
            fileList = this.fileCreate(files, null, "user", "image", "atchFile");
        }
		
		if(fileList != null && fileList.size() > 0)
		{
            // update
            if(!"".equals(fileId)){			
                maxFileSeq = this.getMaxFileNo(fileId);	
                fileMapper.updateFileMst(new CstMap(ImmutableMap.builder()
                                                        .put("fileId", fileId)
                                                        .put("modId", cstMap.getString("memberId"))
                                                        .put("modIp", cstMap.getString("ip")).build()
                                                    ));
            }else{
                fileId = seqGenService.newSeqGenId("file");
                cstMap.put("fileId", fileId);
                fileMapper.insertFileMst(new CstMap(ImmutableMap.builder()
                                                        .put("fileId", fileId)
                                                        .put("regId", cstMap.getString("memberId"))
                                                        .put("regIp", cstMap.getString("ip")).build()
                                                    ));
            }

            for(CstMap fileMap : fileList){
                fileMap.put("fileId", fileId);
                fileMap.put("fileNo", maxFileSeq += 1);
                fileMap.put("regId", cstMap.getString("memberId"));
                fileMap.put("regIp", cstMap.getString("ip"));
            }

            fileMapper.insertFileDtl(new CstMap(ImmutableMap.builder().put("fileList",fileList).build()));
		}
    }

    @Override
    public void deleteFiles(CstMap cstMap) throws Exception {
		String fileId = cstMap.getString("fileId");
		String fileNo = cstMap.getString("fileNo");
		
		if(!"".equals(fileId)){
			if(!"".equals(fileNo)){
                fileMapper.deleteFileDtl(new CstMap(ImmutableMap.builder()
                                                        .put("fileId", fileId)
                                                        .put("fileNo",fileNo).build()
                                                    ));
				// 파일 상세에 등록된 파일 갯수 조회
				int fileCnt = this.getMaxFileNo(fileId);
				
				if (fileCnt == 0) 
				{
					fileMapper.deleteFileMst(fileId);
				}	
			}			
        }
    }

    public List<CstMap> getUpdoadFileTemp(MultipartHttpServletRequest multiRequest) throws Exception {
        Map<String, MultipartFile> files = multiRequest.getFileMap();	
        List<CstMap> fileList = this.fileCreate(files, propertiesConfig.getData("file", "uploadPath"), "temp", "image", "atchFileImg");
       
        return fileList;
    }

    public List<CstMap> fileCopy(CstMap cstMap, String storePath, String folder) throws Exception {

        String pyhPathCopy = "";
        String fileId = cstMap.getString("fileId");
        int maxFileSeq = 0;
        List<String> filePaths = cstMap.getList("webPath");
        List<String> realFileNm = cstMap.getList("realFileName");
        String fileExtn = "";
        
        Calendar c = Calendar.getInstance();
        Tika tika = new Tika();
        String mimeType = "";

        String inFilePath = "";
        String outFilePath = "";
        File infile = null;
        File outfile = null;

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        long size = 0; 

        List<CstMap> fileList = new ArrayList<CstMap>();

        try {     

            if ("".equals(storePath) || storePath == null) 
            {
                pyhPathCopy = propertiesConfig.getData("file", "uploadPath");
            } 
            else 
            {
                pyhPathCopy = storePath;
            }

            if(filePaths.size() > 0 ){
                int i = 0;
                for(String filePath : filePaths){
                    if(!filePath.equals("")){
                        inFilePath = pyhPathCopy + filePath;
                        infile = new File(inFilePath);
                        fis = new FileInputStream(infile);
                        mimeType = tika.detect(fis);

                        String filePyhPath = "";

                        if(folder != null && !folder.equals(""))
                            filePyhPath += folder + File.separator;

                        if (mimeType.indexOf("image") > -1){
                            filePyhPath += "image";
                        }else if(mimeType.indexOf("video") > -1){
                            filePyhPath += "video";
                        }else{
                            filePyhPath += "atch";
                        }

                        filePyhPath = pyhPathCopy + filePyhPath + File.separator + c.get(Calendar.YEAR) +  File.separator + (c.get(Calendar.MONTH) + 1);

                        File saveFolder = new File(FilterUtil.filePathBlackList(filePyhPath));

                        if (!saveFolder.exists() || saveFolder.isFile()){
                            saveFolder.mkdirs();
                        }

                        String realFileName = realFileNm.get(i);

                        fileExtn = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase(Locale.ENGLISH);
                        
                        String fileName = CommonUtil.getTimeStamp() + "." + fileExtn;
                        filePath = filePyhPath + File.separator + fileName; 

                        String webPath = "";
                        webPath = File.separator + filePath.replace(pyhPathCopy, "");
                        webPath = webPath.replaceAll("\\\\", "/");
                        
                        outFilePath = filePyhPath + File.separator + fileName;
                        outfile = new File(outFilePath);

                        fos = new FileOutputStream(outfile);
                    	fcin =  fis.getChannel();
            	    	fcout = fos.getChannel();
            	    	  
                        size = fcin.size();
                        
                        CstMap fileMap = new CstMap(ImmutableMap.builder()
                                                .put("realFileName", realFileName)
                                                .put("fileName", fileName)
                                                .put("webPath", webPath)
                                                .put("phyPath",filePath).build());
                        fileList.add(fileMap);

                        fcin.transferTo(0, size, fcout);
                        i++;
                    }
                }

                if(!"".equals(fileId)){			
                    maxFileSeq = this.getMaxFileNo(fileId);	
                    fileMapper.updateFileMst(new CstMap(ImmutableMap.builder()
                                                            .put("fileId", fileId)
                                                            .put("modId", cstMap.getString("memberId"))
                                                            .put("modIp", cstMap.getString("ip")).build()
                                                        ));
                }else{
                    fileId = seqGenService.newSeqGenId("file");
                    fileMapper.insertFileMst(new CstMap(ImmutableMap.builder()
                                                            .put("fileId", fileId)
                                                            .put("regId", cstMap.getString("memberId"))
                                                            .put("regIp", cstMap.getString("ip")).build()
                                                        ));
                }

                for(CstMap fileMap : fileList){
                    fileMap.put("fileId", fileId);
                    fileMap.put("fileNo", maxFileSeq += 1);
                    fileMap.put("regId", cstMap.getString("memberId"));
                    fileMap.put("regIp", cstMap.getString("ip"));
                }
    
                fileMapper.insertFileDtl(new CstMap(ImmutableMap.builder().put("fileList",fileList).build()));
            }
        }catch(Exception e){
            throw new FileUploadException("업로드 에러");
        }finally{
            if (fis != null)
            {
                fis.close();
            }
            
            if (fos != null)
            {
                fos.close();	        	
            }
            if(fcout != null)
            {
                fcout.close();
            }
            if(fcin != null)
            {
                fcin.close();
            }
        }
        
        return null;
    }

    public List<CstMap> fileCreate(Map<String, MultipartFile> files, String storePath, String folder, String atchKind, String atchName ) throws Exception {
       
        String pyhPath = "";
       
        if ("".equals(storePath) || storePath == null) 
		{
			pyhPath = propertiesConfig.getData("file", "uploadPath");
		} 
		else 
		{
			pyhPath = storePath;
		}

        Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file = null;
        
        Calendar c = Calendar.getInstance();
        Tika tika = new Tika();
       
        long _size = 0;
        String realFileNm = "";
        String mimeType = "";
        String fileExtn = "";

        long fileSize = 0;
	    String[] checkFileExt = null;
        boolean isFileExt = false; 

        List<CstMap> fileList = new ArrayList<CstMap>();
        CstMap fileMap = null;

        while (itr.hasNext()) 
		{    
            Entry<String, MultipartFile> entry = itr.next();
            if (entry.getKey().indexOf(atchName) > -1){
                file = entry.getValue();

                realFileNm = file.getOriginalFilename();
                fileExtn = realFileNm.substring(realFileNm.lastIndexOf(".") + 1).toLowerCase(Locale.ENGLISH);
                _size = file.getSize();
                mimeType = tika.detect(file.getInputStream());
                
                String filePyhPath = "";

                if(folder != null && !folder.equals(""))
                    filePyhPath += folder + File.separator;

                if (mimeType.indexOf("image") > -1){
                    fileSize = Integer.parseInt(propertiesConfig.getData("file", "imgFileSize"));
                    checkFileExt = propertiesConfig.getData("file", "imgExtn").split(",");
                    filePyhPath += "image";
                }else if(mimeType.indexOf("video") > -1){
                    fileSize = Integer.parseInt(propertiesConfig.getData("file", "videoFileSize"));
                    checkFileExt = propertiesConfig.getData("file", "videoExtn").split(",");
                    filePyhPath += "video";
                }else{
                    fileSize = Integer.parseInt(propertiesConfig.getData("file", "atchFileSize"));
                    checkFileExt = propertiesConfig.getData("file", "atchExtn").split(",");
                    filePyhPath += "atch";
                }

                if(atchKind != null){
                    if(mimeType.indexOf(atchKind) == -1){
                        throw new FileUploadException("허용되지 않은 파일입니다.");
                    }
                }

                if (_size > (long) fileSize) 
                {
                    throw new FileUploadException("최대 첨부용량을 초과하셨습니다.");
                }

                if (checkFileExt != null)
                {
                    for (int q = 0; q < checkFileExt.length; q++)
                    {
                        if (checkFileExt[q].trim().toLowerCase(Locale.ENGLISH).equals(fileExtn.toLowerCase(Locale.ENGLISH)))
                        {
                            isFileExt = true;
                            break;
                        }
                    }
                }

                if(!isFileExt){
                    throw new FileUploadException("해당 파일 확장명은 등록하실 수 없습니다.");
                }
                
                filePyhPath = pyhPath + filePyhPath + File.separator + c.get(Calendar.YEAR) +  File.separator + (c.get(Calendar.MONTH) + 1);
                File saveFolder = new File(FilterUtil.filePathBlackList(filePyhPath));

                if (!saveFolder.exists() || saveFolder.isFile()){
                    saveFolder.mkdirs();
                }
                
                String fileName = CommonUtil.getTimeStamp() + "." + fileExtn;
                String realFileName = realFileNm;
                String filePath = filePyhPath + File.separator + fileName;
                String webPath = "";

                webPath = File.separator + filePath.replace(pyhPath, "");
                webPath = webPath.replaceAll("\\\\", "/");

                fileMap = new CstMap(ImmutableMap.builder()
                                                    .put("realFileName", realFileName)
                                                    .put("fileName", fileName)
                                                    .put("webPath", webPath)
                                                    .put("phyPath",filePath).build());
                fileList.add(fileMap);

                file.transferTo(new File(FilterUtil.filePathBlackList(filePath)));
            }
        }
        return fileList;
    }

    public int getMaxFileNo(String fileId) throws Exception {
        return fileMapper.getMaxFileNo(fileId);
    }
    
   
 
}