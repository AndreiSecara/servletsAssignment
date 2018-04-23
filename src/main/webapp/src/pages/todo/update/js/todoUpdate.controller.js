(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoUpdateCtrl", TodoUpdateCtrl);

    TodoUpdateCtrl.$inject = ['$scope', '$location', 'TodoService'];

    function TodoUpdateCtrl($scope, $location, TodoService) {

        $scope.data = {};

        $scope.updateTodo = function() {
            TodoService.update($scope.data)
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
    }
})();