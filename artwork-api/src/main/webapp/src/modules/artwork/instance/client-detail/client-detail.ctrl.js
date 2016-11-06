(function (ng) {

    var mod = ng.module("artworkModule");

    mod.filter('trusted', ['$sce', function ($sce) {
        return function(url) {
            return $sce.trustAsResourceUrl(url);
        };
    }]);

    mod.controller("artworkClientDetailCtrl", ['$scope', "artwork",
        function ($scope, artwork) {
            $scope.artwork = artwork;
            $scope.detail = 'images';
            $scope.changeDetail = function(detail) {
                $scope.detail = detail;
            };
        }]);
})(window.angular);