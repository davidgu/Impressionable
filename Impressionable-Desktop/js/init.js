"use strict";

//All links open in the default browser instead of electron's window
const shell = require('electron').shell;
var openExternalLink = (link) => {
    event.preventDefault();
    shell.openExternal(link);
};

angular.module('impressionableApp', []);

angular
  .module('impressionableApp')
  .controller('userController', ($scope, $rootScope) => {
    $scope.screenList = [
        $scope.loginScreen = {
            state: true
        },
        $scope.signInScreen = {
            state: false
        },
        $scope.signUpScreen = {
            state: false
        },
        $scope.mainScreen = {
            state: false
        }
    ];
    $scope.backgroundImg = "images/backgroundImage.jpg";

    $scope.authenticatedLogin = false;
    $scope.authenticatedSignUp = false;
    $rootScope.user;
    $scope.usernameMessage;
    $scope.signUpMessage;

    //Retrieve JSON data from localusers.json and save it in $rootScope field user
    const fs = require('fs');
    let userFile = fs.readFileSync('localusers.json');
    let userData = JSON.parse(userFile);

    $rootScope.updateJson = (str) => {
        let configJSON = angular.toJson(str, 4);
        fs.writeFileSync('localusers.json', configJSON);
    }

    $scope.login = (username, pass, func, index, list, property) => {
        if (username.length < 5) {
            $scope.authenticatedLogin = false;
            $scope.usernameMessage = "Please enter a valid username (greater than four characters)";
        } else {
            userData.forEach((item, key) => {
                if (username == item.username && pass == item.password) {
                    $rootScope.user = item;
                    $scope.authenticatedLogin = true;
                    $scope.usernameMessage = "Welcome back, " + $rootScope.user.realName + "!";
                    func(index, list, property);
                } else {
                    $scope.usernameMessage = "Username or password incorrect";
                    $scope.authenticatedLogin = false;
                }
            })
        }
    };

    $scope.addUser = (username, realName, password) => {
        let object = {};
        realName = realName.charAt(0).toUpperCase() + realName.slice(1);
        object.username = username;
        object.realName = realName;
        object.password = password;
        object.major = "N/A";
        object.minor = "N/A";
        object.year = "N/A";
        object.gpa = "N/A";

        if (object.username.length >= 5 && object.username) {
            if (!$rootScope.checkForInstance(object.username, userData, 'username')) {
                console.log("User", object.username, "has been added to database");
                userData.push(object);
                $scope.signUpMessage = "Username " + object.username + " successfully created!"
                $scope.authenticatedSignUp = true;
                //Returns userData in alphabetical order
                userData.sort(function(a, b) {
                    let nA = a.username.toLowerCase();
                    let nB = b.username.toLowerCase();
                    if (nA < nB) {
                        return -1;
                    } else if (nA > nB) {
                        return 1;
                    }
                    return 0;
                });

            } else {

            }
        } else {
            $scope.signUpMessage = "Error: Please choose a username larger than four characters!"
            $scope.authenticatedSignUp = false;
        }
        $rootScope.updateJson(userData);
    };

    $rootScope.updateUser = (major, minor, year, gpa) => {
      $rootScope.user.major = major;
      if (!$rootScope.user.major)
        $rootScope.user.major = "N/A"
      $rootScope.user.minor = minor;
      if (!$rootScope.user.minor)
        $rootScope.user.minor = "N/A"
      $rootScope.user.year = year;
      if (!$rootScope.user.year)
        $rootScope.user.year = "N/A"
      $rootScope.user.gpa = gpa;
      if (!$rootScope.user.gpa)
        $rootScope.user.gpa = "N/A"
      userData.forEach((item, index) => {
          if (item.username == $rootScope.user.username) {
              userData[index] = $rootScope.user
          }
      })
      $rootScope.updateJson(userData);
      $scope.updateUserMsg = "User updated!";
    };

    $scope.createSession = (name) => {
      let time = new Date();
      $scope.adminID = String(name) + time.valueOf();
      $scope.applicantID = String(name) + String(Math.floor((Math.random() * 100000000) + 1));
      let sessionFile = fs.readFileSync('session.json');
      let sessionData = JSON.parse(sessionFile);

      let object = {};

      object.adminID = $scope.adminID;
      object.applicantID = $scope.applicantID;
      object.apps = [];

      sessionData.push(object);

      let configSessionJSON = angular.toJson(sessionData, 4);
      fs.writeFileSync('session.json', configSessionJSON);
    };

    $scope.addSessionApp = (id) => {
      let sessionFile = fs.readFileSync('session.json');
      let sessionData = JSON.parse(sessionFile);
      sessionData.forEach((item, key) => {
          if (id == item.applicantID) {
            let object = {};
            object.username = $rootScope.user.username;
            object.realName = $rootScope.user.realName;
            object.major = $rootScope.user.major;
            object.minor = $rootScope.user.minor;
            object.year = $rootScope.user.year;
            object.gpa = $rootScope.user.gpa;

            sessionData[key].apps.push(object);

            let configSessionJSON = angular.toJson(sessionData, 4);
            fs.writeFileSync('session.json', configSessionJSON);
          }
    })
  }

    //changeToTrue takes a list and changes all elements to the falseValue except the one at index elem, which becomes trueValue
    $rootScope.changeToTrue = (elem, list, property) => {
        list.forEach((item, key) => {
            if (elem !== key) {
                item[property] = false;
            } else {
                item[property] = true;
            }
        })
        return list;
    };
    $rootScope.changeToFalse = (list, property, falseValue) => {
        list.forEach((item, key) => {
            item[property] = falseValue;
        })
        return list;
    };
    $rootScope.checkForInstance = (elem, list, property) => {
        var checker = false;
        list.forEach((item, key) => {
            if (elem == item[property]) {
                checker = true;
            }
        });
        if (checker == true) {
            return true;
        } else {
            return false;
        }
    };
    $rootScope.retrieveIndex = (index, property) => {
        $rootScope[property] = index;
    };

});
