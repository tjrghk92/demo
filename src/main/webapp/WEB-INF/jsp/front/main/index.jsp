<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/el.jspf"%>
<input type="hidden" name="maxSize" id="maxSize" value="${common.file.imgFileSize}">
<input type="hidden" name="extns" id="extns" value="${common.file.atchExtn}">
<div id="root"></div>
<script src="/js/bundle/${index}/${index}.bundle.js"></script>
