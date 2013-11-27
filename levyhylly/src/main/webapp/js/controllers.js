'use strict';

var controllersModule = angular.module('levyhylly.controllers', ['levyhylly.services']);
controllersModule.controller('TracksController', function($scope, Track) {
  $scope.tracks = [];
  $scope.loadTracks = function(albumId) {
    $scope.tracks = Track.query({ 'albumId': albumId });
  };
  $scope.addTrack = function(number, name, length) {
    var track = new Track({'number': number, 'name': name, 'length': length});
    track.save();
    $scope.tracks[number - 1] = track;
  }
});