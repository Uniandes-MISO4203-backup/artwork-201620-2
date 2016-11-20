(function (ng) {

    var mod = ng.module("checkoutModule");

    mod.controller("checkoutListCtrl", ["$scope", '$state', 'item', '$stateParams',
        function ($scope, $state, item, $params) {
            $scope.records = item;
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = item.totalRecords;

            this.pageChanged = function () {
                $state.go('checkoutList', {page: this.currentPage});
            };
        }]);
})(window.angular);