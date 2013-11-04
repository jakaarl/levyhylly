<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
    <title><fmt:message key="app.title"/></title>
  </head>
  <body>
    <jsp:include page="../includes/header.jsp"/>
    <p>
    <c:if test="${empty resultAlbums}"><fmt:message key="search.noResults"/></c:if>
    <c:forEach var="album" items="${resultAlbums}">
      <a href="albumDetails?albumId=${album.id}" class="album">${album.name} ${album.year}</a>
    </c:forEach>
    <c:if test="${not empty artistId}">
      <a href="createAlbum?artistId=${artistId}" class="create"><fmt:message key="create.album"/></a>
    </c:if>
    </p>
  </body>
</html>