(function (ng) {
    var mod = ng.module('checkoutModule', ['ngCrud', 'ui.router']);
    
    mod.constant('checkoutModel', {
        name: 'checkout',
        displayName: 'Check out',
	url: 'checkout',
        fields: {
            number : { 
                name: 'number',
                displayName: 'Number',
                type: 'String',
                required: true
            }, 
            type : {
                name: 'type',
                displayName: 'Type',
                type: 'String',
                required: true
            }, 
            expirationYear : {
                name: 'expirationYear',
                displayName: 'Expiration Year',
                type: 'Long',
                required: true  
            },
            creditcard: {
                displayName: 'Credit Card',
                type: 'Reference',
                url: 'creditCardModel',
                options: [],
                required: true        
            }
        }
    });    
    
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