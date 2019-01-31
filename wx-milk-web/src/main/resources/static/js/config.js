"use strict";
define(function (require, exports, module) {
/*    exports.rootUrl = APP_SETTINGS.prs;
    exports.mdmUrl = APP_SETTINGS.mdm;
    exports.cardUrl = APP_SETTINGS.card;*/
    var datas = exports.data = {};
    datas.saleUnit = [{id: "1", name: "个"}, {id: "2", name: "件"}, {id: "3", name: "包"}, {id: "4", name: "套"}, {
        id: "5",
        name: "双"
    }];
    datas.sexs = [{id: "0", name: "男"}, {id: "1", name: "女"}, {id: "2", name: "其他"}];
    datas.memberLevels = [{id: "1", name: "普通会员"}, {id: "2", name: "VIP会员"}, {id: "3", name: "金卡VIP会员"}, {id: "4", name: "金卡会员"}];
    datas.ticketTypes=[{id: "1", name: "现金券",payNo:"P12"}, {id: "2", name: "活动券",payNo:"P14"}, {id: "3", name: "抵用券",payNo:"P11"}, {id: "4", name: "团购券",payNo:"P13"}];

    datas.quantityBoxInit = false;
    datas.orderType = [{id: "-1", name: "退货"}, {id: "1", name: "正常"}, {id: "2", name: "订金"}, {id: "3", name: "团购"}];
    datas.saleCodeStatus= [{id: "0", name: "待启用"}, {id: "1", name: "正常"}, {id: "2", name: "停销"}, {id: "3", name: "待清退"}, {id: "4", name: "已清退"}];
    datas.status = [{id: "1", name: "制单"},{id: "2", name: "已提交"},{id: "5", name: "支付中"},
                        {id: "10", name: "已挂单"},{id: "11", name: "已部分收款"},{id: "12", name: "已收全款"},
                        {id: "21", name: "部分退款成功"},{id: "22", name: "退款成功"},{id: "99", name: "已作废"}];
});