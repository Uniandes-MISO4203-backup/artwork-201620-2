(function (ng) {
    var mod = ng.module('roleModule', ['ngCrud']);
    mod.controller('roleCtrl', ['$rootScope', 'Restangular','$state', function ($rootScope, Restangular) {

        $rootScope.auth = function () {
                Restangular.all("users").customGET('me').then(function (response) {
                    if (response == null) {
                        $rootScope.category = false;
                        $rootScope.artist = false;
                        $rootScope.client = false;
                        $rootScope.product = false;
                        $rootScope.nationality = false;
                        $rootScope.message = false;
                        $rootScope.item = false;
                        $rootScope.authenticated = false;
                        $rootScope.profile = false;
                        $rootScope.profileArtist = false;
                        $rootScope.shoppingcart = false;
                        $rootScope.checkout = false;
                    } else {
                        $rootScope.authenticated = true;
                        var roles = $rootScope.roles = response.roles;
                        if (roles.indexOf("client") !== -1) {
                            $rootScope.category = false;
                            $rootScope.artist = false;
                            $rootScope.client = false;
                            $rootScope.profile = true;
                            $rootScope.product = false;
                            $rootScope.nationality = false;
                            $rootScope.message = true;
                            $rootScope.item = true;
                            $rootScope.profileArtist = false;
                            $rootScope.shoppingcart = true;
                            $rootScope.checkout = true;                            
                        }
                        if (roles.indexOf("artist") !== -1) {
                            $rootScope.category = false;
                            $rootScope.artist = false;
                            $rootScope.client = false;
                            $rootScope.product = false;
                            $rootScope.nationality = false;
                            $rootScope.profile = false;
                            $rootScope.profileArtist = true;
                        }
                        if (roles.indexOf("admin") !== -1) {
                            $rootScope.category = true;
                            $rootScope.artist = true;
                            $rootScope.client = true;
                            $rootScope.product = true;
                            $rootScope.nationality = true;
                            $rootScope.message = true;
                            $rootScope.profile = false;
                            $rootScope.profileArtist = false;
                        }
                    }
                });
            };
        $rootScope.auth();
        $rootScope.$on('logged-in', function () {
            $rootScope.auth();
        });

        $rootScope.$on('logged-out', function () {
            $rootScope.auth();
        });
    }]);
})(window.angular);




