/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
(function (ng) {
    var mod = ng.module('itemModule', ['ngCrud', 'ui.router']);

    mod.constant('itemModel', {
        name: 'item',
        displayName: 'Item',
		url: 'wishList',
        fields: {            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            },
            qty: {
                displayName: 'Qty',
                type: 'Long',
                required: true
            },
            artwork: {
                displayName: 'Artwork',
                type: 'Reference',
                model: 'artworkModel',
                options: [],
                required: true
            },
            product: {
                displayName: 'Product',
                type: 'Reference',
                model: 'productModel',
                options: [],
                required: false
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/item/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('item', {
                url: '/wishList?page&limit',
                abstract: true,
                parent: 'clientDetail',
                views: {
                     clientChieldView: {
                        templateUrl: basePath + 'item.tpl.html',
                        controller: 'itemCtrl'
                    }
                },
                resolve: {
                    references: ['$q', 'Restangular', function ($q, r) {
                            return $q.all({
                                artwork: r.all('artworks').getList()
,                                 product: r.all('products').getList()
                            });
                        }],
                    model: 'itemModel',
                    items: ['client', '$stateParams', 'model', function (client, $params, model) {
                            return client.getList(model.url, $params);
                        }]                }
            });
            $sp.state('itemList', {
                url: '/list',
                parent: 'item',
                views: {
                    itemView: {
                        templateUrl: basePath + 'list/item.list.tpl.html',
                        controller: 'itemListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('itemNew', {
                url: '/new',
                parent: 'item',
                views: {
                    itemView: {
                        templateUrl: basePath + 'new/item.new.tpl.html',
                        controller: 'itemNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('itemInstance', {
                url: '/{itemId:int}',
                abstract: true,
                parent: 'item',
                views: {
                    itemView: {
                        template: '<div ui-view="itemInstanceView"></div>'
                    }
                },
                resolve: {
                    item: ['items', '$stateParams', function (items, $params) {
                            return items.get($params.itemId);
                        }]
                }
            });
            $sp.state('itemDetail', {
                url: '/details',
                parent: 'itemInstance',
                views: {
                    itemInstanceView: {
                        templateUrl: baseInstancePath + 'detail/item.detail.tpl.html',
                        controller: 'itemDetailCtrl'
                    }
                }
            });
            $sp.state('itemEdit', {
                url: '/edit',
                sticky: true,
                parent: 'itemInstance',
                views: {
                    itemInstanceView: {
                        templateUrl: baseInstancePath + 'edit/item.edit.tpl.html',
                        controller: 'itemEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('itemDelete', {
                url: '/delete',
                parent: 'itemInstance',
                views: {
                    itemInstanceView: {
                        templateUrl: baseInstancePath + 'delete/item.delete.tpl.html',
                        controller: 'itemDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
