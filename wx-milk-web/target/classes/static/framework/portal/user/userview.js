"use strict";
define(function (require, exports, module) {
    let UI = require('core/ui');


    class MainView extends UI.ListView {

        constructor() {
            super("main");
        }

        /**
         *
         * @returns {{id: string, data: *[]}}
         */
        getToolbars() {
            return {
                id: 'toolbar',
                data: [
                    {
                        id: "btn-search",
                        iconCls: 'icon-search',
                        text: '查询',
                        value: 1,
                    },
                    {
                        id: "btn-remove",
                        iconCls: 'icon-empty',
                        text: '清空',
                        value: 2
                    }
                ]
            }
        }

        getSearcherControls() {
            return {
                parent: 'searcher',
                controls: [
                    {
                        "label": "公&nbsp;&nbsp;&nbsp;&nbsp;司",
                        "type": "combobox",
                        "name": "companyNo",
                        "options": {
                            "required": true,
                            "inputWidth": 200,
                            data: [{id: 1, name: "百丽深圳"}],
                            valueField: 'id',
                            textField: 'name'
                        },
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺",
                        "type": "combobox",
                        "name": "shopNo",
                        "options": {valueField: 'id', textField: 'name', data: [{id: 1, name: "百丽深圳茂业"}]},
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "业务日期",
                        "type": "datebox",
                        "name": "saleStartDate",
                        "options": {"maxDate": "saleEndDate", "required": true},
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "&nbsp;&nbsp;— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
                        "type": "datebox",
                        "name": "saleEndDate",
                        "options": {"minDate": "saleStartDate", "required": true},
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "业务类型",
                        "type": "combobox",
                        "name": "businessType",
                        "options": {"required": false, "multiple": true},
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "品牌",
                        "type": "combobox",
                        "name": "brandNo",
                        "class": "ipt",
                        "options": {"inputWidth": 200},
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "商品编号",
                        "type": "combobox",
                        "name": "itemNo",
                        "options": {},
                        "colspan": 1,
                        "rowspan": 1
                    },
                    {
                        "label": "经营城市",
                        "type": "combobox",
                        "name": "organNo",
                        "options": {},
                        "colspan": 1,
                        "rowspan": 1
                    }]
            }
        }

        getGridOptions() {
            return [{
                id: 'grid',
                title: "结算公司",
                height: "670",
                loadMsg: '请稍等,正在加载...',
                iconCls: 'icon-ok',
                pageSize: "20",
                pageList: [20, 50, 100, 200],
                checkOnSelect: false,
                pagination: true,
                fitColumns: false,
                singleSelect: false,
                rownumbers: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                emptyMsg: "暂无数据",
                columns: [[
                    {field: 'id', title: 'ID', width: 80, hidden: true},
                    {field: 'companyNo', title: '公司编码', width: 80},
                    {field: 'name', title: '公司名称', width: 180},
                    {field: 'statusStr', title: '状态', width: 50},
                    {field: 'bankName', title: '开户银行', width: 120},
                    {field: 'bankAccountName', title: '银行账户名', width: 100},
                    {field: 'zoneName', title: '大区', width: 120},
                    {field: 'organTypeName', title: '本部', width: 120},
                    {field: 'bankAccount', title: '银行账号', width: 140},
                    {field: 'taxRegistryNo', title: '税务登记号', width: 140},
                    {field: 'taxLevelStr', title: '纳税级别', width: 80},
                    {field: 'legalPerson', title: '法人代表', width: 80},
                    {field: 'identityCard', title: '营业证号/身份证号', width: 140},
                    {field: 'contactName', title: '联系人', width: 80},
                    {field: 'tel', title: '联系人电话', width: 100},
                    {field: 'fax', title: '传真', width: 100},
                    {field: 'email', title: '电子邮件', width: 100},
                    {field: 'createUser', title: '建档人', width: 60},
                    {field: 'createTime', sortField: 'create_time', title: '建档时间', width: 125, sortable: true},
                    {field: 'updateUser', title: '修改人', width: 60},
                    {field: 'updateTime', sortField: 'update_time', title: '修改时间', width: 125, sortable: true},
                    {field: 'remark', title: '备注', width: 125}
                ]]
            },
                {
                    id: 'grid2',
                    title: "结算公司2",
                    height: "670",
                    loadMsg: '请稍等,正在加载...',
                    iconCls: 'icon-ok',
                    pageSize: "20",
                    pageList: [20, 50, 100, 200],
                    checkOnSelect: false,
                    pagination: true,
                    fitColumns: false,
                    singleSelect: false,
                    rownumbers: true,
                    enableHeaderContextMenu: true,
                    enableHeaderClickMenu: true,
                    emptyMsg: "暂无数据",
                    columns: [[
                        {field: 'id', title: 'ID', width: 80, hidden: true},
                        {field: 'companyNo', title: '公司编码', width: 80},
                        {field: 'name', title: '公司名称', width: 180},
                        {field: 'statusStr', title: '状态', width: 50},
                        {field: 'bankName', title: '开户银行', width: 120},
                        {field: 'bankAccountName', title: '银行账户名', width: 100},
                        {field: 'zoneName', title: '大区', width: 120},
                        {field: 'organTypeName', title: '本部', width: 120},
                        {field: 'bankAccount', title: '银行账号', width: 140},
                        {field: 'taxRegistryNo', title: '税务登记号', width: 140},
                        {field: 'taxLevelStr', title: '纳税级别', width: 80},
                        {field: 'legalPerson', title: '法人代表', width: 80},
                        {field: 'identityCard', title: '营业证号/身份证号', width: 140},
                        {field: 'contactName', title: '联系人', width: 80},
                        {field: 'tel', title: '联系人电话', width: 100},
                        {field: 'fax', title: '传真', width: 100},
                        {field: 'email', title: '电子邮件', width: 100},
                        {field: 'createUser', title: '建档人', width: 60},
                        {field: 'createTime', sortField: 'create_time', title: '建档时间', width: 125, sortable: true},
                        {field: 'updateUser', title: '修改人', width: 60},
                        {field: 'updateTime', sortField: 'update_time', title: '修改时间', width: 125, sortable: true},
                        {field: 'remark', title: '备注', width: 125}
                    ]]
                }];
        }
    }
    class Page extends UI.Page {
        constructor() {
            super('1010001',$('#mainPanel'));
            this.views = [
                new MainView()
            ]
        }
    }

    var page = new Page();

    page.render();
});