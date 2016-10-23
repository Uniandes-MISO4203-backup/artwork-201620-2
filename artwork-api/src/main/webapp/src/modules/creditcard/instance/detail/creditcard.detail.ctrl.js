(function (ng) {

    var mod = ng.module("creditCardModule");

    mod.controller("creditCardDetailCtrl", ['$scope', "$state", "item", '$rootScope',
        function ($scope, $state, item, $rootScope) {
            $scope.currentRecord = item;
            var roles = $rootScope.roles;
            
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('creditCardNew');
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function () {
                        $state.go('creditCardEdit');
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function () {
                        $state.go('creditCardDelete');
                    },
                    show: function () {
                        return (roles.indexOf("admin") !== -1);
                    }
                },
                refresh: {
                    displayName: 'Refresh',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                },
                list: {
                    displayName: 'List',
                    icon: 'th-list',
                    fn: function () {
                        $state.go('creditCardList');
                    }
                }
            };
        }]);
})(window.angular);
