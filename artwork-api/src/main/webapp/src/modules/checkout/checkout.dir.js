(function (ng) {
    var mod = ng.module('checkoutModule');
    
    mod.directive('checkOut', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/checkout/checkout.tpl.html',
            controller: "checkoutCtrl",
            scope: {
                cart: "="
            }            
        };
    });
    
})(window.angular);