'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TracksController', function($scope, $timeout, Track) {
  $scope.tracks = [];
  $scope.editedTrack = new Track();
  $scope.loadTracks = function(albumId) {
    $scope.tracks = Track.query({ 'albumId': albumId });
  };
  $scope.addTrack = function(albumId) {
    $scope.editedTrack.albumId = albumId;
    $scope.editedTrack.$save();
    $timeout(function() {
      $scope.loadTracks(albumId);
    }, 500);
    $scope.editedTrack = new Track();
  };
  $scope.removeTrack = function(albumId, number) {
    var track = new Track({'albumId': albumId, 'number': number});
    track.$remove();
    $timeout(function() {
      $scope.loadTracks(albumId);
    }, 500); // delay so that list isn't rendered prematurely (TODO: deferred update)
  };
});