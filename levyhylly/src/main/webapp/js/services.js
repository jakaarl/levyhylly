'use strict';

var servicesModule = angular.module('levylly.services', ['ngResource']);
servicesModule.service('trackService', function($resource) {
  this.loadTracks = function(albumId) {
    return $resource('http://localhost:8080/loadTracks/:albumId', {});
  }
});
