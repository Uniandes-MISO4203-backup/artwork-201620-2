(function (ng) {
    var mod = ng.module("messageModule");
    
    mod.controller("messageListCtrl", ["$scope", '$state', 'messages', '$stateParams','$rootScope',
        function ($scope, $state, messages, $params, $rootScope) {
            $scope.records = messages;
            var roles = $rootScope.roles;

            //Paginación
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = messages.totalRecords;

            this.pageChanged = function () {
                $state.go('messageList', {page: this.currentPage});
            };

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
                refresh: {
                    displayName: 'Refresh',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                }            };
            $scope.recordActions = {
                detail: {
                    displayName: 'Detail',
                    icon: 'eye-open',
                    fn: function (rc) {
                        $state.go('messageDetail', {messageId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function (rc) {
                        $state.go('messageEdit', {messageId: rc.id});
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function (rc) {
                        $state.go('messageDelete', {messageId: rc.id});
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                }
            };
        }]);    
    
    
})(window.angular);    