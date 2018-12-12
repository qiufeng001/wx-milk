"use strict";
define(function (require, exports, module) {
        function buildMenu(data, options, accordionOptions) {
            var $obj = $("#leftMenu");
            if ($obj.length != 0) {
                var state = $.data($obj[0], 'accordion');
                if (state) {
                    var opts = state.options;
                    $('#left>div').remove();
                    $obj = $('<div id="leftMenu"></div>').appendTo($('#left'));
                }
            } else {
                $('#left>div').remove();
                $obj = $('<div id="leftMenu"></div>').appendTo($('#left'));
            }
            /* Modify End */
            var acd = "";
            try {
                $.each(data, function (idx, item) {
                    var id = "tree_panel_" + idx;
                    var title = item.text;
                    //新增Accordion
                    acd = '<div title="' + title + '"><ul id="' + id + '"></ul></div>';
                    $obj.append(acd);
                    $("#" + id).tree({
                        lines: true,
                        data: item.children,
                        onClick: function (node) {
                            var target = node.target;
                            var $target = $(target);
                            //父节点
                            if (!$(this).tree("isLeaf", target)) {
                                $(this).tree('toggle', target);
                                return;
                            }
                            //不显示菜单
                            if (options && !options.click.call(this))
                                return;
                            var title = node.text
                                , iconCls = node.iconCls
                                , url = node.attributes.url;
                            //如果是完整的URL地址则表示需要跳转到其它系统
                            var isGoto = true;
                            var initialize = !!$target.attr("initialize");
                            var regExp_url = /^([http|https]+):\/\/(.*)\.belle\.net\.cn\/(.*?)\/.*/;
                            if (regExp_url.test(url) && !initialize) {
                                $.easyui.loading({
                                    msg: "正在为你跳转，请稍候。。。"
                                });
                                var matches = regExp_url.exec(url);
                                var jumpTo = matches[1] + "://" + matches[2] + ".belle.net.cn/" + matches[3] + "/sso_to_index?outFlag=true";
                                $.ajax({
                                    type: 'get',
                                    url: jumpTo,
                                    async: false,
                                    dataType: 'html',
                                    success: function () {
                                        $target.attr("initialize", "true");
                                    },
                                    error: function (xhr) {
                                        alert("错误码：" + xhr.status + "</br>" + "错误信息：" + xhr.statusText, 2);
                                        isGoto = false;
                                    }
                                });
                                $.easyui.loaded();
                            }
                            if (!isGoto)
                                return;
                            //新增TAB
                            if(url.indexOf('?')>0)
                                url += "&_md=" + item.id;
                            else
                                url += "?_md=" + item.id;

                            addTab({
                                title: title,
                                href: url,
                                icon: iconCls
                            });
                            //注册全屏菜单
                            //var fullScreenArr = ["销售订单", "销售订单(触屏版)", "退换货(触屏版)", "待提货订单(触屏版)", "内购订单(触屏版)", "退换货", "团购订单", "跨店订单"];
                            //if ($.array.contains(fullScreenArr, title))
                            //    top.$("#tabToolsFullScreen").trigger('click');
                        }
                    });
                });
                //渲染accordion
                var accOpts = accordionOptions ? accordionOptions : {
                    fit: true,
                    animate: false
                }
                $obj.accordion(accOpts);
                $obj.accordion('select', 0);
            } catch (e) {
            }
        }

        exports.renderApplicatonBar = function (data, callback) {
            var wrapper = $("#subSystem");
            wrapper.empty();
            var links = "";
            $.each(data, function (i, app) {
                links += `<a href="#" class="" value="${app.appNo}" data-toggle="subSystem">${app.name}</a>`;
            });
            wrapper.append(links);
            return wrapper;
        };

        exports.renderSubBar = function (menus) {
            buildMenu(menus);
        };

        exports.renderToolbar = function(data){

        }
    }
);
