(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoDetailCtrl", TodoDetailCtrl);

    TodoDetailCtrl.$inject = ['$scope', '$routeParams', 'TodoService'];

    function TodoDetailCtrl($scope, $routeParams, TodoService) {

        var id = $routeParams.todoId;
        TodoService.get(id)
            .then(function (res) {
                $scope.todo = res.data;
            }, function () {
                $scope.todo = {};
            });
    }

})();