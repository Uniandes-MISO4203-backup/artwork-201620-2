(function (ng) {

    var mod = ng.module("artworkModule");

    mod.controller("artworkClientDetailCtrl", ['$scope', "artwork",
        function ($scope, artwork) {
            $scope.currentRecord = artwork;
        }]);
})(window.angular);