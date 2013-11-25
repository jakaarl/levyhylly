'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TrackController', function($scope, TrackService) {
  $scope.loadTracks = function(albumId) {
    console.log("Loading tracks for album: " + albumId);
    return (albumId == null ? [] : TrackService.loadTracks(albumId));
  }
});