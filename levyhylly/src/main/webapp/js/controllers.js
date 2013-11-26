'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('LoadTracksController', function($scope, TrackService) {
  $scope.tracks = [];
  $scope.load = function(albumId) {
    $scope.tracks = TrackService.loadTracks(albumId);
  };
});