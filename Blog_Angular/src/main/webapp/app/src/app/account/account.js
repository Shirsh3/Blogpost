angular.module('ngBoilerplate.account', ['ui.router','ngResource'])
.config(function($stateProvider) {
	$stateProvider.state('login', {
		url:'/login',
		views: {
			'main': {
				templateUrl:'account/login.tpl.html',
				controller: 'LoginCtrl'
			}
		},
		data : { pageTitle : "Login" }
	})
	.state('register', {
		url:'/register',
		views: {
			'main': {
				templateUrl:'account/register.tpl.html',
				controller: 'RegisterCtrl'
			}
		},
		data : { pageTitle : "Registration" }
	}
	);
})
.factory('accountService',function($resource){
	
	var service={};
	service.onRegister=function(accountData,success,failure){
		
		var Account_rest=$resource("");
		Account_rest.save();
	};
	return service;
})
.factory('sessionService',function(){

	var session={};
	session.login=function(data){
		alert ("user logged in with " + data.name + "password" + data.password);
		localStorage.setItem("session", data);
	};
	session.logout=function(data){
		localStorage.removeItem("session", data);
	};
	session.isLoggedin=function(){
		
		return localStorage.getItem("session")!==null;
	};
	return session;
})


.controller("LoginCtrl", function($scope,sessionService,$state) {
	$scope.login = function() {
		sessionService.login($scope.account);
		$state.go("home");
	};
})
.controller("RegisterCtrl", function($scope,sessionService,$state) {
	$scope.register = function() {
		sessionService.login($scope.account);
		$state.go("about");
	};
});