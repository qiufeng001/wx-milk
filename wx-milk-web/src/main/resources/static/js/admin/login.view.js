"use strict";
define(function (require, exports, module) {
    var controls = require('core/controls');
    var Service = require('./login.service');
    var config = require('../config');

    var service = new Service();

    class LoginView {
        constructor() {
            //获取cookie并用；号切割成cookie数组
            var arrCookie = document.cookie.split("; ");
            var flag;
            //遍历cookie数组，处理每个cookie对
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                //找到名称为userId的cookie，并返回它的值
                if ("_flag" == arr[0]) {
                    flag = arr[1];
                    break;
                }
            }
            if (flag == "true") {
                $('.validatecode').show();
            }
        }

        error(msg) {
            window.refresh($('#imgcode')[0]);
            $("#error").text(msg);
        }

        signIn() {
            var account = $('#account').val();
            var password = $('#password').val();
            var validateCode = $('#validateCode').val();
            if (!account) {
                $('#errMsg').text('用户名不能为空!')
                return;
            }
            if (!password) {
                $('#errMsg').text('密码不能为空!')
                return;
            }
            service.signIn(account, password, validateCode).then((status) => {
                if (status == 1) {
                    window.location.href = "/home/index";
                    return;
                }
                if (status == -1) {
                    $('.validatecode').show();
                    $('#errMsg').text('错误密码或者用户名!')
                }
                else if (status == -2) {
                    $('#errMsg').text('验证码错误!')
                } else if (status == 0) {
                    $('#errMsg').text('无效用户!')
                }
                else if (status == -6) {
                    $('#errMsg').text('用户在当前系统不允许登陆!')
                }
            });
        }
    }

    var view = new LoginView();
    $(function () {
        /*    var $box = $('.login .login_text');
         $('#account',$box).focus();
         var accountBoxHotkeys = null;
         accountBoxHotkeys = new Hotkeys();
         accountBoxHotkeys.enable();
         accountBoxHotkeys.bind($box,true);*/
        $('#btnLogin').bind('click', e => view.signIn());
    })
});