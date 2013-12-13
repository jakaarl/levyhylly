<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!doctype html>
<html>
  <jsp:include page="../includes/head.jsp"/>
  <body ng-app="levyhylly">
    <jsp:include page="../includes/header.jsp"/>
    <p>
      <form:form id="albumForm" action="saveAlbum" method="POST" commandName="albumDetails">
        <form:hidden path="artistId"/>
        <form:hidden path="albumId"/>
        <label for="artist"><fmt:message key="album.artistLabel"/></label>
        <form:input path="artistName" size="30" maxLength="128" readonly="${not empty albumDetails.artistId}" /> <form:errors path="artistName" cssClass="errMessage"/><br/>
        <label for="name"><fmt:message key="album.nameLabel"/></label>
        <form:input path="name" size="30" maxLength="128" /> <form:errors path="name" cssClass="errMessage"/><br/>
        <label for="year"><fmt:message key="album.yearLabel"/></label>
        <form:input path="year" size="5" maxLength="4"/> <form:errors path="year" cssClass="errMessage"/><br/>
        <input type="submit" name="save" value="<fmt:message key="save.album"/>"/><br/>
      </form:form>
      <form id="trackForm" action="#" method="GET">
        <p><fmt:message key="album.tracksLabel"/></p>
       <c:if test="${not empty albumDetails.albumId}">
        <ul id="trackListing">
          <div ng-controller="TracksController" ng-init="loadTracks(${albumDetails.albumId})"/>
            <div ng-repeat="track in tracks">
              <li class="track">{{ track.number }} {{track.name}} {{track.formattedLength}} <button type="button" ng-click="removeTrack(track.albumId, track.number)"><fmt:message key="remove.track"/></button></li>
            </div>
            <input id="addedTrackName" type="text" placeholder="<fmt:message key="track.name.placeholder"/>" ng-model="editedTrack.name" size="30" maxLength="128" ng-required="true"/>
            <input id="addedTrackLength" type="number" placeholder="<fmt:message key="track.length.placeholder"/>" ng-model="editedTrack.length" size="8" maxLength="8" ng-required="true"/>
            <button type="button" ng-click="addTrack(${albumDetails.albumId})"><fmt:message key="add.track"/></button>
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