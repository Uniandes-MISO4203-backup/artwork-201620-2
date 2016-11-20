(function (ng) {
    var mod = ng.module('checkoutModule', ['ngCrud', 'ui.router']);
    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/checkout/';
            
            $sp.state('checkoutList', {
                url: '/checkout?page&limit',
                views: {
                     mainView: {
                        templateUrl: basePath + 'list/checkout.list.tpl.html',
                        controller: 'checkoutListCtrl'
                    }
                },
                resolve: {
                    item: ['Restangular', function (r) {
                        return r.one('checkout').get();
                    }]
                }
            });
        }
    ]);
})(window.angular);