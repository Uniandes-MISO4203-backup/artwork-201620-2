(function (ng) {

    var mod = ng.module("checkoutModule");

    mod.controller("checkoutListCtrl", ["$scope", '$state', 'item', '$stateParams', 'Restangular',
        function ($scope, $state, item, $params, r) {
            $scope.cart = item;
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = item.cartItems.totalRecords;

            this.pageChanged = function () {
                $state.go('shoppingCart', {page: this.currentPage});
            };
        }]);
})(window.angular);