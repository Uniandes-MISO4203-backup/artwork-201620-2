(function (ng) {

    var mod = ng.module("creditCardModule");

    mod.controller("creditCardDeleteCtrl", ["$state", "item", function ($state, item) {
            this.confirmDelete = function () {
                item.remove().then(function () {
                    $state.go('creditCardList', null, {reload: true});
                });
            };
        }]);
})(window.angular);
