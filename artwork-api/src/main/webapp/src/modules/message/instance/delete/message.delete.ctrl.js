(function (ng) {

    var mod = ng.module("messageModule");

    mod.controller("messageDeleteCtrl", ["$state", "message", function ($state, message) {
            this.confirmDelete = function () {
                message.remove().then(function () {
                    $state.go('messageList', null, {reload: true});
                });
            };
        }]);
})(window.angular);
