<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <jsp:include page="../includes/head.jsp"/>
  <body>
    <jsp:include page="../includes/header.jsp"/>
    <p>${albumDetails.artistName}: ${albumDetails.name} (${albumDetails.year})</p>
    <p>
    <c:if test="${not empty tracks}">
      <ul>
      <c:forEach var="track" items="${tracks}">
        <li class="track">${track.number} ${track.name} (${track.formattedLength})</li>
      </c:forEach>
      </ul>
    </c:if>
    <a href="editAlbum?albumId=${albumDetails.albumId}"><fmt:message key="edit.album"/></a>
    </p>
  </body>
</html>