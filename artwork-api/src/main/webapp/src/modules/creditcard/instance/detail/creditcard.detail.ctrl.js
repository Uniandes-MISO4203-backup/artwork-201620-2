(function (ng) {

    var mod = ng.module("creditCardModule");

    mod.controller("creditCardDetailCtrl", ['$scope', "$state", "item",
        function ($scope, $state, item) {
            $scope.currentRecord = item;
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
