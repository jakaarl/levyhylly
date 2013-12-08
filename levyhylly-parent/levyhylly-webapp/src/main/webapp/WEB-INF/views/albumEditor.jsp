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
        <input type="hidden" name="artistId" value="<c:out value="${artistId}"></c:out>"/>
        <input type="hidden" name="albumId" value="<c:out value="${album.id}"></c:out>"/>
        <label for="artist"><fmt:message key="album.artistLabel"/></label>
        <input type="text" name="artistName" size="30" maxLength="128"<c:if test="${not empty artistId}"> disabled="disabled"</c:if> value="<c:out value="${artistName}"></c:out>"/><br/>
        <label for="name"><fmt:message key="album.nameLabel"/></label>
        <input type="text" name="name" size="30" maxLength="128" value="<c:out value="${album.name}"></c:out>"/><br/>
        <label for="year"><fmt:message key="album.yearLabel"/></label>
        <input type="text" name="year" size="5" maxLength="4" value="<c:out value="${album.year}"></c:out>"/><br/>
        <input type="submit" name="save" value="<fmt:message key="save.album"/>"/><br/>
      </form>
      <form id="trackForm" action="#" method="GET">
        <p><fmt:message key="album.tracksLabel"/></p>
       <c:if test="${not empty album.id}">
        <ul id="trackListing">
          <div ng-controller="TracksController" ng-init="loadTracks(${album.id})"/>
            <div ng-repeat="track in tracks">
              <li class="track">{{ track.number }} {{track.name}} {{track.formattedLength}} <button type="button" ng-click="removeTrack(track.albumId, track.number)"><fmt:message key="remove.track"/></button></li>
            </div>
            <input id="addedTrackName" type="text" placeholder="<fmt:message key="track.name.placeholder"/>" ng-model="editedTrack.name" ng-required="true" size="30" maxLength="128"/>
            <input id="addedTrackLength" type="number" placeholder="<fmt:message key="track.length.placeholder"/>" ng-model="editedTrack.length" ng-required="true" size="8" maxLength="8"/>
            <button type="button" ng-click="addTrack(${album.id})"><fmt:message key="add.track"/></button>
          </div>
        </ul>
       </c:if>
      </form>
    </p>
  </body>
  <script src="lib/angular/angular.js"></script>
  <script src="lib/angular/angular-resource.js"></script>
  <script src="js/app.js"></script>
  <script src="js/services.js"></script>
  <script src="js/controllers.js"></script>
</html>