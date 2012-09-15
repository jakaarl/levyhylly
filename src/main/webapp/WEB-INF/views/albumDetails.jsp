<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <title>Levyhylly</title>
  </head>
  <body>
    <jsp:include page="../includes/header.jspf"/>
    <p>${album.name}</p>
    <p>
    <c:forEach var="track" items="${tracks}">
      <span class="track">${track.number} ${track.name}</span>
    </c:forEach>
    </p>
  </body>
</html>