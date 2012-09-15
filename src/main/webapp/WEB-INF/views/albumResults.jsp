<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <title>Levyhylly</title>
  </head>
  <body>
    <jsp:include page="../includes/header.jspf"/>
    <p>
    <c:forEach var="album" items="${resultAlbums}">
      <a href="/albumDetails?albumId=${album.id}" class="album">${album.name}</a>
    </c:forEach>
    </p>
  </body>
</html>