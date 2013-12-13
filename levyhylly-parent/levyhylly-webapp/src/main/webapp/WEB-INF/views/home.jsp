<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
    <title>Levyhylly</title>
  </head>
  <body>
    <jsp:include page="../includes/header.jsp"/>
    <div class="langSwitch"><a href="?lang=fi">fi</a> | <a href="?lang=en">en</a></div>
    <p><fmt:message key="home.introduction"/></p>
    <p>
      <form id="search" action="search" method="POST">
        <label for="searchTerm"><fmt:message key="search.label"/></label>
        <input type="text" name="searchTerm" size="30" maxLength="50"/>
        <button name="submitButton" value="byArtist" type="submit"><fmt:message key="search.byArtist"/></button>
        <button name="submitButton" value="byAlbum" type="submit"><fmt:message key="search.byAlbum"/></button>
        <button name="submitButton" value="byTrack" type="submit"><fmt:message key="search.byTrack"/></button>
      </form>
      <a href="createAlbum" class="create"><fmt:message key="create.album"/></a>
  </body>
</html>