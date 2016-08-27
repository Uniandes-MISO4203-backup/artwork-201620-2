(function (ng) {
    
    var mod = ng.module('creditCardModule', ['ngCrud', 'ui.router']);

    mod.constant('creditCardModel', {
        name: 'creditcard',
        displayName: 'Credit Card',
	url: 'creditCard',
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
            expirationMonth : {
                name: 'expirationMonth',
                displayName: 'Expiration Month',
                type: 'Long',
                required: true                 
            }
        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/creditcard/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('creditcard', {
                url: '/creditCard?page&limit',
                abstract: true,
                parent: 'clientDetail',
                views: {
                     clientChieldView: {
                        templateUrl: basePath + 'creditcard.tpl.html',
                        controller: 'creditCardCtrl'
                    }
                },
                resolve: {
                    references: ['$q', 'Restangular', function ($q, r) {
                            return $q.all({
                                artwork: r.all('artworks').getList()
,                                 product: r.all('products').getList()
                            });
                        }],
                    model: 'creditCardModel',
                    items: ['client', '$stateParams', 'model', function (client, $params, model) {
                            return client.getList(model.url, $params);
                        }]                }
            });
            $sp.state('creditCardList', {
                url: '/list',
                parent: 'creditcard',
                views: {
                    creditCardView: {
                        templateUrl: basePath + 'list/creditcard.list.tpl.html',
                        controller: 'creditCardListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('creditCardNew', {
                url: '/new',
                parent: 'creditcard',
                views: {
                    creditCardView: {
                        templateUrl: basePath + 'new/creditcard.new.tpl.html',
                        controller: 'creditCardNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('creditCardInstance', {
                url: '/{creditCardId:int}',
                abstract: true,
                parent: 'creditcard',
                views: {
                    creditCardView: {
                        template: '<div ui-view="creditCardInstanceView"></div>'
                    }
                },
                resolve: {
                    item: ['items', '$stateParams', function (items, $params) {
                            return items.get($params.creditCardId);
                        }]
                }
            });
            $sp.state('creditCardDetail', {
                url: '/details',
                parent: 'creditCardInstance',
                views: {
                    creditCardInstanceView: {
                        templateUrl: baseInstancePath + 'detail/creditcard.detail.tpl.html',
                        controller: 'creditCardDetailCtrl'
                    }
                }
            });
            $sp.state('creditCardEdit', {
                url: '/edit',
                sticky: true,
                parent: 'creditCardInstance',
                views: {
                    creditCardInstanceView: {
                        templateUrl: baseInstancePath + 'edit/creditcard.edit.tpl.html',
                        controller: 'creditCardEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('creditCardDelete', {
                url: '/delete',
                parent: 'creditCardInstance',
                views: {
                    creditCardInstanceView: {
                        templateUrl: baseInstancePath + 'delete/creditcard.delete.tpl.html',
                        controller: 'creditCardDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);