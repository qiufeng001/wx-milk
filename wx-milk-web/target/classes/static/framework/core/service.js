"use strict";
/**
 * 服务基础类
 * @module core/service
 */
define(function (require, exports, module) {
    /**
     * 是否是简单对象
     * @param data
     * @returns {boolean}
     */
    function isObject(data) {
        if (!$.isPlainObject(data))
            return false;
        for (var p in data) {
            var d = data[p];
        }
    }

    /**
     * 代理对象处理函数
     * @type {{get: handle.get, set: handle.set}}
     */
    let handle = {
        get: function (target, name, proxy) {
            if (name == 'proxy')
                return this;
            if (name in target)
                return Reflect.get(target, name, proxy);
            let url = target.url;

            return (function (url, name) {
                var options = {};
                name = name.toLocaleLowerCase();
                options.async = true;
                if (name.endsWith('Sync')) {//是否为同步调用
                    options.async = false;
                    options.url = url + "/" + name.substr(0, name.length - 4);
                }
                else {
                    options.url = url + "/" + name;
                }

                //查询方法默认使用GET
                if (name.startsWith('get') || name.startsWith('find') ||
                    name.startsWith('select') || name.startsWith('query')
                    || name.startsWith('list') || name.startsWith('check')) {
                    options.type = "GET";
                }
                else {
                    //修改使用POST调用
                    options.type = "POST";
                }
                return function () {
                    if (name.startsWith('batch')) //批处理方法是用特殊处理，将数据作为字符串提交
                        options.data = {"datas": JSON.stringify(arguments[0])};
                    else
                        options.data = arguments[0];
                    return $.ajax(options);
                }

            })(url, name);
        },
        set: function (target, name, value, proxy) {
            return Reflect.set(target, name, value, proxy);
        }
    };


    /**
     * Class 服务基类
     */
    class Service {
        /**
         *
         * @param {String} url 对应的服务地址
         */
        constructor(url) {
            this._url = url
        }

        /**
         * 获取服务地址，只读
         * @returns {String|*}
         */
        get url() {
            return this._url;
        }

        /**
         * 创建代理对象
         * @returns {Proxy}
         */
        proxy() {
            return new Proxy(this, handle);
        }

        /**
         *根据ID查询对象
         * @param id
         * @returns {Promise.<T>|*}
         */
        getById(id) {
            var self = this;
            return $.get(this.url + "/" + id);
        }

        /**
         * 根据查询条件查询
         * @param {Object} params 查询条件
         * @returns {Promise.<T>|*}
         */
        findByParam(params) {
            var self = this;
            return $.get(this.url + "/get", params);
        }

        /**
         * 根据唯一索引获取
         * @param {String} id ID
         * @param {String} no 编号
         */
        findByUnique(id, no) {

        }

        /**
         * 新增
         * @param {Object} params 实体
         *  @returns {Promise.<T>|*}
         */
        create(params) {
            var self = this;
            return $.post(this.url + "/create", params);
        }

        /**
         * 保存
         * @param {Object} params 实体
         * @returns {Promise.<T>|*}
         */
        update(params) {
            var self = this;
            return $.post(this.url + "/update", params);
        }

        /**
         * 删除 {Object} params 实体
         * @param params
         * @returns {Promise.<T>|*}
         */
        delete(params) {
            var self = this;
            if (params instanceof Array) {
                params = "'" + params.join("','") + "'";
            }
            return $.post(this.url + "/delete", {ids: params});
        }

        /**
         * 根据ID删除
         * @param {String} id 实体id
         * @returns {Promise.<T>|*}
         */
        deleteById(id) {
            var self = this;
            return $.post(this.url + "/delete/" + id);
        }

    }

    class BillService extends Service {
        constructor(url) {
            super(url);
        }

        getBill(billNo){
            return $.get(this.url + "/bill", {billNo});
        }

        getDetails(billNo) {
            return $.get(this.url + "/detail/query", {billNo});
        }

        confirm(bill) {
            return $.post(this.url + "/confirm", bill);
        }

        verify(bill) {
            return $.post(this.url + "/verify", bill);
        }

        cancel(bill) {
            return $.post(this.url + "/cancel", bill);
        }

        /**
         * 下一单
         */
        next(billNo,params){
            return $.get(this.url + "/next/" + billNo, params);
        }

        /**
         * 上一单
         */
        pre(billNo,params){
            return $.get(this.url + "/pre/" + billNo, params);
        }

        /**
         * 新增单据明细
         * @param billNo
         * @param details
         */
        createDetails(billNo, details) {
            return $.post(this.url + "/detail/create",
                {billNo, details: JSON.stringify(details)});
        }

        updateDetails(details) {
            return $.post(this.url + "/detail/upate", {details: JSON.stringify(details)});
        }

        deleteDetails(details) {
            if (typeof details == "String")
                return $.ajax({
                    url: this.url + "/detail/" + details,
                    type: "DELETE"
                });
            else
                return $.post(this.url + "/detail/delete", {details: JSON.stringify(details)});
        }

        clearDetails(billNo) {
            return $.post(this.url + "/detail/clear", {billNo});
        }

        saveDetails(billNo, datas) {
            return $.post(this.url + "/detail/save", {datas: JSON.stringify(datas)});
        }
    }

    /** @alias module:core/service */
    module.exports = {Service, BillService};
});