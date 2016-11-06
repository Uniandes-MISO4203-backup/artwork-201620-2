(function (ng) {

    var mod = ng.module("artworkModule");

    mod.controller("artworkClientDetailCtrl", ['$scope', "artwork",
        function ($scope, artwork) {
            $scope.artwork = artwork;
            console.log($scope.artwork);
        }]);
})(window.angular);