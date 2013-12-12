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
    $scope.editedTrack.$save({}, function() {
      $scope.loadTracks(albumId);
      $scope.editedTrack = new Track();
    });
  };
  $scope.removeTrack = function(albumId, trackNumber) {
    Track.remove({'albumId': albumId, 'trackNumber': trackNumber}, function() {
      $scope.loadTracks(albumId);
    });
  };
});