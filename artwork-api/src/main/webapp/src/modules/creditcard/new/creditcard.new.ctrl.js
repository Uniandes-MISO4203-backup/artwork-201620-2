(function (ng) {

    var mod = ng.module("creditCardModule");

    mod.controller("creditCardNewCtrl", ["$scope", "$state", "items",
        function ($scope, $state, items) {
            $scope.currentRecord = {};
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.itemForm.$valid) {
                            items.post($scope.currentRecord).then(function (rc) {
                                $state.go('creditCardDetail', {creditCardId: rc.id}, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('creditCardList');
                    }
                }
            };
        }]);
})(window.angular);
