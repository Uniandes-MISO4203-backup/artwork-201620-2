(function (ng) {

    var mod = ng.module("wishListModule");

    mod.controller("wishListListCtrl", ["$scope", '$state', 'items', '$stateParams','$rootScope', 'Restangular',
        function ($scope, $state, items, $params, r) {
            $scope.records = items;
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = items.totalRecords;

            this.pageChanged = function () {
                $state.go('wishList', {page: this.currentPage});
            };
            
            $scope.removeItem = function(index) {
                console.log("Entra");
                $scope.records[index].remove().then(function(data) {
                    $scope.records.splice(index,1);
                }).catch(function(data) {
                    alert(data.data);
                });
            };
        }]);
})(window.angular);
