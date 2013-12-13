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
     <fmt:message key="search.resultsByTrack"/>: <c:out value="${searchTerm}"></c:out>
    </c:if>
    <c:choose>
     <c:when test="${empty resultTracks}"><br/><fmt:message key="search.noResults"/></c:when>
     <c:otherwise>
      <ul>
      <c:forEach var="track" items="${resultTracks}">
       <li><a href="albumDetails?albumId=${track.albumId}" class="track">${track.name} (${track.formattedLength})</a></li>
      </c:forEach>
      </ul>
     </c:otherwise>
    </c:choose>
    </p>
  </body>
</html>