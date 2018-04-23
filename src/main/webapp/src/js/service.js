(function () {

    'use strict';

    angular.module("todoApp")
        .service("TodoService", TodoService);

    TodoService.$inject = ['$http'];

    function TodoService($http) {
        return {
            list: function() {
                return $http.get('todos');
            },

            get: function(id) {
                var requestConfig = {
                    params: {id: id}
                };
                return $http.get('todos' , requestConfig);
            },

            deleteTodo: function(id) {
                var requestConfig = {
                    params: {id: id}
                    };
                return $http.delete('todos' , requestConfig);
            },

            update: function(data) {
                return $http.put('todos', data, null);
            },

            save: function(data) {
                return $http.post('todos', data, null);
            }
        };
    }

})();