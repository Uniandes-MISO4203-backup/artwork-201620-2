(function (ng) {
    var mod = ng.module('wishListModule', ['ngCrud', 'ui.router']);
    
    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/wishList/';
            $sp.state('wishList', {
                url: '/wishList?page&limit',
                views: {
                     mainView: {
                        templateUrl: basePath + 'list/wishList.list.tpl.html',
                        controller: 'wishListListCtrl'
                    }
                },
                resolve: {
                    items: ['Restangular', '$stateParams', function (r, $params) {
                        return r.all('wishList').getList($params);
                    }]
                }
            });
        }
    ]);
})(window.angular);


