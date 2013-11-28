'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TracksController', function($scope, $timeout, Track) {
  $scope.tracks = [];
  $scope.loadTracks = function(albumId) {
    $scope.tracks = Track.query({ 'albumId': albumId });
  };
  $scope.addTrack = function(albumId, number, name, length) {
    var track = new Track({'albumId': albumId, 'number': number, 'name': name, 'length': length});
    track.$save();
    $scope.tracks[number - 1] = track;
  }
  $scope.removeTrack = function(albumId, number) {
    var track = new Track({'albumId': albumId, 'number': number});
    track.$remove();
    $timeout(function() {
      $scope.loadTracks(albumId);
    }, 1000); // delay so that list isn't rendered prematurely (TODO: deferred update)
    
  }
});