(function (ng) {

    var mod = ng.module("creditCardModule");

    mod.controller("creditCardListCtrl", ["$scope", '$state', 'items', '$stateParams','$rootScope',
        function ($scope, $state, items, $params, $rootScope) {
            $scope.records = items;
            var roles = $rootScope.roles;            

            //Paginación
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = items.totalRecords;

            this.pageChanged = function () {
                $state.go('creditCardList', {page: this.currentPage});
            };

            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('creditCardNew');
                    }
                },
                refresh: {
                    displayName: 'Refresh',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                },
                cancel: {
                    displayName: 'Go back',
                    icon: 'arrow-left',
                    fn: function () {
                        $state.go('clientDetail');
                    }
                }

            };
            $scope.recordActions = {
                detail: {
                    displayName: 'Detail',
                    icon: 'eye-open',
                    fn: function (rc) {
                        $state.go('creditCardDetail', {creditCardId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function (rc) {
                        $state.go('creditCardEdit', {creditCardId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function (rc) {
                        $state.go('creditCardDelete', {creditCardId: rc.id});
                    },
                    show: function () {
                        return (roles.indexOf("admin") !== -1);
                    }
                }
            };
        }]);
})(window.angular);
