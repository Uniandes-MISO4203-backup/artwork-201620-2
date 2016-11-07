(function (ng) {

    var mod = ng.module("shoppingCartModule");

    mod.controller("shoppingCartListCtrl", ["$scope", '$state', 'item', '$stateParams', 'Restangular',
        function ($scope, $state, item, $params, r) {
            $scope.cart = item;
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = item.cartItems.totalRecords;

            this.pageChanged = function () {
                $state.go('shoppingCart', {page: this.currentPage});
            };
            
            $scope.removeItem = function(index) {
                return r.one('shoppingCart', $scope.cart.cartItems[index].id).remove().then(function() {
                    $state.reload();                    
                }).catch(function() {
                });
            };
        }]);
})(window.angular);