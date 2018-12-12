"use strict";
$(function () {
    var optionsMain = {
        url: rootPath + "/sys/cache/provider/list",
        height: "500px",
        onDblClickRow: function (index, row) {
            // var url = rootPath + "/system/cache/provider/keys";
            var group = row.group;
            var name = row.name;            
            $('#gridDetail').datagrid('load', {group, name});
        },
        columns: [[{
            field: 'group',
            title: '分组',
            width: 100
        }, {
            field: 'name',
            title: '名称',
            width: 100
        },
            {
                field: 'value2',
                title: 'Action',
                align: 'right',
                formatter: function (value, row, index) {
                    return `<button class="btn2" index="${index}" action="clear">清空</button>
                    		<button class="btn1" index="${index}"  action="load">重加载</button>`;
                },
                width: 120
            }]]
    };

    $('#gridMain').datagrid(optionsMain);

    var optionsDetail = {
        url: rootPath + "/sys/cache/provider/keys",
        height: "500px",
        // loadFilter: function (data) {
        // var txt = $('#btnSearch').textbox('getValue');
        // if (!txt)
        // return data;
        // return data.filter(d=> {
        // return d.no.indexOf(txt) >= 0;
        // });
        // },
        columns: [[{
            field: 'no',
            title: 'Key',
            align: "left",
            width: 250
        }, {
            field: 'value',
            title: 'Value',
            width: 100
        }, {
            field: 'value2',
            title: 'Action',
            formatter: function (value, row, index) {
                return `<button class="btn" index="${index}" action="get">查看</button>
                        <button class="btn" index="${index}" action="del">删除</button>`;
            },
            width: 80
        }]]
    };
    $('#mainPanel').click(function (e) {
        var el = $(e.toElement);
        var index = el.attr("index");
        if (typeof index == 'undefined')
            return;
        var rows = $('#gridMain').datagrid('getData').rows;
        var row = rows[index];
        var action = el.attr('action');
        
        var url = `${rootPath}/sys/cache/provider/${action}`;
        $.post(url, {
            group: row.group,
            name: row.name
        }).then(d => {
            $.messager.alert(d ? '完成' : '错误');
        }).fail(e=> {
            $.messager.alert("错误:" + e);
        });
    });

    $('#btnSearch').textbox('button').bind('click', (e)=> {
        var $grid = $('#gridDetail');
        var data = $grid.datagrid('getData');
        var txt = $('#btnSearch').textbox('getValue');
        if(txt){
        	var ary  = data.rows.filter(c=>c.no.startsWith(txt));
        	$grid.datagrid('loadData',ary);
        }
        else{
        	$grid.datagrid('reload');
        }
    });

    $('#gridDetail').datagrid(optionsDetail);

    $('#detailPanel').click(function (e) {
        var el = $(e.toElement);
        var index = el.attr("index");
        if (typeof index == 'undefined')
            return;
        var action = el.attr("action");
        var params = $('#gridDetail').datagrid('options').queryParams;

        var rows = $('#gridDetail').datagrid('getData').rows;
        var row = rows[index];
        var url = `${rootPath}/sys/cache/provider/detail/${action}`;
        if (action == "del") {
            $.post(url, {
                group: params.group,
                name: params.name,
                key: row.no
            }).then(d => {
                $.messager.alert(d ? '完成' : '错误');
            }).fail(e=> {
                $.messager.alert("错误:" + e);
            });
        }
        else if (action == "get") {
        	window.open(url + `?group=${params.group}&name=${params.name}&key=${row.no}`); 
        }
    });
});