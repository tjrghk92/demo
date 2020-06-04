package demo.front.member.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.auth.adapter.AuthorizationAdapter;
import demo.auth.role.Role;
import demo.auth.vo.UserVo;
import demo.custom.map.CstMap;
import demo.custom.util.PaginationUtil;
import demo.front.member.mapper.MemberMapper;
import demo.front.member.service.MemeberService;
import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemeberServiceImpl implements MemeberService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorizationAdapter authorizationAdapter;

  

    @Override
    public CstMap selectMemberList(CstMap cstMap) throws Exception {
        cstMap.put("cntPage", 3);
        cstMap.put("pageSize", 3);
        CstMap pageMap = PaginationUtil.getPage(cstMap);
        
        List<CstMap> list = memberMapper.selectMemberList(pageMap);
        if(!list.isEmpty()){
            pageMap.put("totalCnt", String.valueOf(list.get(0).get("totalCnt")));
        }
        
        return new CstMap(ImmutableMap.builder().put("pageMap", pageMap).put("list", list).build());
    }

    @Override
    public CstMap selectMemberDtl(CstMap cstMap) throws Exception {
        return memberMapper.selectMemberDtl(cstMap);
    }

    @Override
    public int insertMember(CstMap cstMap) throws Exception {
        cstMap.put("memberPass", passwordEncoder.encode(cstMap.getString("memberPass")));
        return memberMapper.insertMember(cstMap);
    }

    @Override
    public int updateMember(CstMap cstMap) throws Exception {

        authorizationAdapter.login(cstMap.getString("memberId"), cstMap.getString("memberPass"));
        return memberMapper.updateMember(cstMap);
    }

    @Override
    public UserVo loadUserByUsername(String username) throws UsernameNotFoundException {
        String memberId = username;
        UserVo user = new UserVo();
        
        try {
            CstMap cstMap = memberMapper.selectMember(memberId);
            
            if(cstMap == null)
                throw new UsernameNotFoundException(memberId);

            user.setUsername(cstMap.getString("memberId")); 
            user.setPassword(cstMap.getString("memberPass"));
            user.setName(cstMap.getString("memberName"));
            user.setRegDtm(cstMap.getString("regDtm"));

            String roleType = "";
            for(Role role: Role.values()){
                if(cstMap.getString("memberType").equals(role.getValue())){
                    roleType = role.toString();
                    break;
                }
            }

            user.setAuthorities(new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(roleType))));
       
        } catch (Exception e) {
            throw new BadCredentialsException("Login Error !!");
        }

        return user;
    }

}