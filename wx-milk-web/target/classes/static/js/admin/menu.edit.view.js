"use strict";
define(function (require, exports, module) {

    $(function () {
        validate();
        initPMenu();
        save();
    });

    function initPMenu() {
        $(".selectpicker").selectpicker({
            noneSelectedText: '请选择'
        });
        window.onload = function () {
            $('.selectpicker').selectpicker('val', '');
            $('.selectpicker').selectpicker('refresh');
        }
        setTimeout(function () {
            $.ajax({
                url: '/menu/selectPid',
                type: 'POST',
                async: 'false',
                success: function (data) {
                    var $select = $("#slpk")
                    var $lengtt = $select[0].childNodes.length;
                    if ($lengtt == 0) {
                        for (var i = 0; i < data.length; i++) {
                            var $id = $("#id").val();
                            if($id != null && $id == data[i].id) {
                                $select.append("<option value='" + data[i].id + "' selected='selected'>" + data[i].name + "</option>");
                            }else {
                                $select.append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
                            }
                        }
                        $('.selectpicker').selectpicker('val', '');
                        $('.selectpicker').selectpicker('refresh');
                    }
                }
            });
        }, 500);

    }

    // 新增
    function save() {
        $("#save").click(function () {
            $.ajax({
                url: '/menu/createOrUpdate',
                data: $('#menuForm').serialize(),
                type: 'POST',
                async: 'false',
                success: function (d) {
                    if (d.id != null || d.id != '') {
                        $('#addOrUpdate-menuModel').modal('hide');
                        window.location.href = "/menu/";
                    }
                }
            });
        });
    }
    
    function validate() {
        $("#name").mouseout(function () {
            var oldName = $("#oldName").val();
            var menuId = $("#id").val();
            var name = $("#name").val();
            var params = {};
            var paramsMap = {};
            paramsMap.name = name;
            params.paramsMap = paramsMap;
            if(oldName != name) {
                $.ajax({
                    url: '/menu/validate',
                    data: params,
                    type: 'POST',
                    async: 'false',
                    success: function (d) {
                        if(d > 0) {
                            $("#msg").text("菜单重复!");
                        }else {
                            $("#msg").text("");
                        }
                    }
                });
            }
        });
    }
})