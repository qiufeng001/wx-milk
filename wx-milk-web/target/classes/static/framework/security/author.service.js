"use strict";
define(function(require, exports, module){
   let Service = require('core/service').Service;

    class AuthorService extends Service{
        constructor(url){
            super(url)
        }

        /**
         * 获取用户的应用程序列表
         * @returns {Promise.<T>|*}
         */
        getApplication(){
            return $.get(this.url + "/application/list").then(this.validate);
        }

        /**
         * 获取用户的模块列表
         * @param appNo
         * @returns {Promise.<T>|*}
         */
        getModule(appNo){
            return $.get(this.url + "/module/list",{appNo}).then(this.validate);
        }

        /**
         * 获取用户对应的模块操作权限
         * @param moduleNo
         * @returns {Promise.<T>|*}
         */
        getModuleOperation(moduleNo){
            return $.get(this.url + "/operation/list",{moduleNo}).then(this.validate);
        }
    }

    module.exports = new AuthorService("/admin/personal");
});