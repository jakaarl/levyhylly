<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <jsp:include page="../includes/head.jsp"/>
  <body>
    <jsp:include page="../includes/header.jsp"/>
    <p>${albumDetails.artistName}: ${albumDetails.name} (${albumDetails.year})</p>
    <p>
    <c:if test="${not empty albumDetails.albumId}">
      <p><fmt:message key="album.tracksLabel"/></p>
      <ul id="trackListing">
        <div id="ng-app" ng-app="levyhylly" ng-controller="TracksController" ng-init="loadTracks(${albumDetails.albumId})"/>
          <div ng-repeat="track in tracks">
            <li class="track">{{ track.number }} {{track.name}} {{track.formattedLength}}</li>
          </div>
        </div>
      </ul>
    </c:if>
    <a href="editAlbum?albumId=${albumDetails.albumId}"><fmt:message key="edit.album"/></a>
    </p>
  </body>
  <script src="lib/angular/angular.js"></script>
  <script src="lib/angular/angular-resource.js"></script>
  <script src="js/app.js"></script>
  <script src="js/services.js"></script>
  <script src="js/controllers.js"></script>
</html>