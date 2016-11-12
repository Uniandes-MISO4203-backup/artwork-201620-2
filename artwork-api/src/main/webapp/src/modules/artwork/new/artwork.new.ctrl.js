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

    var mod = ng.module("artworkModule");

    mod.controller("artworkNewCtrl", ["$scope", "$state", "artworks",
        function ($scope, $state, artworks) {
            $scope.currentRecord = {otherImages:[{}],awards:[{}],places:[{}]};
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.artworkForm.$valid) {
                            artworks.post($scope.currentRecord).then(function (rc) {
                                $state.go('artworkDetail', {artworkId: rc.id}, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('artworkList');
                    }
                }
            };
            $scope.addOtherImage = function() {
                $scope.currentRecord.otherImages.push({url:''});
            };
            $scope.removeOtherImage = function(index){
                $scope.currentRecord.otherImages.splice(index,1);
            };
           $scope.addAwards = function() {
                $scope.currentRecord.awards.push({url:''});
            };
            $scope.removeAwards = function(index){
                $scope.currentRecord.awards.splice(index, 1);
            };
            $scope.addPlaces = function() {
                $scope.currentRecord.places.push({url:''});
            };
            $scope.removePlaces = function(index){
                $scope.currentRecord.places.splice(index, 1);
            };
        }]);
})(window.angular);
