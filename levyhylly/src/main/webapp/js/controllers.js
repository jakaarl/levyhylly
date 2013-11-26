'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TracksController', function($scope, Track) {
  $scope.tracks = [];
  $scope.loadTracks = function(albumId) {
    $scope.tracks = Track.query({ 'albumId': albumId });
  };
});