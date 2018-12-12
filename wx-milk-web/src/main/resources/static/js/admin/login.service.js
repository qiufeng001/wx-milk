"use strict";
define(function (require, exports, module) {
    var config = require('../config');
    var Service = require('core/service').Service;

    class LoginService extends Service{
        constructor(){
            super(config.rootUrl);
        }

        signIn(account,password,validatecode){
            return $.post("signin",{account,password,validatecode});
        }
    }

    module.exports = LoginService;
});