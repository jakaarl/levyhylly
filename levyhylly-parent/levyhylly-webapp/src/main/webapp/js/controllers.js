'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TracksController', function($scope, $timeout, Track) {
  $scope.tracks = [];
  $scope.editedTrack = new Track();
  $scope.loadTracks = function(albumId) {
    Track.query({ 'albumId': albumId }, function(response) {
      $scope.tracks = response.resource;
    });
  };
  $scope.addTrack = function(albumId) {
    $scope.editedTrack.albumId = albumId;
    $scope.editedTrack.$save({}, function(response) {
      $scope.loadTracks(albumId);
      $scope.editedTrack = new Track();
    });
  };
  $scope.removeTrack = function(albumId, number) {
    Track.$remove({'albumId': albumId, 'number': number}, function(response) {
      $scope.loadTracks(albumId);
    });
  };
});