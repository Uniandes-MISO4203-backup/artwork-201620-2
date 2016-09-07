(function (ng) {
    ng.directive('commentDir', function() {
        return {
            restrict: 'E',
            scope: {
                comments: '='
            },
            templateUrl: 'src/modules/comment/comment.tpl.html',
            controller:controller
        };
    });
    
    function controller($scope, Restangular) {
        $scope.comment = null;    
    };

})(window.angular);
