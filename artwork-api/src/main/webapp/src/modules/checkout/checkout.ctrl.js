(function (ng) {
    var mod = ng.module('checkoutModule');
    
    mod.controller('checkoutCtrl', function($scope, $rootScope, $modal, Restangular) {
        var checkOut = function(){
            $modal.open({
                templateUrl: 'src/modules/checkout/checkoutview.tpl.html',
                controller: modalController,
                scope: $scope,
                resolve: {
                    creditcards: ['Restangular', function (r) {
                        return r.all('clients/creditCard').getList();
                    }]
                }
            });
        };

        $scope.checkOut = checkOut;
        var alertBox = function(){
            $modal.open({
                templateUrl: 'src/modules/checkout/checkoutalert.tpl.html',
                controller: modalAlertController,
                scope: $scope
            });
        };

        $scope.alertBox = alertBox;        
    });
    
    modalController.$inject = ['$scope', '$modalInstance', '$state', 'creditcards', 'Restangular'];
    function modalController($scope, $modalInstance, $state, creditcards, r){
        
        $scope.checkout = {
            shippingAddress: '',
            creditCard : {
                id: ''
            }
        };
        
        $scope.checkOut = function(){
            r.all('checkout').post($scope.checkout).then(function() { 
                $modalInstance.close($scope.alertBox());
            });
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.creditcards = creditcards;
    };    
    
    modalAlertController.$inject = ['$scope', '$modalInstance', '$state'];
    function modalAlertController($scope, $modalInstance, $state){
        $scope.cancel = function () {
            $modalInstance.close($state.go('checkoutList'));
        };
    };        
    
})(window.angular);