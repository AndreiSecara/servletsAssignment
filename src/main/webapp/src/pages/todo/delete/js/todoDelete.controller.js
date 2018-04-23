(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoDeleteCtrl", TodoDeleteCtrl);

    TodoDeleteCtrl.$inject = ['$scope', '$routeParams', '$location', 'TodoService'];

    function TodoDeleteCtrl($scope, $routeParams, $location, TodoService) {

        var id = $routeParams.todoId;

        $scope.deleteTodo = function() {
            TodoService.deleteTodo(id)
                        .then(function (res) {
                            $scope.todos = res.data;
                        }, function () {
                            $scope.todos = [];
                        });
            $location.path('/#/');
        };

        $scope.turnBack = function() {
            $location.path('/#/');
        };

    };
})
();
