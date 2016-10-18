(function (ng) {
    var mod = ng.module('wishListModule');
    
    mod.directive('addWishList', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/wishList/wishList.tpl.html',
            scope: {
                artwork: "="
            },
            controller: 'wishListCtrl'
        };
    });
})(window.angular);


