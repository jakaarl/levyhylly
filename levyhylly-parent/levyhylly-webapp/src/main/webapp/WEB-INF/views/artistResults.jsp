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
    <c:if test="${not empty searchTerm}">
     <fmt:message key="search.resultsByArtist"/>: <c:out value="${searchTerm}"></c:out>
    </c:if>
    <c:choose>
     <c:when test="${empty resultArtists}"><br/><fmt:message key="search.noResults"/></c:when>
     <c:otherwise>
      <ul>
       <c:forEach var="artist" items="${resultArtists}">
       <li><a href="artistAlbums?artistId=${artist.id}" class="artist">${artist.name}</a></li>
       </c:forEach>
      </ul>
     </c:otherwise>
    </c:choose>
    </p>
  </body>
</html>