package demo.custom.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeqGenMapper {

    public String selectSeqGen(String genId) throws Exception;
    
}