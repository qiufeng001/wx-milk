"use strict";
/**
 * 控件构造工厂模块
 * @module core/factory
 */
define(function (require, exports, module) {
    const utils = require('./utils');

    var standar = function (ctrl) {
        var required = ctrl.options && ctrl.options.required;
        return `<input id="${ctrl.name}"   name="${ctrl.name}"
                type="${ctrl.type}" class="${ctrl.class}" data-options="required:${required}">`;

    };

    var easyui = function (ctrl) {
        var required = ctrl.options && ctrl.options.required;
        return `<input id="${ctrl.name}" name="${ctrl.name}" x-name="${ctrl.name}"
                type="text" class="easyui-${ctrl.type} ${ctrl.class || ''}"
                data-options="required:${required||'false'}">`;
    };

    var controls = {
        "password": standar,
        "checkbox": standar,
        "radio": standar
    };

    $.each($.parser.plugins, (i, p)=> {
        controls[p] = easyui;
    });
    /**
     * CLASS 控件构造工厂
     */
    class Factory {
        /**
         *
         * @param {View} view 视图对象
         * @see View
         */
        constructor(view) {
            this.view = view;
        }

        /**
         * 控件定义是否存在指定属性
         * @param {String} name 属性名称
         * @returns {boolean} 是否存在
         */
        exist(name) {
            return controls.hasOwnProperty(name);
        };

        /**
         * 根据控件定义构造控件（按照jquery plugin方式定义的组件)
         * @param {Object} ctrl 控件定义
         * @returns {*}
         */
        build(ctrl) {
            var $e = null;
            if ($.isFunction(ctrl.type))
                $e = ctrl.type.call(this.view, ctrl);
            else if (this.exist(ctrl.type))
                $e = controls[ctrl.type](ctrl);
            else {
                if ($.fn.hasOwnProperty(ctrl.type))
                    $e = easyui(ctrl);
            }
            ctrl.$e = $e;
            return $e;
        };

        /**
         *
         * @param {Jquery} jq 控件所在的父容器
         * @param {Array} ctrls 组件定义
         */
        parse(jq, ctrls) {
            ctrls.map(c=> {
                var item = $(jq).find("input[name='" + c.name + "']");
                if ($.isFunction(item[c.type])) {
                    item[c.type](c.options);
                    if (c.type == "textbox") {
                        var length = c.options.maxLength || 80;
                        item.textbox('textbox').attr('width', length);
                    }
                }
                utils.setCtrlValue(c, item);
            });
        }
    }

    /** @alias module:core/factory */
    module.exports = Factory;

});