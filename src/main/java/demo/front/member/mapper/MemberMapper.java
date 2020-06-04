package demo.front.member.mapper;

import java.util.List;

import demo.custom.map.CstMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
   
    public List<CstMap> selectMemberList(CstMap cstMap) throws Exception;
    
    public CstMap selectMemberDtl(CstMap cstMap) throws Exception;

    public CstMap selectMember(String memberId) throws Exception;

    public int insertMember(CstMap cstMap) throws Exception;

    public int updateMember(CstMap cstMap) throws Exception;

} 