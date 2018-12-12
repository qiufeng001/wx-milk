"use strict";
/**
 * 视图渲染器
 * @module core/renderer
 */
define(function (require, exports, module) {
    const Factory = require("./factory");

    /** Class 渲染器 */
    class Renderer {
        /**
         *
         * @param {View} view 视图
         * @see View
         */
        constructor(view) {
            this.factory = new Factory(view);
        }

        /**
         * 按照控件定义渲染控件
         * @param {Control} ctrls 组件列表
         * @returns {*}
         */
        formatControls(ctrls) {
            var ary = $.map(ctrls, function (item, i) {
                return item;
            });
            return ary;
        }

        /**
         * 获取组件呈现的HTML内容
         * @param {Integer} colcount  每列控件数量
         * @param {Array} ctrls  控件定义
         * @returns {string} 控件的html
         */
        getControlsHtml(colcount, ctrls) {
            var self = this;            
            var index = 0;
            var rows = Math.ceil(ctrls.length / count);
            var count = Math.max(1, colcount);
            var width = Math.round(100 / (count * 2));
            var col='';
            var html = '<div class="controls-panel search-tb"><input type="hidden" name="id" id="id" ><table border="0" style="width:100%;"><colgroup>';            
            for (var i = 1; i <= count; i++) {
                col += `<col class="col-${i}-1" width="90"><col class="col-${i}-2" width="150">`;
            }
            html += `${col}</colgroup><tbody>`;
            var table=`</tr></tbody></table><div class="other"><table border="0" style="width:100%;"><colgroup>${col}</colgroup><tbody>`;

            //var ctrlObj = ctrls.groupBy('group');
            var ctrlObj = {};
            var groups = [];
            for(var key in ctrlObj){
                groups.push(key);
            }
            if(groups.length > 1){
                var groupHtml = `<div class="controls-panel search-tb"><input type="hidden" name="id" id="id" >`;
                groups.each(function(name){
                    var panelHtml = `<div class="easyui-panel" data-options="title:'${name}',fieldset:true,cls:'mt10'">`;
                    panelHtml += `<table border="0" style="width:100%;"><colgroup>${col}</colgroup><tbody>`;
                    var i = 0;
                    var group = ctrlObj[name];
                    while (i < group.length) {
                        var c = self.getCol(count,group,i);
                        panelHtml += c[0];
                        i = c[1];
                    };
                    panelHtml += `</tbody></table></div>`;
                    groupHtml += panelHtml;
                });
                groupHtml += `</div>`;
                return groupHtml;
            }else{
                while (index < ctrls.length) {
                    var a = this.getCol(count,ctrls,index);
                    html += a[0];
                    index = a[1];
                };
                return html;
            }

        }
        /**
         * 渲染每一行
         * @param {Integer} count  每列控件数量
         */
        getCol(count,ctrls,index){
        	var self = this;
        	var html='<tr>'
        	var c = 0;
            while (c < count) {
                if (index >= ctrls.length) {
                    html += `<td></td><td></td>`;
                } else {
                	var ctrl = ctrls[index];
                    var colspan = Math.max(1, ctrl.colspan || 0);
                    if ((count - c ) < colspan) {
                        html += `<td colspan="${count - c }"  ></td>`;
                        //rows += 1;
                        break;
                    }
                    if(ctrl.options && ctrl.options.hidden){
	                    html += "<td style='display:none;'>";
	                    if (ctrl.options && ctrl.options.required)
	                        html += '<span class="ui-color-red">*</span>';	
	                    html += `<label>${ctrl.label}：</label></td><td style='display:none;' colspan="${colspan * 2 - 1}"  >${self.factory.build(ctrl)}</td>`;
	                    c--;
                    }else{
                    	html += "<td>";
	                    if (ctrl.options && ctrl.options.required)
	                        html += '<span class="ui-color-red">*</span>';	
	                    html += `<label>${ctrl.label}：</label></td><td colspan="${colspan * 2 - 1}"  >${self.factory.build(ctrl)}</td>`;
                    }
                    c += colspan - 1;
                    index+=1;
                }
                c++;
            }
        	html += "</tr>";
            return [html,index];
        }

        /**
         * 渲染工具栏
         * @param {Jquery} contianer 容器元素
         * @param {View} view
         * @see View
         */
        renderToolbar(contianer, options) {

        };

        /**
         * 渲染查询框
         * @param {Jquery} contianer 父容器
         * @param {View} view 视图
         * @param {Integer} colcount 每行控件数量
         */
        renderControls(contianer, controls, colcount) {

            var form = $(`<form></form>`).appendTo(contianer);

            var ctrls = this.formatControls(controls);

            var html = this.getControlsHtml(colcount, ctrls);

            if (html) {
                var el = $(html);
                form.append(el);
                this.factory.parse(el, controls);
                contianer.find(".easyui-panel").panel();
                //$.parser.parse(form);
            }
            return form;
        };

    }
    /** @alias module:core/renderer */
    module.exports = Renderer;
});