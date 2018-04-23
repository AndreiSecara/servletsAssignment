(function () {
    'use strict';

    angular.module('todoApp', ['ngRoute'])
        .config(Config);

    Config.$inject = ['$routeProvider', '$locationProvider'];

    function Config($routeProvider, $locationProvider) {
        $locationProvider.hashPrefix('');

        $routeProvider
            .when('/',
            {
                templateUrl: 'src/pages/todo/list/list.html',
                controller: 'TodoListCtrl'
            })
            .when('/todoDetail/:todoId',
                {
                    templateUrl: 'src/pages/todo/details/detail.html',
                    controller: 'TodoDetailCtrl'
                })
            .when('/addTodo' ,
                {
                    templateUrl: 'src/pages/todo/add/add.html',
                    controller: 'TodoAddCtrl'
                })
            .when('/updateTodo/:todoId',
                {
                    templateUrl: 'src/pages/todo/update/update.html',
                    controller: 'TodoUpdateCtrl'
                })
            .when('/deleteTodo/:todoId',
                {
                    templateUrl: 'src/pages/todo/delete/delete.html',
                    controller: 'TodoDeleteCtrl'
                })
            .otherwise({redirectTo: '/'});
    }

})();
