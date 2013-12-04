'use strict';

var servicesModule = angular.module('levyhylly.services', ['ngResource']);
servicesModule.service('Track', function($resource,$location) {
  return $resource($location.protocol() + '://' + $location.host() + '\\:8080/albums/:albumId/tracks/:trackNumber', { albumId: '@albumId', trackNumber: '@number' });
});
