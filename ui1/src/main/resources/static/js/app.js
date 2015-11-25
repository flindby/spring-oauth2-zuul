//Angular module 
angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/protected', {
		templateUrl : 'protected.html',
		controller : 'protected'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';	
})

//Navigation controller
.controller('navigation', function($rootScope, $scope, $http, $location, $route) {

	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	$http.get('user').success(function(data) {
		if (data.name) {
			$rootScope.authenticated = true;
			$rootScope.loggedInUser = data;
		} else {
			$rootScope.authenticated = false;
		}
	}).error(function() {
		$rootScope.authenticated = false;
	});

	$scope.credentials = {};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed")
			$rootScope.authenticated = false;
		});
	}
})

.controller('home', function($scope, $http) {
	$http.get('resource/test').success(function(data) {
		$scope.greeting = data;
	})
})

.controller('protected', function($scope, $http) {	
	$http.get('resource/test').success(function(data) {
		$scope.test = data;
	})
});