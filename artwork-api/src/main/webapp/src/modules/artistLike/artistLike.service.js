/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function (ng) {
    var mod = ng.module('artistLikeModule', []);

    mod.factory('artistLikeService', ['Restangular',
        function (Restangular) {
           return {a:1};             
        }]);

})(window.angular);
