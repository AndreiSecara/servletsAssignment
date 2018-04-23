(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoAddCtrl", TodoAddCtrl);

    TodoAddCtrl.$inject = ['$scope', '$location', 'TodoService'];

    function TodoAddCtrl($scope, $location, TodoService) {

        $scope.data = {};

        $scope.addTodo = function() {
            TodoService.save($scope.data)
                .then(function (res) {
                    alert(res.data);

                    TodoService.list()
                        .then(function (res) {
                            $scope.todo = res.data;
                        }, function () {
                            $scope.todos = [];
                        });

                    $location.path('/#/');

                }, function () {
                    alert(res.data);
                });
        };
    };
}());