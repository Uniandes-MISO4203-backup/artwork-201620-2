(function (ng) {

    var mod = ng.module("itemModule");

    mod.controller("creditCardDeleteCtrl", ["$state", "item", function ($state, item) {
            this.confirmDelete = function () {
                item.remove().then(function () {
                    $state.go('creditCardList', null, {reload: true});
                });
            };
        }]);
})(window.angular);
