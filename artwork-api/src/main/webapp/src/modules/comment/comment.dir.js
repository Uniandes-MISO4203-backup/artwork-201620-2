(function (ng) {
    var mod = ng.module('commentModule', ['ngCrud', 'ui.router']);
    mod.directive('comment', function() {
        return {
            restrict: 'E',
            templateUrl: 'src/modules/comment/comment.tpl.html',
            scope: {
                artwork: "="
            },
            controller: controller
        };
    });

    function controller($scope, Restangular) {
        $scope.comment = null;
        $scope.empty = true;
        $scope.comments = [];
        Restangular.all('comments').customGET($scope.artwork.id).then(function (data) {
            $scope.comments=data;
            if(data.length!==0) {
                $scope.empty = false;
            }
        });
        $scope.postComment = function() {
            var comment = $scope.comment;
            if(comment) {
                comment.name = comment.message;
                comment.artwork = $scope.artwork;
                console.log("comment",comment);
                Restangular.all('comments').post(comment).then(function(data) {
                    $scope.empty = false;
                    $scope.comments.push(comment);
                }); 
            }  
        };
    }
    
})(window.angular);
