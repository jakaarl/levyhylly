<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
    <title>Levyhylly</title>
  </head>
  <body>
    <jsp:include page="../includes/header.jsp"/>
    <p>
      <form id="albumForm" action="saveAlbum" method="POST">
      <c:if test="${not empty artistId}">
        <input type="hidden" name="artistId" value="${artistId}"/>
      </c:if>
        <label for="artist"><fmt:message key="album.artistLabel"/></label>
        <input type="text" name="artist" size="30" maxLength="128"<c:if test="${not empty artistId}"> disabled="disabled"</c:if>><c:if test="${not empty artist}">${artist}</c:if></input><br/>
        <label for="name"><fmt:message key="album.nameLabel"/></label>
        <input type="text" name="name" size="30" maxLength="128"><c:if test="${not empty name}">${name}</c:if></input><br/>
      </form>
    </p>
  </body>
</html>