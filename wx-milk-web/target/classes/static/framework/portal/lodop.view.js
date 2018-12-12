"use strict";
define(function (require, exports, module) {
    let print = require('core/print');

    function CheckIsInstall() {
        try {
            var LODOP = print.getLodop();
            if (LODOP.VERSION) {
                if (LODOP.CVERSION)
                    alert("当前有C-Lodop云打印可用!\n C-Lodop版本:" + LODOP.CVERSION + "(内含Lodop" + LODOP.VERSION + ")");
                else
                    alert("本机已成功安装了Lodop控件！\n 版本号:" + LODOP.VERSION);

            }
            ;
        } catch (err) {
        }
    };


    var LODOP; //声明为全局变量
    function myPrint() {
        CreatePrintPage();
        LODOP.PRINT();
    };
    function myPrintA() {
        CreatePrintPage();
        LODOP.PRINTA();
    };
    function myPreview() {
        CreatePrintPage();
        LODOP.PREVIEW();
    };
    function mySetup() {
        CreatePrintPage();
        LODOP.PRINT_SETUP();
    };
    function myDesign() {
        CreatePrintPage();
        LODOP.PRINT_DESIGN();
    };
    function myBlankDesign() {
        LODOP = getLodop();
        LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_空白练习");
        LODOP.PRINT_DESIGN();
    };
    function CreatePrintPage() {
        LODOP = getLodop();
        LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_名片");
        LODOP.ADD_PRINT_RECT(10, 55, 360, 220, 0, 1);
        LODOP.SET_PRINT_STYLE("FontSize", 11);
        LODOP.ADD_PRINT_TEXT(20, 180, 100, 25, "郭德强");
        LODOP.SET_PRINT_STYLEA(2, "FontName", "隶书");
        LODOP.SET_PRINT_STYLEA(2, "FontSize", 15);
        LODOP.ADD_PRINT_TEXT(53, 187, 75, 20, "科学家");
        LODOP.ADD_PRINT_TEXT(100, 131, 272, 20, "地址：中国北京社会科学院附近东大街西胡同");
        LODOP.ADD_PRINT_TEXT(138, 132, 166, 20, "电话：010-88811888");
    };


    //LODOP = print.getLodop();
    //LODOP.PRINT_INIT("");
    //LODOP.ADD_PRINT_HTM(10, 55, "100%", "100%", document.getElementById("textarea01").value);


    $('#btnPrintView').bind('click', myPreview);
    $('#btn').bind('click', CheckIsInstall);
});