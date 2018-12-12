"use strict";
define(function (require, exports, module) {
    let authorService = require('security/author.service');

    let view = require('./index.view');

    function barClick(e){
        var appNo = $(e.target).attr('value');
        authorService.getModule(appNo)
            .then(data=>view.renderSubBar(data));

    }

    function init() {
        return authorService.getApplication()
            .then(apps=>{
                var appbar = view.renderApplicatonBar(apps);
                $('a',appbar).click(barClick);
            }) ;
    }

    function complete() {
        $('#loading').remove();
    }

    init().then(complete);
});