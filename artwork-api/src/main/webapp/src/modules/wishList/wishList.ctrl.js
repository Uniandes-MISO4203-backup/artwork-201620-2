(function (ng) {
    var mod = ng.module('wishListModule');
    
    mod.controller('wishListCtrl', function($scope, Restangular) {
        $scope.added=false;
        $scope.addToWishList = function() {
            var item = {
                name:$scope.artwork.name,
                qty:1,
                artwork:$scope.artwork
            };
            Restangular.all('wishList').post(item).then(function(data) { 
                $scope.added=true;
            });
        };
    });
})(window.angular);