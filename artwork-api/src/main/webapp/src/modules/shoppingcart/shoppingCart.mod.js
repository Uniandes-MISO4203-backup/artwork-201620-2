(function (ng) {
    var mod = ng.module('shoppingCartModule', ['ngCrud', 'ui.router']);
    
    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/shoppingcart/';
            $sp.state('shoppingCart', {
                url: '/shoppingCart?page&limit',
                views: {
                     mainView: {
                        templateUrl: basePath + 'list/shoppingCart.list.tpl.html',
                        controller: 'shoppingCartListCtrl'
                    }
                },
                resolve: {
                    item: ['Restangular', function (r) {
                        return r.one('shoppingCart').get();
                    }]
                }
            });
        }
    ]);
})(window.angular);