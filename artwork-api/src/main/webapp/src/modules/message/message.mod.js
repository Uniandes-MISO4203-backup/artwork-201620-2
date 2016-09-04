(function (ng) {
    
    var mod = ng.module('messageModule', ['ngCrud', 'ui.router']);

    mod.constant('messageModel', {
        name: 'message',
        displayName: 'Message',
	url: 'messages',
        fields: {
            subject : { 
                name: 'subject',
                displayName: 'Subject',
                type: 'String',
                required: true
            }, 
            body : {
                name: 'body',
                displayName: 'Body',
                type: 'String',
                required: true
            },
            sentDate : {
                name: 'sentDate',
                displayName: 'Sent Date',
                type: 'Date',
                required: false
            }            
        },
        editFields: {
            subject : { 
                name: 'subject',
                displayName: 'Subject',
                type: 'String',
                required: true
            }, 
            body : {
                name: 'body',
                displayName: 'Body',
                type: 'String',
                required: true
            }        
        }        
    });
    
    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/message/';
            var baseInstancePath = basePath + 'instance/';
            
            $sp.state('message', {
                url: '/messages?page&limit',
                abstract: true,
                views: {
                    mainView: {
                        templateUrl: basePath + 'message.tpl.html',
                        controller: 'messageCtrl'
                    }
                },
                resolve: {
                    model: 'messageModel',
                    messages: ['Restangular', 'model', '$stateParams', function (r, model, $params) {
                            return r.all(model.url).getList($params);
                        }]
                }
            });
            $sp.state('messageList', {
                url: '/list',
                parent: 'message',
                views: {
                    messageView: {
                        templateUrl: basePath + 'list/message.list.tpl.html',
                        controller: 'messageListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('messageNew', {
                url: '/new',
                parent: 'message',
                views: {
                    messageView: {
                        templateUrl: basePath + 'new/message.new.tpl.html',
                        controller: 'messageNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('messageInstance', {
                url: '/{messageId:int}',
                abstract: true,
                parent: 'message',
                views: {
                    messageView: {
                        template: '<div ui-view="messageInstanceView"></div>'
                    }
                },
                resolve: {
                    message: ['messages', '$stateParams', function (messages, $params) {
                            return messages.get($params.messageId);
                        }]
                }
            });            
            $sp.state('messageDetail', {
                url: '/details',
                parent: 'messageInstance',
                views: {
                    messageInstanceView: {
                        templateUrl: baseInstancePath + 'detail/message.detail.tpl.html',
                        controller: 'messageDetailCtrl'
                    }
                }
            });
            $sp.state('messageEdit', {
                url: '/edit',
                sticky: true,
                parent: 'messageInstance',
                views: {
                    messageInstanceView: {
                        templateUrl: baseInstancePath + 'edit/message.edit.tpl.html',
                        controller: 'messageEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('messageDelete', {
                url: '/delete',
                parent: 'messageInstance',
                views: {
                    messageInstanceView: {
                        templateUrl: baseInstancePath + 'delete/message.delete.tpl.html',
                        controller: 'messageDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);    