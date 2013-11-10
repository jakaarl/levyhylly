<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
    <title><fmt:message key="app.title"/></title>
    <script>
      function addTrack() {
      
      }
    </script>
  </head>
  <body>
    <jsp:include page="../includes/header.jsp"/>
    <p>
      <form id="albumForm" action="saveAlbum" method="POST">
      <c:if test="${not empty artistId}">
        <input type="hidden" name="artistId" value="${artistId}"/>
      </c:if>
        <label for="artist"><fmt:message key="album.artistLabel"/></label>
        <input type="text" name="artist" size="30" maxLength="128"<c:if test="${not empty artistId}"> disabled="disabled"</c:if> value="<c:if test="${not empty artistName}">${artistName}</c:if>"/><br/>
        <label for="name"><fmt:message key="album.nameLabel"/></label>
        <input type="text" name="name" size="30" maxLength="128" value="<c:if test="${not empty album.name}">${album.name}</c:if>"/><br/>
        <label for="year"><fmt:message key="album.yearLabel"/></label>
        <input type="text" name="year" size="5" maxLength="4" value="<c:if test="${not empty album.year}">${album.year}</c:if>"/><br/>
        <label for="track"><fmt:message key="album.tracksLabel"/></label>
        <ul>
        <c:forEach var="track" items="${tracks}">
          <input type="hidden" name="track_${track.number}_name" value="${track.name}"/>
          <input type="hidden" name="track_${track.number}_length" value="${track.length}"/>
          <li class="track">${track.number} ${track.name} (${track.formattedLength})</li>
        </c:forEach>
        </ul>
      </form>
    </p>
  </body>
</html>