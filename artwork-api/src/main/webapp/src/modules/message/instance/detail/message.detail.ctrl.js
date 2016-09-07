(function (ng) {

    var mod = ng.module("messageModule");

    mod.controller("messageDetailCtrl", ['$scope', "$state", "message",'$rootScope',
        function ($scope, $state, message,$rootScope) {
            $scope.currentRecord = message;
            var roles = $rootScope.roles;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('messageNew');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function () {
                        $state.go('messageEdit');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function () {
                        $state.go('messageDelete');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
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
                        $state.go('messageList');
                    }
                }
            };
        }]);
})(window.angular);