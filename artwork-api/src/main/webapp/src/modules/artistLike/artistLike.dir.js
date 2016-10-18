/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function (ng) {
    var mod = ng.module('artistLikeModule', []);

    mod.directive('artistLike', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/artistLike/artistLike.tpl.html',
            scope: {
                artist: "="
            },
            controller: controller
        };
    });

    controller.$inject = ['$scope', '$state','Restangular'];
    function controller($scope, $state, Restangular){
        var postLike = function(artist){
            return Restangular.all('artistLike').post({
                name: "Like name",
                artist: artist
            });
        };
        var getLikes = function(artistId){
            return Restangular.one('artistLike', artistId).get();
        };

        getLikes($scope.artist.id).then(function(res){
          $scope.numLikes = res;
        });

        $scope.addLike = function(artist){
            postLike(artist).then(function(){
                $state.reload();
            });
        };

    };

})(window.angular);

