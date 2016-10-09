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
        $scope.qualify = {
            score:3
        };
        $scope.modalId = 'ratingModal' + $scope.artwork.id;
        $scope.star5id = 'star-5' + $scope.artwork.id;
        $scope.star4id = 'star-4' + $scope.artwork.id;
        $scope.star3id = 'star-3' + $scope.artwork.id;
        $scope.star2id = 'star-2' + $scope.artwork.id;
        $scope.star1id = 'star-1' + $scope.artwork.id;
        
        $scope.empty = true;
        $scope.score = null;
        
        $scope.alreadyQualifiedError = false;
        
        Restangular.one('qualifys', $scope.artwork.id).customGET('score').then(function (data) {
            if(data!==null) {
                $scope.score= data;
                $scope.empty = false;
            }
        });
        
        $scope.comments = [];
        
        function mapSimplify(data){
            var res = [];
            for(var n = 0; n < data.length ;n++){
                var elem = {
                    message: data[n].message,
                    score: data[n].score,
                    client: {
                        name: data[n].client.name,
                        id: data[n].client.id
                    }
                };
                res.push(elem);
            }
            return res;
        }
        
        Restangular.one('qualifys', $scope.artwork.id).get().then(function (data) {
            $scope.comments = mapSimplify(data);
        });
        
        $scope.postQualify = function() {
            var qualify = $scope.qualify;
            if(qualify) {
                qualify.artwork = $scope.artwork;
                
                Restangular.all('qualifys').post(qualify).then(function(data) {
                    $scope.empty = false;
                    $scope.qualify = null;
                    Restangular.one('qualifys', $scope.artwork.id).customGET('score').then(function (data) {
                        $scope.score= data;
                    });
                    Restangular.one('qualifys', $scope.artwork.id).get().then(function (data) {
                        $scope.comments = mapSimplify(data);
                    });
                }, function(err){
                    $scope.alreadyQualifiedError = true;
                });
            }  
        };
    }
    
})(window.angular);
