(function (ng) {

    var mod = ng.module("wishListModule");

    mod.controller("wishListListCtrl", ["$scope", '$state', 'items', '$stateParams','$rootScope', 'Restangular',
        function ($scope, $state, items, $params) {
            $scope.records = items;
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = items.totalRecords;

            this.pageChanged = function () {
                $state.go('wishList', {page: this.currentPage});
            };
            
            $scope.removeItem = function(index) {
                $scope.records[index].remove().then(function() {
                    $scope.records.splice(index,1);
                }).catch(function() {
                });
            };
        }]);
})(window.angular);
