'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TracksController', function($scope, $timeout, Track) {
  $scope.tracks = [];
  $scope.editedTrack = new Track();
  $scope.loadTracks = function(albumId) {
    Track.query({ 'albumId': albumId }, function(data) {
      $scope.tracks = data;
    });
  };
  $scope.addTrack = function(albumId) {
    $scope.editedTrack.albumId = albumId;
    delete($scope.editedTrack.hasErrors);
    $scope.editedTrack.$save({}, function() {
      $scope.loadTracks(albumId);
      $scope.editedTrack = new Track();
    }, function(httpResponse) {
      if (httpResponse.status == 400) {
        $scope.editedTrack.hasErrors = true;
      } else {
        delete($scope.editedTrack.hasErrors);
      }
    });
  };
  $scope.removeTrack = function(albumId, trackNumber) {
    Track.remove({'albumId': albumId, 'trackNumber': trackNumber}, function() {
      $scope.loadTracks(albumId);
    });
  };
});