<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/el.jspf"%>

<!DOCTYPE html>
<html lang="ko">
    <head>
    
    </head>
    <body>
    <div id="header">
        <input type="hidden" name="auth" value="${auth}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrf"/>
        <c:choose>
            <c:when test="${auth}">
                <a href = "/member/logout">로그아웃</a>
                <c:if test="${fn:indexOf(member.authorities, 'SPECIAL') > -1 and fn:indexOf(requestUri, '/member/list') == -1 }">
                    <a href = "/member/list">회원 리스트</a>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:if test="${fn:indexOf(requestUri, '/member/login') == -1}">
                    <a href = "/member/login">로그인</a>
                </c:if>
                <c:if test="${fn:indexOf(requestUri, '/member/signup') == -1}">
                    <a href = "/member/signup">회원가입</a>
                </c:if>
            </c:otherwise>
        </c:choose> 
        <c:if test="${fn:indexOf(requestUri, 'main') == -1}">
            <a href = "/">메인으로</a>
        </c:if>
        <c:if test="${auth}">
            안녕하세요  ${member.name}님!
        </c:if> 
    </div>
