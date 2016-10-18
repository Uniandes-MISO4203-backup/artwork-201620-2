(function (ng) {

    var mod = ng.module("wishListModule");

    mod.controller("wishListListCtrl", ["$scope", '$state', 'items', '$stateParams','$rootScope', 'Restangular',
        function ($scope, $state, items, $params, r) {
            $scope.name=null;
            $scope.records = items;
            console.log(items);
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = items.totalRecords;

            this.pageChanged = function () {
                $state.go('wishList', {page: this.currentPage});
            };
        }]);
})(window.angular);
