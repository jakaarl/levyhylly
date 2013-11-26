<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>
  <head>
    <title><fmt:message key="app.title"/></title>
  </head>
  <body ng-app="levyhylly">
    <jsp:include page="../includes/header.jsp"/>
    <p>
      <form id="albumForm" action="saveAlbum" method="POST">
        <input type="hidden" name="artistId" value="${artistId}"/>
        <input type="hidden" name="albumId" value="${album.id}"/>
        <label for="artist"><fmt:message key="album.artistLabel"/></label>
        <input type="text" name="artist" size="30" maxLength="128" disabled="disabled" value="<c:out value="${artistName}"/>"/><br/>
        <label for="name"><fmt:message key="album.nameLabel"/></label>
        <input type="text" name="name" size="30" maxLength="128" value="<c:out value="${album.name}"></c:out>"/><br/>
        <label for="year"><fmt:message key="album.yearLabel"/></label>
        <input type="text" name="year" size="5" maxLength="4" value="<c:out value="${album.year}"></c:out>"/><br/>
        <label for="track"><fmt:message key="album.tracksLabel"/></label>
        <ul id="trackListing">
          <div ng-controller="TracksController" ng-init="loadTracks(${album.id})"/>
            <div ng-repeat="track in tracks">
              <li class="track">{{ track.number }} {{track.name}} {{track.formattedLength}}</li>
            </div>
            
          </div>
        </ul>
        <input id="addedTrackName" type="text" name="addedTrackName" value="" size="30" maxLength="128"/>
        <input id="addedTrackLength" type="text" name="addedTrackLength" value="" size="8" maxLength="8"/>
        <button type="button" onClick="addTrack()"><fmt:message key="add.track"/></button>
        <br/>
        <input type="submit" name="save" value="<fmt:message key="save.album"/>"/>
      </form>
    </p>
  </body>
  <script src="lib/angular/angular.js"></script>
  <script src="lib/angular/angular-resource.js"></script>
  <script src="js/app.js"></script>
  <script src="js/services.js"></script>
  <script src="js/controllers.js"></script>
</html>