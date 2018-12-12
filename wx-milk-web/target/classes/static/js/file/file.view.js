"use strict";
define(function (require, exports, module) {

    $(function () {
        InitMainTable();
    });

    var $table;
    //初始化bootstrap-table的内容
    function InitMainTable() {
        //记录页面bootstrap-table全局变量$table，方便应用
        var queryUrl = '/file/list';
        $table = $('#table-panel').bootstrapTable({
            url: queryUrl,                      //请求后台的URL（*）
            method: 'get',
            data_type: 'json',
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            showRefresh: true,                  //是否显示刷新按钮
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 10,                     //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            strictSearch: true,
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            //得到查询的参数
            queryParams: function (params) {
                //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                var temp = {
                    rows: params.limit,                         //页面大小
                    page: (params.offset / params.limit) + 1,   //页码
                    sort: params.sort,      //排序列名
                    sortOrder: params.order //排位命令（desc，asc）
                };

                temp.paramsMap = getParamsMap();
                return temp;
            },
            columns: [{
                checkbox: true,
                visible: true                  //是否显示复选框
            }, {
                field: 'id',
                title: '文件id',
                align: 'center',
                visible: false
            }, {
                field: 'fsType',
                title: '功能类型',
                align: 'center',
                sortable: true
            }, {
                field: 'templateType',
                title: '模板类型',
                align: 'center',
                sortable: true
            }, {
                field: 'fileName',
                title: '文件名称',
                align: 'center',
                sortable: true
            },{
                field: 'suffix',
                title: '文件后缀名',
                align: 'center',
                sortable: true
            }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                width: 350,
                sortable: true,
                events: 'operateEvents',
                formatter: operateFormatter
            }],
            onLoadSuccess: function () {
            },
            onLoadError: function () {
                showTips("数据加载失败！");
            },
            onDblClickRow: function (row, $element) {
                var id = row.ID;
                EditViewById(id, 'view');
            },
        });
    };

    function getParamsMap() {
        var paramsMap = {};
        paramsMap.name = $("#name").val();
        paramsMap.fName = $("#fName").val();

        return paramsMap;
    }

    function reset() {
        $("#name").val("");
        $("#fName").val("");
    }

    function search(text) {
        $table.bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
    }

    // 操作列表
    function operateFormatter(value, row, index) {
        return [
            '<button type="button" id="btn-delete" class="btn-delete btn btn-danger  btn-sm" style="margin-right:15px;">删除</button>'
        ].join('');
    }

    // 操作事件
    window.operateEvents = {
        // 单个删除
        'click .btn-delete': function (e, value, row, index) {
            var id = row.id;
            var msg = "您真的确定要删除吗？\n\n请确认！";
            if (confirm(msg) == true) {
                $.ajax({
                    url: '/file/delete/' + id,
                    type: 'POST',
                    async: 'false',
                    data: id,
                    success: function (data) {
                        if(data > 0) {
                            search();
                        }
                    }
                });
            } else {
                return false;
            }
        }
    }

})