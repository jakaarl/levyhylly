<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
  <head>
    <title><fmt:message key="app.title"/></title>
    <script>
    /* OMG horror, will need to make a proper UI with a handy JS library! */
      var nextTrackNumber = ${fn:length(tracks) + 1};
      function addTrack() {
        var trackName = document.getElementById('addedTrackName').value;
        var trackLength = document.getElementById('addedTrackLength').value;
      	
      	var newTrackNameInput = createHidden("track_" + nextTrackNumber + "_name", trackName);
      	var newTrackLengthInput = createHidden("track_" + nextTrackNumber + "_length", trackLength);
      	var trackForm = document.getElementById('albumForm');
      	trackForm.appendChild(newTrackNameInput);
      	trackForm.appendChild(newTrackLengthInput);
      	
      	var newTrackItem = document.createElement('li');
      	newTrackItem.class = 'track';
      	newTrackItem.innerHTML = nextTrackNumber + ' ' + trackName +  ' (' + formatLength(trackLength) + ')';
      	var trackList = document.getElementById('trackListing');
      	trackList.appendChild(newTrackItem);
      	
      	nextTrackNumber++;
      }
      function createHidden(name,value) {
        var hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = name;
        hiddenInput.value = value;
        return hiddenInput;
      }
      function formatLength(length) {
        var formatted = "";
		var hours = Math.floor(length / (60 * 60));
		var minutes = Math.floor((length % (60 * 60)) / 60);
		var seconds = length % 60;
		if (hours > 0) {
		  formatted += zeroPad(hours, 2) + ":";
		}
		formatted += zeroPad(minutes, 2) + ":";
		formatted += zeroPad(seconds, 2);
		return formatted;
      }
      function zeroPad(number,desiredLength) {
        var padded = number + '';
        while (padded.length < desiredLength) {
          padded = "0".concat(padded);
        }
        return padded;
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
      <c:if test="${not empty albumId}">
        <input type="hidden" name="albumId" value="${albumId}"/>
      </c:if>
        <label for="artist"><fmt:message key="album.artistLabel"/></label>
        <input type="text" name="artist" size="30" maxLength="128"<c:if test="${not empty artistId}"> disabled="disabled"</c:if> value="<c:if test="${not empty artistName}">${artistName}</c:if>"/><br/>
        <label for="name"><fmt:message key="album.nameLabel"/></label>
        <input type="text" name="name" size="30" maxLength="128"<c:if test="${not empty albumId}"> disabled="disabled"</c:if> value="<c:if test="${not empty album.name}">${album.name}</c:if>"/><br/>
        <label for="year"><fmt:message key="album.yearLabel"/></label>
        <input type="text" name="year" size="5" maxLength="4" value="<c:if test="${not empty album.year}">${album.year}</c:if>"/><br/>
        <label for="track"><fmt:message key="album.tracksLabel"/></label>
        <ul id="trackListing">
        <c:forEach var="track" items="${tracks}">
          <input type="hidden" name="track_${track.number}_name" value="${track.name}"/>
          <input type="hidden" name="track_${track.number}_length" value="${track.length}"/>
          <li class="track">${track.number} ${track.name} (${track.formattedLength})</li>
        </c:forEach>
        </ul>
        <input id="addedTrackName" type="text" name="addedTrackName" value="" size="30" maxLength="128"/>
        <input id="addedTrackLength" type="text" name="addedTrackLength" value="" size="8" maxLength="8"/>
        <button type="button" onClick="addTrack()"><fmt:message key="add.track"/></button>
        <br/>
        <input type="submit" name="save" value="<fmt:message key="save.album"/>"/>
      </form>
    </p>
  </body>
</html>