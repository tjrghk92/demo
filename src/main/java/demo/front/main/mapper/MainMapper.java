package demo.front.main.mapper;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
   
    public int insertTemp() throws Exception;

} 