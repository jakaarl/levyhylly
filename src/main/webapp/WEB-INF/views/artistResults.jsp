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
    <c:if test="${empty resultArtists}"><fmt:message key="search.noResults"/></c:if>
    <c:forEach var="artist" items="${resultArtists}">
      <a href="artistAlbums?artistId=${artist.id}" class="artist">${artist.name}</a>
    </c:forEach>
    </p>
  </body>
</html>