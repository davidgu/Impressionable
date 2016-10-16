"use strict";

angular
  .module('impressionableApp')
  .controller('homeController', ($scope)=>{
    $scope.homeList = [
      $scope.homeScreen = {state: true},
      $scope.updateInfoScreen = {state: false},
      $scope.createSessionScreen = {state: false}
    ];
    $scope.qualitiesList = [
      $scope.qualitiesScreen = {state: false},
      $scope.sessionScreen = {state: false}
    ];
  });
