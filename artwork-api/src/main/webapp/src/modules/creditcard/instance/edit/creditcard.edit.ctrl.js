(function (ng) {

    var mod = ng.module("creditCardModule");

    mod.controller("creditCardEditCtrl", ["$scope", "$state", "item",
        function ($scope, $state, item) {
            $scope.currentRecord = item;
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.itemForm.$valid) {
                            $scope.currentRecord.put().then(function (rc) {
                                $state.go('creditCardDetail', {creditCardId: rc.id}, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('creditCardDetail');
                    }
                }
            };
        }]);
})(window.angular);
