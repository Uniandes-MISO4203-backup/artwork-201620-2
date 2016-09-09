(function (ng) {

    var mod = ng.module("messageModule");

    mod.controller("messageEditCtrl", ["$scope", "$state", "message",
        function ($scope, $state, message) {
            $scope.currentRecord = message;
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.messageForm.$valid) {
                            $scope.currentRecord.put().then(function (rc) {
                                $state.go('messageDetail', {messageId: rc.id}, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('messageDetail');
                    }
                }
            };
        }]);
})(window.angular);