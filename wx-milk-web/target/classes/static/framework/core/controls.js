"use strict";
/**
 * 工具栏定义
 *  @module core/control
 */
define(function (require,exports,module) {

    /** order 定义为字符串，便于继承组件中调整按钮顺序 **/
     var bar = {
        create: {id: "btn-create",iconCls: 'icon icon-plus',text: '新增',value: 2,order: "1"},
        edit: {id: "btn-edit", iconCls: 'icon icon-pencil', text: '编辑', value: 3, order: "2"},
        save: {id: "btn-save", iconCls: 'icon icon-floppy-disk', text: '保存', value: 3, order: "3"},
        remove: {id: "btn-delete", iconCls: 'icon icon-bin', text: '删除', value: 4, order: "4"},
        clear: {id: "btn-clear", iconCls: 'icon icon-spinner11', text: '重置', value: 1, order: "11"},
        search: {id: "btn-search", iconCls: 'icon icon-search', text: '查询', value: 1, order: "2"},
        exports: {id: "btn-export", iconCls: 'icon icon-upload2', text: '导出', value: 5, order: "6"},
        imports: {id: "btn-import", iconCls: 'icon icon-download2', text: '导入', value: 6, order: "5"},
        list: {id: "btn-list", iconCls: 'icon icon-price-tag', text: '浏览', value: 1, order: "0"},
        prev:{id: "btn-prev", iconCls: 'icon icon-arrow-left', text: '上一单', value: 1, order: "5"},
        next:{id: "btn-next", iconCls: 'icon icon-arrow-right', text: '下一单', value: 1, order: "6"}       
    };

    var bars = [bar.create, bar.remove];

    /**
     * 数组合并
     * @param ary1
     * @param ary2
     * @returns {Array.<T>|*}
     */
    function merge(ary1, ary2) {
        $.merge(ary1, ary2);
        var val = ary1.sort((a, b)=>a.order > b.order);
        return val;
    }

    /** @alias module:core/control */
    exports.toolbars = {
        /** 基础工具栏 */
        bars:bar,
        /** 通用列表界面工具条 */
        listBar: merge([bar.search,bar.clear,bar.exports], bars), //列表工具条
        /** 带编辑工具条 */
        editListBar: merge([bar.search, bar.edit], bars),
        /** 明细编辑工具条 */
        detailBar: merge([bar.list,bar.save], bars),
        /** 单据列表工具条 */
        billListBar:merge([bar.save,bar.prev,bar.next], bars)
    }


});