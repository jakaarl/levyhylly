<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <title>Levyhylly</title>
  </head>
  <body>
    <jsp:include page="../includes/header.jspf"/>
    <p>
    <c:forEach var="artist" items="${resultArtists}">
      <a href="artistAlbums?artistId=${artist.id}" class="artist">${artist.name}</a>
    </c:forEach>
    </p>
  </body>
</html>