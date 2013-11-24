'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TrackController', function($scope, TrackService) {
  $scope.loadTracks = function(albumId) {
    return TrackService.loadTracks(albumId);
  }
});