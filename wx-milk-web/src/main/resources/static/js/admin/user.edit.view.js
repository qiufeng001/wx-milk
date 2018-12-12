"use strict";
define(function (require, exports, module) {

    $(function () {
        /*validateAccount();
        validateName();*/
        save();
        //upload();
    });

    // 新增
    function save() {
        $("#save").click(function () {
            var account = $("#account").val();
            var name = $("#name").val();
            if (account == null || account == '') {
                alert("账号不能为空！");
                return;
            }
            if (name == null || name == '') {
                alert("用户名不能为空！");
                return;
            }

            $.ajax({
                url: '/user/createOrUpdate',
                data: $('#menuForm').serialize(),
                type: 'POST',
                async: 'false',
                success: function (d) {
                    if (d.success) {
                        window.location.href = "/user/";
                    }
                    if (d.message == 'repeat') {
                        alert("保存失败, 用户名或者账号重复！");
                        return;
                    }
                }
            });
        });
    }

    /*
     * 验证账号
     * 链接参数 type，0：验证账号，1：验证用户名
     */
    function validateAccount() {
        $("#name").mouseout(function () {
            var oldAccount = $("#oldAccunt").val();
            var menuId = $("#id").val();
            var account = $("#account").val();
            var params = {};
            var paramsMap = {};
            paramsMap.validateAccount = account;
            params.paramsMap = paramsMap;
            if (oldAccount != account) {
                $.ajax({
                    url: '/user/validate',
                    data: params,
                    type: 'POST',
                    async: 'false',
                    success: function (d) {
                        if (d > 0) {
                            $("#msg").text("账号重复!");
                        } else {
                            $("#msg").text("");
                        }
                    }
                });
            }
        });
    }

    /**
     * 验证用户名
     * 链接参数 type，0：验证账号，1：验证用户名
     */

    function validateName() {
        $("#name").mouseout(function () {
            var oldName = $("#oldName").val();
            var menuId = $("#id").val();
            var name = $("#name").val();
            var params = {};
            var paramsMap = {};
            paramsMap.validateName = name;
            params.paramsMap = paramsMap;
            if (oldName != name) {
                $.ajax({
                    url: '/user/validate',
                    data: params,
                    type: 'POST',
                    async: 'false',
                    success: function (d) {
                        if (d > 0) {
                            $("#msg").text("账号重复!");
                        } else {
                            $("#msg").text("");
                        }
                    }
                });
            }
        });
    }

    /**
     * 上传头像像
     *
     */
    /*function upload() {
        var fileArea = $("#uploadFile").text();
        if(fileArea != null && fileArea != '') {
            $.ajax({
                url: '/file/upload',
                data: $('#menuForm').serialize(),
                type: 'POST',
                async: 'false',
                success: function (data) {
                    console.log(data);
                }
            });
        }
    }*/
})