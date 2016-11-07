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

    var mod = ng.module("clientModule");

    mod.controller("clientDetailCtrl", ['$scope', "$state", "client",'$rootScope',
        function ($scope, $state, client,$rootScope) {
            $scope.currentRecord = client;
            var roles = $rootScope.roles;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('clientNew');
                    },
                    show: function () {
                        return (roles.indexOf("admin") !== -1);
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function () {
                        $state.go('clientEdit');
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function () {
                        $state.go('clientDelete');
                    },
                    show: function () {
                        return (roles.indexOf("admin") !== -1);
                    }
                },
                refresh: {
                    displayName: 'Refresh',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                },
                list: {
                    displayName: 'List',
                    icon: 'th-list',
                    fn: function () {
                        $state.go('clientList');
                    },
                    show: function () {
                        return (roles.indexOf("admin") !== -1);
                    }
                },
                back: {
                    displayName: 'Go Back',
                    icon: 'arrow-left',
                    fn: function () {
                        $state.go('clientList');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                },
                wishList: {
                    displayName: 'Wish List',
                    icon: 'link',
                    fn: function () {
                        $state.go('wishList');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                },
                shoppingCart: {
                    displayName: 'Shopping Cart',
                    icon: 'shopping-cart',
                    fn: function () {
                        $state.go('shoppingCart');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                },                
                creditCardList: {
                    displayName: 'Credit Card List',
                    icon: 'usd',
                    fn: function () {
                        $state.go('creditCardList');
                    }
                },
                messageList: {
                    displayName: 'Messages',
                    icon: 'envelope',
                    fn: function () {
                        $state.go('messageList');
                    },
                    show: function () {
                        return (roles.indexOf("client") !== -1);
                    }
                }
            };
        }]);
})(window.angular);
