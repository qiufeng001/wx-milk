"use strict";
/**
 * Utils
 * @module core/utils
 */
define(function (require, exports, module) {
    /**
     * 表达式定义
     * @type {{$now: values.$now, $periodStart: values.$periodStart, $periodEnd: values.$periodEnd, $periodEnd2: values.$periodEnd2}}
     */
    const values = {
        /**
         * 当前日期
         */
        $now:()=>new Date(),
        /**
         * 当月1号
         * @returns {Date}
         */
        $periodStart: ()=>{
            var now = new Date();
            return new Date(now.getFullYear(), now.getMonth(),1)
        },
        /**
         * 26号月结日
         * @returns {Date}
         */
        $periodEnd: ()=>{ //
            var now = new Date();
            return new Date(now.getFullYear(), now.getMonth(),26)
        },
        /**
         * 月末
         * @returns {Date}
         */
        $periodEnd2: ()=>{ //
            var now = new Date();
            now.setMonth(now.getMonth() + 1);
            now.setDate(0);
            return new Date(now.getFullYear() , now.getMonth(), now.getDate());
        }
    };

    /**
     * 设置组件值
     * @param ctrl
     * @param jq
     * @param val
     * @returns {*}
     */
    exports.setCtrlValue = function(ctrl,jq,val){
        if(!val)
            val = exports.parseValue(ctrl.options.defaultValue);

        if (val && $.fn.hasOwnProperty(ctrl.type)) {
            return $(jq)[ctrl.type]('setValue',val);
        } else {
            return $(jq).val(val);
        }
    };

    /**
     * 获取组件值
     * @param ctrl
     * @param jq
     * @returns {*}
     */
    exports.getCtrlValue = function(ctrl,jq){
        if ($.fn.hasOwnProperty(ctrl.type)) {
            return $(jq)[ctrl.type]('getValue');
        } else {
            return $(jq).val();
        }
    };


    /**
     * 根据表达式获取默认值
     * @param express
     */
    exports.parseValue = function(express){
        if( express && values.hasOwnProperty(express))
            return values[express]();
        return express;
    };
});