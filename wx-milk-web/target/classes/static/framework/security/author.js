"use strict";
define(function (require, exports, module) {
    let authorService = require('framework/security/author.service');
    let controls = require('framework/core/controls');

    let operations = window.operations;
    // operations 来自服务器端定义，服务器端通过拦截用户请求的controller，
    // 从数据库中查询controller对应的用户模块权限,输出到模板中
    let isAdmin = operations.indexOf('-1') > -1;
    let bars = controls.toolbars.bars;

    /**
     * 根据工具栏定义获取详细信息
     * @param item
     * @returns {*}
     */
    function getBar(item) {
        if ($.isPlainObject(item))
            return item;
        var bar = null;
        for (var f in bars) {
            var b = bars[f];
            if (item == f || b.text == item || b.value == item) {
                bar = b;
                break;
            }
        }
        if (bar == null)
            throw new Error('错误工具条编码');

        return $.extend({}, bar);
    }

    /**
     * 根据用户权限，设置工具条状态
     * @param item
     * @returns {*}
     */
    exports.filterOperation = function (toolbars) {
        return toolbars.map(c=> {
            var bar = getBar(c);
            bar.disabled = !(isAdmin || operations.any(v=>v == bar.value || bar.value == 1));
            return bar;
        });
    }
});