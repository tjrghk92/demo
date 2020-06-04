package demo.custom.mapper;

import java.util.List;

import demo.custom.map.CstMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    public CstMap selectFile(CstMap cstMap) throws Exception;
    
    public List<CstMap> selectFiles(CstMap cstMap) throws Exception;

    public int getMaxFileNo(String fileId) throws Exception;

    public int insertFileMst(CstMap cstMap) throws Exception;

    public int insertFileDtl(CstMap fileList) throws Exception;

    public int updateFileMst(CstMap cstMap) throws Exception;

    public int deleteFileMst(String fileId) throws Exception;

    public int deleteFileDtl(CstMap cstMap) throws Exception;
}