'use strict';

var servicesModule = angular.module('levyhylly.services', ['ngResource']);
servicesModule.service('TrackService', function($resource) {
  this.loadTracks = function(albumId) {
    //return $resource('http://localhost\\:8080/album/:albumId/tracks').get({ 'parameters': { 'albumId': albumId }});
  }
});
