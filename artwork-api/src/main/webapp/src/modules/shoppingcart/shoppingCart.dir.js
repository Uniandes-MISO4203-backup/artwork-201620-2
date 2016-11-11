(function (ng) {
    var mod = ng.module('shoppingCartModule');
    
    mod.directive('addShoppingCart', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/shoppingcart/shoppingCart.tpl.html',
            scope: {
                artwork: "="
            },
            controller: controller
        };
    });

    controller.$inject = ['$scope', '$rootScope', '$state', '$modal', 'Restangular'];
    function controller($scope, $rootScope, $state, $modal, Restangular){
        var addToCart = function(artwork){
            return Restangular.all('shoppingCart').post({
                quantity: 1,
                artwork: artwork
            }).then(function() { 
                $modal.open({
                    templateUrl: 'src/modules/shoppingcart/shoppingCartModal.tpl.html',
                    controller: modalController,
                    scope: $scope,
                    resolve: {
                        artwork: artwork
                    }
                });
            });
        };

        $scope.addToCart = function(artwork){
            if ($rootScope.CheckAuthenticated()) {
                addToCart(artwork);
            }
        };
    };
    
    modalController.$inject = ['$scope', '$modalInstance', '$state'];
    function modalController($scope, $modalInstance, $state){
        $scope.goToCart = function(){
            $modalInstance.close($state.go('shoppingCart'));
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
})(window.angular);