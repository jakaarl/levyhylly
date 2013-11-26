'use strict';

var servicesModule = angular.module('levyhylly.services', ['ngResource']);
servicesModule.service('Track', function($resource) {
  return $resource('http://localhost\\:8080/albums/:albumId/tracks/:trackNumber', { trackNumber: '@number' });
});
