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
    var mod = ng.module('artworkModule', ['ngCrud', 'ui.router']);

    mod.constant('artworkModel', {
        name: 'artwork',
        displayName: 'Artwork',
        url: 'artworks',
        fields: {            
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            },
            image: {
                displayName: 'Image',
                type: 'Image',
                required: true
            },
            price: {
                displayName: 'Price',
                type: 'Long',
                required: true
            }        
        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/artwork/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('artwork', {
                url: '/artworks?page&limit',
                abstract: true,
                parent: 'artistDetail',
                views: {
                     artistChieldView: {
                        templateUrl: basePath + 'artwork.tpl.html',
                        controller: 'artworkCtrl'
                    }
                },
                resolve: {
                    model: 'artworkModel',
                    artworks: ['artist', '$stateParams', 'model', function (artist, $params, model) {
                            return artist.getList(model.url, $params);
                        }]                
                }
            });
            $sp.state('artworkList', {
                url: '/list',
                parent: 'artwork',
                views: {
                    artworkView: {
                        templateUrl: basePath + 'list/artwork.list.tpl.html',
                        controller: 'artworkListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('artworkNew', {
                url: '/new',
                parent: 'artwork',
                views: {
                    artworkView: {
                        templateUrl: basePath + 'new/artwork.new.tpl.html',
                        controller: 'artworkNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('artworkInstance', {
                url: '/{artworkId:int}',
                abstract: true,
                parent: 'artwork',
                views: {
                    artworkView: {
                        template: '<div ui-view="artworkInstanceView"></div>'
                    }
                },
                resolve: {
                    artwork: ['artworks', '$stateParams', function (artworks, $params) {
                            return artworks.get($params.artworkId);
                        }]
                }
            });
            $sp.state('artworkDetail', {
                url: '/details',
                parent: 'artworkInstance',
                views: {
                    artworkInstanceView: {
                        templateUrl: baseInstancePath + 'detail/artwork.detail.tpl.html',
                        controller: 'artworkDetailCtrl'
                    }
                }
            });
            $sp.state('artworkEdit', {
                url: '/edit',
                sticky: true,
                parent: 'artworkInstance',
                views: {
                    artworkInstanceView: {
                        templateUrl: baseInstancePath + 'edit/artwork.edit.tpl.html',
                        controller: 'artworkEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('artworkDelete', {
                url: '/delete',
                parent: 'artworkInstance',
                views: {
                    artworkInstanceView: {
                        templateUrl: baseInstancePath + 'delete/artwork.delete.tpl.html',
                        controller: 'artworkDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('artworkCategory', {
                url: '/category',
                parent: 'artworkDetail',
                abstract: true,
                views: {
                    artworkChieldView: {
                        template: '<div ui-view="artworkCategoryView"></div>'
                    }
                },
                resolve: {
                    category: ['artwork', function (artwork) {
                            return artwork.getList('category');
                        }],
                    model: 'categoryModel'
                }
            });
            $sp.state('artworkCategoryList', {
                url: '/list',
                parent: 'artworkCategory',
                views: {
                    artworkCategoryView: {
                        templateUrl: baseInstancePath + 'category/list/artwork.category.list.tpl.html',
                        controller: 'artworkCategoryListCtrl'
                    }
                }
            });
            $sp.state('artworkCategoryEdit', {
                url: '/edit',
                parent: 'artworkCategory',
                views: {
                    artworkCategoryView: {
                        templateUrl: baseInstancePath + 'category/edit/artwork.category.edit.tpl.html',
                        controller: 'artworkCategoryEditCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    pool: ['Restangular', 'model', function (r, model) {
                            return r.all(model.url).getList();
                        }]
                }
            });
            $sp.state('artworkGallery', {
                url: '/artworkGallery',
                views: {
                    mainView: {
                       templateUrl: basePath + 'list/artwork.gallery.tpl.html',
                       controller: 'artworkListCtrl',
                       controllerAs: 'ctrl'    
                   }
                },
                resolve: {
                    model: 'artworkModel',
                    artworks: ['Restangular', 'model', '$stateParams', function (r, model, $params) {
                            return r.all(model.url).getList($params);
                        }]                
                }
            });
            $sp.state('artworkClientDetail', {
                url: '/detalle/{artistId:int}/{artworkId:int}',
                views: {
                    mainView: {
                        templateUrl: baseInstancePath + 'client-detail/client-detail.tpl.html',
                        controller: 'artworkClientDetailCtrl',
                        controllerAs: 'ctrl' 
                    }
                },
                resolve: {
                    model: 'artworkModel',
                    artwork: ['Restangular', 'model', '$stateParams', function (r, model, $params) {
                            return r.one('artists/'+$params.artistId+'/'+model.url+'/',$params.artworkId).get();
                        }]
                }
            });
	}]);
})(window.angular);
