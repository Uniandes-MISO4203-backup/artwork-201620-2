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

    var mod = ng.module("nationalityModule");

    mod.controller("nationalityDetailCtrl", ['$scope', "$state", "nationality",'$rootScope',
        function ($scope, $state, nationality,$rootScope) {
            $scope.currentRecord = nationality;
            var roles = $rootScope.roles;
            
            $scope.refreshPage = function(){
                $state.reload();
            }
            
            $scope.goToList = function() {
                $state.go('nationalityList');
            }
            
            $scope.newRecord = function() {
                $state.go('nationalityNew');
            }
            
            $scope.editRecord = function() {
                $state.go('nationalityEdit');
            }
            
            $scope.deleteRecord = function() {
                $state.go('nationalityDelete');
            }
            
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('nationalityNew');
                    },
                    show: function () {
                        return (roles.indexOf("admin") !== -1);
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function () {
                        $state.go('nationalityEdit');
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function () {
                        $state.go('nationalityDelete');
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
                        $state.go('nationalityList');
                    }
                }
            };
        }]);
})(window.angular);
