(function (ng) {

    var mod = ng.module("messageModule");

    mod.controller("messageNewCtrl", ["$scope", "$state", "messages",
        function ($scope, $state, messages) {
            $scope.currentRecord = {};
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.messageForm.$valid) {
                            messages.post($scope.currentRecord).then(function (rc) {
                                $state.go('messageDetail', {messageId: rc.id}, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('messageList');
                    }
                }
            };
        }]);
})(window.angular);