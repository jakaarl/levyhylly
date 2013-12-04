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
    <c:if test="${not empty artistName}">
     <fmt:message key="search.resultsByArtist"/>: <c:out value="${artistName}"></c:out>
    </c:if>
    <c:if test="${not empty searchTerm}">
     <fmt:message key="search.resultsByAlbum"/>: <c:out value="${searchTerm}"></c:out>
    </c:if>
    <c:choose>
     <c:when test="${empty resultAlbums}"><fmt:message key="search.noResults"/></c:when>
     <c:otherwise>
      <ul>
      <c:forEach var="album" items="${resultAlbums}">
       <li><a href="albumDetails?albumId=${album.id}" class="album">${album.name} (${album.year})</a></li>
      </c:forEach>
      </ul>
     </c:otherwise>
    </c:choose>
    <c:if test="${not empty artistId}">
      <a href="createAlbum?artistId=${artistId}" class="create"><fmt:message key="create.album"/></a>
    </c:if>
    </p>
  </body>
</html>