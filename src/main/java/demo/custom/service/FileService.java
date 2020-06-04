package demo.custom.service;

import java.util.List;

import demo.custom.map.CstMap;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService {

    public CstMap selectFile(CstMap cstMap) throws Exception;

    public List<CstMap> selectFiles(CstMap cstMap) throws Exception;

    public void insertFiles(CstMap cstMap, MultipartHttpServletRequest multiRequest) throws Exception;

    public void deleteFiles(CstMap cstMap) throws Exception;
    
}