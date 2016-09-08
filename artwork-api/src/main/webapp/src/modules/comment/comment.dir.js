(function (ng) {
    var mod = ng.module('commentModule', ['ngCrud', 'ui.router']);
    mod.directive('comment', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/comment/comment.tpl.html',
            scope: {
                artwork: "="
            },
            controller: controller
        };
    });

    function controller($scope, Restangular) {
        console.log("artwork",$scope.artwork);
        Restangular.all('comments').customGET($scope.artwork.id).then(function (data) {
            console.log("Resultado",data);
        });
        $scope.comments=[{"message":"a"},{"message":"b"},{"message":"c"}];
    }
    
})(window.angular);
