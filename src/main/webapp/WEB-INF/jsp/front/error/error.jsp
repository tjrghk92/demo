<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/el.jspf"%>

<!DOCTYPE html>
<html lang="ko">
    <head></head>
    <body>
    <div layout:fragment="content">
      <h1>Error Page</h1>
      error code : <span >${code}</span>
      <br>error msg : <span>${msg}</span>
      <br>timestamp : <span>${timestamp}</span>
    </div>
    </body>
</html>