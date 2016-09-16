(function (ng) {
    var mod = ng.module('qualifyModule', ['ngCrud', 'ui.router']);
    mod.directive('qualify', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/qualify/qualify.tpl.html',
            scope: {
                artwork: "="
            },
            controller: controller
        };
    });

    function controller($scope, Restangular) {
        $scope.qualify = null;
        $scope.empty = true;
        $scope.score = null;
        Restangular.all('qualifys').customGET($scope.artwork.id).then(function (data) {
            if(data!==null) {
                $scope.score= data;
                $scope.empty = false;
            }
        });
        $scope.postQualify = function() {
            var qualify = $scope.qualify;
            if(qualify) {
                qualify.artwork = $scope.artwork;
                
                Restangular.all('qualifys').post(qualify).then(function(data) {
                    $scope.empty = false;
                    $scope.qualify = null;
                    Restangular.all('qualifys').customGET($scope.artwork.id).then(function (data) {
                    $scope.score= data;
                }); 
            });
            }  
        };
    }
    
})(window.angular);
