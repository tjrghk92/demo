package demo.front.member.service;


import demo.custom.map.CstMap;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemeberService extends UserDetailsService {
    
    public CstMap selectMemberList(CstMap cstmap) throws Exception;

    public CstMap selectMemberDtl(CstMap cstmap) throws Exception;

    public int insertMember(CstMap cstMap) throws Exception;

    public int updateMember(CstMap cstMap) throws Exception;

}