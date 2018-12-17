"use strict";
define(function (require, exports, module) {

    $(function () {
        InitMainTable();
        initDictions();
        saveUserRole();
    });

    var $table;
    //初始化bootstrap-table的内容
    function InitMainTable() {
        //记录页面bootstrap-table全局变量$table，方便应用
        var queryUrl = '/user/list';
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
                title: '用户id',
                align: 'center',
                visible: false
            }, {
                field: 'name',
                title: '用户名',
                align: 'center',
                sortable: true
            }, {
                field: 'account',
                title: '账号',
                align: 'center',
                sortable: true
            }, {
                field: 'status',
                title: '状态',
                align: 'center',
                sortable: true,
                formatter:function (value, row, index) {
                    var status = value;
                    status = status == 1 ? '有效' : '停用';
                    return status;
                }
            },{
                field: 'createUser',
                title: '创建人',
                align: 'center',
                sortable: true
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                sortable: true
            }, {
                field: 'updateUser',
                title: '修改人',
                align: 'center',
                sortable: true
            }, {
                field: 'updateTime',
                title: '修改时间',
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

    function initDictions() {
        $.ajax({
            url: '/menu/selectDictionsByAccount',
            type: 'GET',
            success: function (data) {
                var $div = $("#dictions-role");
                for (var i = 0; i < data.length; i++) {
                    var _code = data[i].dictionsCode;
                    var _name = data[i].name;
                    if (_code == 'reset') {
                        var ipt = document.createElement("input");
                        ipt.type = "button";
                        //传入点击按钮的value值到新的按钮
                        ipt.value = _name;
                        //传入点击按钮的id到新的按钮(传入是id+1防止重复)
                        ipt.id = 'btn-reset';
                        ipt.className = "btn btn-default";
                        //当方法有参数时，用onclick = 方法名(参数)时就有错了，需要在方法名前面加function()
                        ipt.onclick = function () {
                            reset();
                        };
                        $div.append(ipt);
                    } else if (_code == 'select') {
                        var ipt = document.createElement("input");
                        ipt.type = "button";
                        ipt.value = _name;
                        ipt.id = 'btn-reset';
                        ipt.className = "btn btn-primary";
                        ipt.onclick = function () {
                            search();
                        };
                        $div.append(ipt);
                    } else if (_code == 'add') {
                        var ipt = document.createElement("input");
                        ipt.type = "button";
                        ipt.value = _name;
                        ipt.id = 'btn-reset';
                        ipt.className = "btn btn-success";
                        ipt.onclick = function () {
                            addModel();
                        };
                        $div.append(ipt);
                    } else if (_code == 'update') {
                        var ipt = document.createElement("input");
                        ipt.type = "button";
                        ipt.value = _name;
                        ipt.id = 'btn-reset';
                        ipt.className = "btn btn-warning";
                        ipt.onclick = function () {
                            update();
                        };
                        $div.append(ipt);
                    } else if (_code == 'delete') {
                        var ipt = document.createElement("input");
                        ipt.type = "button";
                        ipt.value = _name;
                        ipt.id = 'btn-reset';
                        ipt.className = "btn btn-danger";
                        ipt.onclick = function () {
                            delete(0);
                        };
                        $div.append(ipt);
                    }
                }
            }
        });
    }

    // 操作列表
    function operateFormatter(value, row, index) {
        return [
            '<button type="button" id="btn-delete" class="btn-delete btn btn-danger  btn-sm" style="margin-right:15px;">删除</button>',
            '<button type="button" id="btn-distribution" class="btn-distribution btn btn-success  btn-sm" style="margin-right:15px;">分配权限</button>',
            '<button type="button" id="btn-update" class="btn-update btn btn-warning  btn-sm" style="margin-right:15px;">修改</button>',
            '<button type="button" id="btn-start" class="btn-start btn btn-success  btn-sm" style="margin-right:15px;">启用</button>',
            '<button type="button" id="btn-stop" class="btn-stop btn btn-primary  btn-sm" style="margin-right:15px;">停用</button>'
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
                    url: '/user/delete/' + id,
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
        },

        // 分配权限
        'click .btn-distribution': function (e, value, row, index) {
            $("#user-roleModel").modal('show');
            $.ajax({
                url: '/role/list',
                type: 'POST',
                async: 'false',
                success: function (data) {
                    var roles = data.rows;
                    $("#userName").text(row.name);
                    $("#account").val(row.account);
                    var $select = $("#roleSlpk")
                    var $lengtt = $select[0].childNodes.length;
                    if ($lengtt == 0) {
                        for (var i = 0; i < roles.length; i++) {
                            $select.append("<option value='" + roles[i].roleNo + "'>" + roles[i].name + "</option>");
                        }
                        $('.selectpickerRole').selectpicker('val', '');
                        $('.selectpickerRole').selectpicker('refresh');
                    }

                    var $roles = $('#roleSlpk option:selected');
                    if ($roles.length > 0) {
                        $roles.length = 0;
                    }
                }
            });
        },


        // 修改
        'click .btn-update': function (e, value, row, index) {
            window.location.href = '/user`/edit?id=' + row.id;
        },

        // 启用
        'click .btn-start': function (e, value, row, index) {
            alert(row.dno);
        },

        // 停用
        'click .btn-stop': function (e, value, row, index) {
            alert(row.dno);
        }
    }

    // 新增或者修改模态框弹出
    function addModel(args) {
        window.location.href = '/user/edit?id=';
    }

    function saveUserRole() {
        $("#saveRole").click(function () {
            var $roles = $('#roleSlpk option:selected');
            var roleArr = new Array();
            for (var i = 0; i < $roles.length; i++) {
                roleArr.push($roles[i].value);
            }
            var params = {};
            params.roles = roleArr;
            params.account = $("#account").val();

            $.ajax({
                url: '/userRole/saveUserRole',
                data: params,
                type: 'POST',
                async: 'false',
                dataType: 'json',
                success: function (d) {
                    if (d.success) {
                        $("#user-roleModel").modal('hide');
                        window.location.reload(true);
                    }
                }
            });
        });
    }
})