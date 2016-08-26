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
    var mod = ng.module('clientModule', ['ngCrud', 'ui.router']);

    mod.constant('clientModel', {
        name: 'client',
        displayName: 'Client',
		url: 'clients',
        fields: {            
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            },
            age:{
                displayName: 'Age',
                type: 'Long',
                required: true
            }
        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/client/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('client', {
                url: '/clients?page&limit',
                abstract: true,
                
                views: {
                     mainView: {
                        templateUrl: basePath + 'client.tpl.html',
                        controller: 'clientCtrl'
                    }
                },
                resolve: {
                    model: 'clientModel',
                    clients: ['Restangular', 'model', '$stateParams', function (r, model, $params) {
                            return r.all(model.url).getList($params);
                        }]
                }
            });
            $sp.state('clientList', {
                url: '/list',
                parent: 'client',
                views: {
                    clientView: {
                        templateUrl: basePath + 'list/client.list.tpl.html',
                        controller: 'clientListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('clientNew', {
                url: '/new',
                parent: 'client',
                views: {
                    clientView: {
                        templateUrl: basePath + 'new/client.new.tpl.html',
                        controller: 'clientNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('clientInstance', {
                url: '/{clientId:int}',
                abstract: true,
                parent: 'client',
                views: {
                    clientView: {
                        template: '<div ui-view="clientInstanceView"></div>'
                    }
                },
                resolve: {
                    client: ['clients', '$stateParams', function (clients, $params) {
                            return clients.get($params.clientId);
                        }]
                }
            });
            $sp.state('clientDetail', {
                url: '/details',
                parent: 'clientInstance',
                views: {
                    clientInstanceView: {
                        templateUrl: baseInstancePath + 'detail/client.detail.tpl.html',
                        controller: 'clientDetailCtrl'
                    }
                }
            });
            $sp.state('clientEdit', {
                url: '/edit',
                sticky: true,
                parent: 'clientInstance',
                views: {
                    clientInstanceView: {
                        templateUrl: baseInstancePath + 'edit/client.edit.tpl.html',
                        controller: 'clientEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('clientDelete', {
                url: '/delete',
                parent: 'clientInstance',
                views: {
                    clientInstanceView: {
                        templateUrl: baseInstancePath + 'delete/client.delete.tpl.html',
                        controller: 'clientDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
