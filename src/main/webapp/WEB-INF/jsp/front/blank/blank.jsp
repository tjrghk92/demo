<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/el.jspf"%>

<!DOCTYPE html>
<html lang="ko">
    <script type="text/javascript">
		//<![CDATA[
			function init()
			{
				<c:if test="${not empty msg}">
					alert("${msg}");
				</c:if>

				<c:choose>
					<c:when test="${not empty back}">
					history.back(-1);
					</c:when>
					<c:otherwise>
					location.href = "${url}";	
					</c:otherwise>
				</c:choose>
			}
		//]]>
    </script>
    <head></head>
    <body onload="init();">
    </body>
</html>