"use strict";
/**
 * Created by hong.l on 2015/6/4.
 */
define(function (require, exports, module) {
    var XLSX = require('./xlsx');
    var Excel = module.exports = {};
    /**
     * 导入
     **/
    Excel.Import = function (options) {
        var self = this;
        self.utils = Excel.utils;
        self.id = Date.parse(new Date());
        this.options = $.extend({
            zipFlag: false,         //上传数据至服务器时压缩标识
            valuefield: 'qty',      //尺码很排时的对应的值列
            H2V: false,             //是否尺码横排
            validate: true,         //是否验证尺码信息
            syncSku: true,         //是否需要获取SKU
            errorContinue: true,    //错误是否继续
            fields: {},             //字段映射列表
            excludefields: [],      //排除字段，row中带有该字段，转换为sku不带
            uniqueFields: null,      //唯一字段,
            downloadUrl:null,		//工程的上下文,用于下载模版			
            headLine:null 			//表体所在的行数,如果不传默认在第一行
        }, options);
        //导入上下文.
        self.context = null;

        /**
         *开始导入回调
         **/
        this.onBegin = function () {

        };
        /**
         * 导入表头回调
         * **/
        this.onLoadHeader = function (hdr) {

        };

        /**
         * 行验证
         * index 索引
         * row 行对象
         **/
        this.validate = function (index, row) {
            return true;
        };

        /**
         * 数据转换回调
         **/
        this.onConvert = function (count, index, row, skus) {

        };

        /**
         * 行更新
         *
         **/
        this.onUpdateRow = function (src, desc) {

        };

        /**
         * 从服务器获取执行状态回调
         *
         **/
        this.onGetSubmitStatus = function (data) {
            var index = data.index;
            var count = data.count;
            if (typeof index !== 'undefined' && typeof count !== 'undefined') {
                if (typeof data.msg !== 'undefined')
                    self.info(data.msg);
                self.processValue(index, count);
            }
        };

        /**
         * 压缩上传至服务器的数据
         * */
        this.zipJSON = function (params) {
            self.info("正在压缩提交的数据，请勿关闭当前窗口。");
            var def = $.Deferred();
            if (self.options.zipFlag) {
                if (typeof params.datas === "string") {//压缩字符串
                    $.extend(params, self.utils.zipJSONStr(params.datas));
                    if (params.zipFlag)
                        params.datas = "$";
                    def.resolve();
                } else if (Object.prototype.toString.call(params.datas) === "[object Array]") {//压缩数组
                    $.extend(params, {zipFlag: true, zipData: []});
                    var count = params.datas.length;
                    var index = 0;
                    //数组使用异步压缩，每压缩一个元素等100ms再压缩下一个元素避免浏览器假死
                    var task = function () {
                        while (index < count) {
                            var row = self.utils.zipJSONStr(params.datas[index]);
                            if (!row.zipFlag) {//若有其中一个元素压缩失败，则所有的都不压缩
                                params.zipFlag = false;
                                params.zipData = ["$"];
                                def.resolve();
                            }
                            params.zipData.push(row.zipData);
                            index += 1;
                            if (index >= count) {//遍历完后，清空原来的datas，强制返回
                                params.datas = ["$"];
                                def.resolve();
                            } else {
                                window.setTimeout(task, 100);
                            }
                        }
                    };
                    setTimeout(task, 50);
                } else {
                    def.resolve();
                }
            } else {
                def.resolve();
            }
            return def;
        };

        /**
         * 提交数据
         **/
        this.submit = function (url, params) {
            self.info("正在处理数据，请勿关闭当前窗口。");
            var task = null;
            var ticket = new Date().getTime();
            if (typeof self.options.mapper !== 'undefined') {
//                task = repository.fetchBatchSaveStatus(self.options.mapper, ticket, self.onGetSubmitStatus);
            }
            params.ticket = ticket;

            return self.zipJSON(params).then(function () {
                self.info("正在提交数据，请勿关闭当前窗口。");
                var promise = $.post(url, params)
                    .then(function (data) {
                        if (data.errorCode&&data.errorCode!='0000') {
                            self.info("导入错误:" + data.errorMessage);
                            $.messager.alert('错误', data.errorMessage);
                            return data;
                        }
                        self.info("导入完成");
                        $.messager.alert('导入', '导入完成！');
                        return data;
                    }).always(function () {
                        if (task)
                            task.stop();
                        task = null;
                        if (typeof self.timer !== 'undefined')
                            self.timer.stop();
                    });
                if (task)
                    task.start();
                return promise;
            });
        };

        this.bind = function (panel) {
            if (panel.addEventListener) {
                panel.addEventListener('dragenter', handleDragover, false);
                panel.addEventListener('dragover', handleDragover, false);
                panel.addEventListener('drop', handleDrop, false);
            }
            panel.find('input[type=file]').each(function (i, input) {
                input.addEventListener('change', handleFile, false);
            });
        };

        /**
         * 停止导入
         *
         **/
        this.stop = function () {
            var p = self.controls.panel;
            if (!self.stoped) {
                $.messager.confirm('导入', '确定要停止导入么?', function (r) {
                    if (r) {
                        self.stoped = true;
                        $('.excel-info', p).text("正在尝试停止导入...");
                    }
                });
            }
        };

        /**
         * 弹出面板
         **/
        this.showPanel = function () {
            if (!self.controls) {
                createPanel();
                var options = {
                    iconCls: 'icon-import',
                    modal: true,
                    closable: false,
                    minimizable: false,
                    maximizable: false,
                    resizable: false,
                    collapsible: false,
                    title: '导入'
                };
                self.controls.panel.window(options);
            } else {
                self.controls.panel.window('open');
            }
            self.info('');
            var p = self.controls.panel;
            $('.excel-ok', p).linkbutton('enable');
            $('.excel-cancel', p).linkbutton('enable');
            $('input[type=file]', p).val('');
            self.controls.prg.progressbar('setValue', 0);
        };

        /**
         * 设置提示内容
         * */
        this.info = function (msg) {
            $('.excel-info', self.controls.panel).text(msg);
        };

        /**
         * 控制台输出
         **/
        this.out = function (msg, level) {
            if (typeof level == 'undefined')
                level = "error";
            self.controls.out.append("<li class='" + level + "'>" + msg + "</li>");
        };

        /**
         * 关闭面板
         *
         **/
        this.close = function () {
            self.stoped = true;
            self.controls.panel.window('close');
        };


        var onLoading = function (count, index, row) {
            self.processValue(index, count);
            if ($.isFunction(self.onLoading) && row) {
                self.onLoading.call(self, count, index, row);
            }
        };

        /**
         * 进度条设置
         *
         **/
        this.processValue = function (index, count) {
            var value = parseInt((index / count) * 100);
            self.controls.prg.progressbar('setValue', value);
        };

        var temp = [];
        var onBegin = function (file) {
            temp = [];
            self.context = {info: {total: 0, ignore: 0, merge: 0, override: 0}};
            self.context.file = file;
            self.errors = null;
            self.time = 0;
            self.stoped = false;
            self.status = 1;
            self.controls.timer.text("00:00:00");
            self.timer = new Excel.utils.timer(self.controls.timer).start();
            var p = self.controls.panel;
            $('.excel-ok', p).linkbutton({text: '停止'});
            //$('.excel-ok', p).linkbutton('disable');
            $('.excel-cancel', p).linkbutton('disable');
            self.controls.out.empty();
            $('.excel-info', p).text("正在分析数据...");
            self.onBegin.call(this);
        };

        var onLoaded = function (result) {
            var p = self.controls.panel;
            $('.excel-ok', p).linkbutton({text: '导入'});
            $('.easyui-linkbutton', self.controls.panel).linkbutton('enable');
            if (result.error) {
                console.log(result.error);
                $.messager.alert('错误', '导入过程中出现错误！');
                if (typeof self.timer !== 'undefined')
                    self.timer.stop();
                clear();
                return;
            }
            if (self.rows) {
                $('.excel-info', self.controls.panel).text("共提取记录：" + self.rows.length + "条。");
                if (self.context.info.override > 0)
                    self.controls.out.append("<li class='info'>覆盖:" + self.context.info.override + "条记录</li>");
                if (self.context.info.ignore > 0)
                    self.controls.out.append("<li class='info'>忽略:" + self.context.info.ignore + "条记录</li>");
                if (self.context.info.merge > 0)
                    self.controls.out.append("<li class='info'>合并:" + self.context.info.merge + "条记录</li>");
                setTimeout(function () {
                    self.onLoaded(result);
                    self.timer.stop();
                    clear();
                }, 2000);
            }
        };

        var onConvert = function (count, index, row, skus) {
            if (row && $.isFunction(self.onConvert)) {
                self.onConvert(count, index, row, skus);
            }
            if (self.options.validate) {
                if (row)
                    temp.push(row);
                if ((temp.length % 200) == 0 || index >= count - 1) {
                    var datas = temp;
                    temp = [];
                    batchUpdateItemInfo(datas);
                }
            }
        };

        /**
         * 批量更新商品信息
         *
         **/
        function batchUpdateItemInfo(datas) {
            Excel.utils.updateItem.call(self, datas, function (desc, src, type, current) {
                if (type == "item") {
                    var brandNo = src.brandNo;
                    if (isNotBlank(desc.brandNo)) {
                        brandNo = desc.brandNo;
                    }
                    if (!authority.hasDataAccess('Brand', brandNo)) {
                        self.error(desc, "没有品牌" + brandNo + "的导入权限");
                        return false;
                    }
                    if (typeof  desc.sizeKind == 'undefined')
                        desc.sizeKind = src.sizeKind;
                    if (desc.sizeKind && desc.sizeKind != src.sizeKind) {
                        self.error(desc, desc.itemCode + ":尺码类型错误，应该为:" + src.sizeKind);
                        return false;
                    }
                    var msg = self.onUpdateRow(src, desc, type);
                    if (typeof msg == "string")
                        self.error(desc, msg);
                    return true;
                } else {
                    if (!desc.skuNo) {
                        self.error(current, desc.itemCode + ":没有尺码" + (desc.sizeNo || ""));
                        return false;
                    }
                    var msg = self.onUpdateRow(src, desc, type, current);
                    if (typeof msg == "string")
                        self.error(desc, msg);
                    return true;
                }
            });
        }

        /**
         * 构建面板
         **/
        function createPanel() {
            var panel = '<div><div class="excel-import" id="e-' + self.id + '" title="导入" '
                + '">'
                + '<div style="padding:10px">'
                + '<div>导入:</div>'
                + '<input type="file" class="inputfile" name="file2" data-options="prompt:\'选择导入文件...\',buttonText:\'浏览\'"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" style="width:480px"/>'
                + '</div>'
                + '<div class="progress">'
                + '<div style="height: 24px;"><span class="timer"></span><span class="excel-info"></span></div>'
                + '<div class="easyui-progressbar excel-p" style="width:480px;"></div>'
                + '</div><div class="label"><span>错误信息:</span></div>'
                + '<div class="excel-output" ><ul class="excel-out">' +
                '<li class="error">注意：Excel中列的名称不能修改必须和模板一致，表头尺码行必须在名称列上方。</li>' +
                '</ul></div>'
                + '<div class="excel-buttons" >'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-ok" style="margin: 2px">导入</a>'
                + '<a href="'+self.options.downloadUrl+'/resources/download/'+self.options.templateName+'" class="easyui-linkbutton excel-tmpl" download="" style="margin: 2px">下载模板</a>'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-cancel"  style="margin: 2px">关闭</a>'
                + '</div>'
                + '</div></div>';

            var p = $(panel).appendTo($('body'));
            var prg = $('.excel-p', p);
            $('.excel-cancel', p).bind('click', function () {
                self.controls.panel.window('close');
            });
            $('.excel-ok', p).bind('click', function () {
                if (!self.status || self.status == 0) {
                    var files = $('input[type=file]', self.controls.panel)[0].files;
                    if (files.length == 0) {
                        $.messager.alert('警告', '请选择一个导入文件!');
                        return;
                    }
                    handleFiles(files);
                }
                else
                    self.stop();
            });
//            $('.excel-tmpl', p).bind('click', function () {
//            	<a id="downEle" href="http://fas.belle.net.cn/fas/resources/download/专柜费用.xlsx" download="">file.js</a>
////            	window.location.href = self.options.downloadUrl + '/download?fileName=' + self.options.templateName;
//            });
            $.parser.parse(p);
            self.controls = {
                panel: $('#e-' + self.id),
                prg: prg,
                timer: $('.timer', p),
                out: $('.excel-out', p)
            }
        }

        /**
         * 处理导入文件
         **/
        function handleFiles(files) {
            var f = files[0];
            onBegin(f);
            self.def = $.Deferred();
            self.elapsed = 0;
            self.time = new Date().getTime();
            var reader = new FileReader();
            reader.onload = function (rd) {
                var data = rd.target.result;
                var wb = XLSX.read(data, {type: 'binary'});
                var name = wb.SheetNames[0];
                try {
                    parserSheet2Json(wb.Sheets[name]);
                }
                catch (e) {

                    self.info("导入文件中存在错误。");
                    self.error(null, e);
                    if (typeof self.timer != 'undefined')
                        self.timer.stop();
                    onLoaded({});
                    self.status = 0;
                    self.stoped = 0;
                    clear();
                    console.error(e);
                }

            };
            reader.readAsBinaryString(f);
            return self.def.promise();
        }

        function handleDrop(e) {
            e.stopPropagation();
            e.preventDefault();
            var files = e.dataTransfer.files;
            handleFiles(files).then(handleResult);
        }

        function handleDragover(e) {
            e.stopPropagation();
            e.preventDefault();
            e.dataTransfer.dropEffect = 'copy';
        }

        function handleFile(e) {
            var files = e.target.files;
            handleFiles(files);
        }

        function isChinese(temp) {
            return /[\u4e00-\u9fa5]/.test(temp);
        }

        function callback(result, state) {
            self.elapsed = Math.round(( new Date().getTime() - self.time  ) / 1000);
            result.errors = self.errors;
            if (state) {
                self.def.resolve(result);
                onLoaded(result);
            }
            else {
                self.def.fail(result);
                onLoaded(result);
                if (typeof self.timer !== 'undefined')
                    self.timer.stop();
            }

        }

        /**
         * Excel行转JS对象
         **/
        function parserSheet2Json(sheet) {
            var val, r, C;
            var hdr = self.hdr = [];
            var unique = {};
            if (sheet == null || sheet["!ref"] == null) {
                callback(null, true);
            }
            var range = sheet["!ref"];
            switch (typeof range) {
                case 'string':
                    r = XLSX.utils.safe_decode_range(range);
                    break;
                case 'number':
                    r = XLSX.utils.safe_decode_range(sheet["!ref"]);
                    r.s.r = range;
                    break;
                default:
                    r = range;
            }
            var header=!self.options.headLine?1:self.options.headLine;
            for (header ; header <= 100; header++) {
                var val = sheet["A" + header];
                if (val !== undefined)
                    break;
            }
            if (header == 100)
                throw "没有找到标题列";

            var h = self.h = {};
            h.s = {r: 1, c: 1};
            h.e = {r: header, c: 1};
            r.e.c = 0;
            hdr.size = {};
            for (var j = header; j <= h.e.r; ++j) {
                var sizeKind = null;
                for (var i = 0; i <= 200; ++i) {
                    var c = XLSX.utils.encode_col(i);
                    val = sheet[c + j];
                    if (val == undefined)
                        continue;
                    var vv = $.trim(XLSX.utils.format_cell(val));
                    if (!vv)
                        continue;
                    if (self.options.H2V) {
                        var chinese = isChinese(vv);
                        if (!chinese && h.s.c > 1)
                            h.e.c = Math.max(i, h.e.c);
                        if (!chinese && h.s.c == 1) {
                            h.e.c = h.s.c = i;
                        }

                        if (!chinese && h.s.c == i) {
                            sizeKind = hdr.size[vv] = {};
                        }
                        if (!chinese && i > h.s.c && sizeKind) {
                            for (var p in sizeKind) {
                                if (sizeKind[p] == vv)
                                    throw "重复的标题列" + vv;
                            }
                            sizeKind[i] = vv;
                        }
                    }

                    if (j == h.e.r) {
                        var field = vv.replace(/\*/ig, '');
                        var required = field.indexOf('*') >= 0;
                        var name = null;
                        for (var p in self.options.fields) {
                            if (p == field || p == vv || p.replace(/\*/ig, '') == field) {
                                name = self.options.fields[p];
                                required = p.indexOf("*") >= 0;
                                break;
                            }
                        }
                        var validate = isChinese(field);
                        if (!name && validate)
                            throw "导入Excel的列配置不符合规则。" + field + "列找不到对应信息。";

                        if (name)
                            hdr[i] = {name: name, required: required, field: field};
                        else
                            hdr[i] = {name: field, required: required, field: field};
                    }
                    r.e.c = Math.max(r.e.c, i);
                }
            }
            if (self.options.H2V) {
                for (var c = h.s.c; c <= h.e.c; c++) {
                    hdr[c] = {name: SIZE_PREFIX + (c), required: false};//尺码详情列required: false
                }
                hdr[h.s.c] = {name: "sizeKind", required: false};//尺码类型列required: false
            }
            //回调方法，交给外部处理头文件
            if ($.isFunction(self.onLoadHeader)) {
                self.onLoadHeader.call(this, hdr);
            }
            var rows = self.rows = [];
            var count = self.context.info.total = r.e.r - h.e.r + 1;
            var caller = {R: h.e.r + 1};
            var nop = function (self) {
                if (self.R >= r.e.r + 1) {
                    onLoading(count, self.R, null);
                    onConvert(count, self.R - h.e.r - 1, null, null);
                }
                self.R += 1;
            };
            var task = function () {
                if (this.R > r.e.r + 1) {
                    window.clearTimeout(self.interval);
                    callback({data: rows, header: hdr}, true);
                    return false;
                }
                while (this.R <= r.e.r + 1) {
                    var row = {__r__: this.R};
                    var empty = true;
                    for (C = 0; C <= r.e.c; C++) {
                        var f = hdr[C];
                        var c = XLSX.utils.encode_col(C);
                        var val = sheet[c + this.R];
                        if (val == undefined || val.t == undefined || val.v == undefined || !$.trim(val.v)) {
                            //若有值为空，则看当前行标题范围之内的列的值是否全为空，
                            //若全为空则不需要判断是否非空直接忽略当前行
                            var flag = false;
                            var RC;
                            for (RC = 0; RC <= r.e.c; RC++) {
                                var rc = XLSX.utils.encode_col(RC);
                                var rv = sheet[rc + this.R];
                                if (rv && rv.t && rv.v && $.trim(rv.v)) {
                                    flag = true;
                                    break;
                                }
                            }
                            //只有当前行标题范围之内的列的值有不为空的才需要判断非空必填
                            if (flag && f.required) {
                                self.error(row, (f.field ? f.field : f.name) + "不能为空.");
                            } else {
                                continue;
                            }
                        }
                        var v = $.trim(XLSX.utils.format_cell(val));
                        switch (val.t) {
                            case 'e':
                                continue;
                            case 's':
                                break;
                            case 'b':
                            case 'n':
                                break;
                            default:
                                throw 'unrecognized type ' + val.t;
                        }
                        empty = false;
                        if (v !== undefined) {
                            row[f.name] = v;
                        } else if (!v && f.required) {
                            self.error(row, f.field + "不能为空.");
                        }
                    }
                    if (empty) {//空行跳过
                        nop(this);
                        continue;
                    }

                    if (!loadRow(count, this.R, row))
                        return false;

                    if (!self.uniqueValidate(unique, row)) {
                        nop(this);
                        continue;
                    }
                    if (self.options.H2V) {
                        Excel.utils.H2V(row, self.hdr.size[row.sizeKind], self.options.excludefields, self.options.valuefield);
                    }
                    if (!row) {
                        if (!self.error(row, '错误的尺码'))
                            return false;
                    }
                    rows.push(row);
                    onConvert(count, this.R - h.e.r - 1, row, row.skus);
                    //rows.push(row);
                    if ((this.R % 100) == 0) {
                        this.R += 1;
                        break;
                    }
                    this.R += 1;
                }
                return true;
            };

            var timeout = 250;
            var fn = function () {
                try {
                    if (!self.stoped) {
                        if (task.call(caller))
                            setTimeout(fn, timeout);
                    } else {
                        $('.excel-info', self.controls.panel).text("已停止");
                        callback({data: null, header: null, error: "已中断"}, false);
                    }
                } catch (e) {
                    console.error(e);
                    callback({data: null, header: null, error: e + ""}, false);
                }
            };
            self.interval = setTimeout(fn, timeout);
        }


        /**
         * 错误提示
         * @param row 出错行
         * @param msg 错误信息
         * @returns {boolean}
         */
        this.error = function (row, msg) {
            if (self.errors == null)
                self.errors = {};
            if (!row) {
                row = {__r__: 0}
            }
            self.errors.hasError = true;
            self.errors[this.R] = {data: row, msg: msg};
            var txt = `【${row.__r__}】 ${msg}`;
            self.controls.out.append("<li class='error'>" + txt + "</li>");
            if (!self.options.errorContinue) {
                window.clearInterval(self.interval);
                callback({data: self.rows, header: self.hdr}, false);
                return false;
            }
            return true;
        };

        /**
         * 唯一性校验
         * @param unique 缓存对象
         * @param row 校验行
         * @returns {*}
         */
        this.uniqueValidate = function (unique, row) {
            if (!self.options.uniqueFields || self.options.uniqueFields.length == 0)
                return true;
            var fields = self.options.uniqueFields;
            var key = "";
            for (var f = 0; f < fields.length; f++) {
                if (f > 0)
                    key += ",";
                key += row[fields[f]];
            }
            var v = unique[key];
            if (typeof v == 'undefined') {
                unique[key] = row;
                return true;
            }
            var action = self.context.action;//
            if (typeof action == 'undefined') {
                action = parseInt(window.prompt(`【${row.__r__}】存在重复的数据，请输入处理方式:1 覆盖 2 累加 3 忽略`));
                while (isNaN(action) || action < 1 || action > 3) {
                    action = parseInt(window.prompt("输入错误，请重新输入处理方式:1 覆盖 2 累加 3 忽略"));
                }
                self.context.action = action;
            }
            return self.mergeRow(unique[key], row, action);

        };

        /**
         * 行合并
         * @param desc 目标行
         * @param src  源行
         * @param action 冲突解决方式
         * @returns {boolean}
         */
        this.mergeRow = function (desc, src, action) {
            //1 覆盖 2 累加 3 忽略
            if (action == 3) {
                self.context.info.ignore += 1;
                self.out(`【${src.__r__}】忽略`, 'info');
                return false;
            }
            else if (action == 1) {
                for (var p in src) {
                    if (p.indexOf("__") >= 0 || self.options.uniqueFields.indexOf(p) > -1)
                        continue;
                    desc[p] = src[p];
                }
                self.context.info.override += 1;
                self.out(`【${src.__r__}】覆盖【${desc.__r__}】`, 'info');
            }
            else if (action == 2 && self.options.H2V) {
                for (var p in src) {
                    var reg = new RegExp("^" + SIZE_PREFIX + "\\d{1,}$");
                    //正则匹配尺码列（尺码列列名一般 以SIZE_PREFIX开头 后面接数字且至少有一个数字）
                    //非尺码列则不计算，结束此轮循环
                    if (!reg.test(p) || p != 'allPrice') {//处理总金额allPrice没有累加的情况 （鉴定结果单）
                        continue;
                    }
//                    if (p.indexOf(SIZE_PREFIX) != 0)
//                        continue;
                    var val = src[p];
                    if (typeof val == 'undefined')
                        continue;
                    if (typeof desc[p] == 'undefined' || !desc[p]) {
                        if (p == 'allPrice') {
                            desc[p] = parseFloat(val).toFixed(2);
                        } else {
                            desc[p] = parseInt(val);
                        }
                    } else {
                        if (p == 'allPrice') {
                            desc[p] = (parseFloat(desc[p]) + parseFloat(val)).toFixed(2);
                        } else {
                            desc[p] = parseInt(desc[p]) + parseInt(val);
                        }
                    }
                }
            }
            else if (action == 2) {
                var p = self.options.valuefield;
                var val = src[p];
                if (typeof val == 'undefined')
                    return;
                if (typeof desc[p] == 'undefined' || !desc[p])
                    desc[p] = val;
                else
                    desc[p] = parseInt(desc[p]) + parseInt(val);
                self.context.info.merge += 1;
            }
            if (action == 2)
                self.out(`【${src.__r__}】累加到【${desc.__r__}】`, 'info');
            if (self.options.H2V) {//重新计算SKU
                Excel.utils.H2V(desc, self.hdr.size[desc.sizeKind], self.options.excludefields, self.options.valuefield);
            }
            return false;
        };

        function loadRow(count, index, row) {
            onLoading(count, index, row);

            if (!$.isFunction(self.validate))
                return true;
            var msg = self.validate.call(self, count, index, row);
            if (msg) {
                return self.error(row, msg);
            }
            return true;
        }

        function clear() {
            $('.excel-ok', self.controls.panel).linkbutton({text: '导入'});
            //$('.excel-info', self.controls.panel).text("");
            self.context = null;
            self.rows = null;
            self.header = null;
            self.h = null;
            self.errors = null;
            self.status = 0;
        }

        self.handleFiles = handleFiles;
    };

    /**
     * 导出
     * @param options
     * @constructor
     */
    Excel.Export = function (options) {
        var File = require('./FileSaver');
        var self = this;
        this.stoped = true;
        var sheetName = "Sheet1"; //默认导入的表格名称
        var sizekKindField = "sizeKind"; //尺码类型列名称，非尺码横排不用

        /**
         * 加载数据是动态处理参数
         * @param p
         * @returns {*}
         */
        this.getParameter = function (p) {
            return p;
        };//


        /**
         * 导入开始回调
         */
        this.onBegin = function () {

        };
        /**
         * 加载进度
         * @param count 总数
         * @param index 索引
         * @param row 当前行
         */
        this.onLoading = function (count, index, row) {

        };
        /**
         * 数据处理结束
         * @param data
         */
        this.onLoaded = function (data) {

        };

        /**
         * 每页数据加载数据回调
         * @param data
         * @returns {*}
         */
        this.onLoadedData = function (data) {
            return data;
        };

        /**
         * 写入行事件
         * @param index 行索引
         * @param row 行数据
         * @returns {number} 受影响行
         */
        this.onPreWriteRow = function (index, row) {
            return 0;
        };

        this.onPreSave = function () {

        };

        this.writeRow = function (row) {
            if (this.options.V2H) {
                writeV2HRow(row, true);
            }
            else {
                writeRow(row, true);
            }
        };
        //this.setHeader = function(header){
        //
        //};
        this.options = $.extend({
            url: '',//数据获取URL
            concurrency: 5,
            V2H: false, //是否尺码横排
            errorContinue: true,//错误是否继续
            bookType: 'xlsx', //输出文件类型 xlsx & xls
            header: [[]],//列头映射
            valuefield: '', //尺码横排时的取值列
            primaryKey: [], //默认为分组列
            include: [], //不需要显示在Excel里面但是需要用来做计算的字段。
            headerIndex: 0,
            auto: true, //自动开始
            convert: true,           //如果是尺码横排是否进行行转列
            pageSize: 500,
        }, options);

        this.options.concurrency = Math.max(1, this.options.concurrency);
        this.options.concurrency = Math.min(5, this.options.concurrency);

        if (this.options.V2H) {
            if (!self.options.valuefield && self.options.convert)
                throw '数据字段必须指定';

            if (typeof self.options.valuefield == 'string')
                self.options.valuefield = [{name: self.options.valuefield, field: self.options.valuefield}];

            if (self.options.primaryKey.length == 0) {
                if (!self.options.header.length)
                    throw '列头信息不能为空';
                for (var i = 0; i < self.options.header.length; i++) {
                    var field = self.options.header[i];
                    if (field.name == sizekKindField) {
                        self.sizeIndex = i;
                        field.width = 30;
                        break;
                    }
                    self.options.primaryKey.push(field.name);
                }
            }
        }


        function Workbook() {
            if (!(this instanceof Workbook)) return new Workbook();
            this.SheetNames = [];
            this.Sheets = {};
        }

        function onLoaded() {
            self.info('写入数据...');
            self.onLoaded();
        }

        function writeHeaderRow(row) {
            var header = row;//
            if (typeof header == 'undefined')
                header = self.options.header;
            if (header == null || header.length == 0)
                return 0;
            var item = header[0];
            if (item instanceof Array) {
                self.multiHeader = true;
                for (var r of header)
                    writeHeaderRow(r);
                return header.length;
            }
            else {
                for (var C = 0; C < header.length; C++) {
                    if (self.options.V2H && self.options.V2H && C >= self.options.primaryKey.length)
                        break;
                    var r = header[C];//.title;
                    if (r.__skip__ || r.hidden)
                        continue;

                    var rowspan = parseInt(r.rowspan);
                    var colspan = parseInt(r.colspan);

                    if (!isNaN(rowspan) && rowspan > 1)
                        self.merge(self.R, C, self.R + rowspan - 1, C);
                    if (!isNaN(colspan) && colspan > 1)
                        self.merge(self.R, C, self.R, C + colspan - 1);
                    writeCell(self.R, C, r.title);
                }
                self.R += 1;
            }
        }

        /**
         * 写入数据
         * @param datas  数据集
         */
        function write(datas) {
            for (var i = 0; i < datas.length; i++) {
                var row = datas[i];
                if (self.options.V2H)
                    writeV2H(row);
                else
                    writeRow(row, false);

                if ($.isFunction(self.onLoading))
                    self.onLoading(self.total, self.dataIndex, row);

                self.dataIndex += 1;
            }
            return true;
        }

        /**
         * 写入一行数据
         * @param r  行号
         * @param row 行数据
         */
        function writeRow(row, event) {
            var r = self.R;
            var header = self.options.header;
            self.rs.length += 1;
            if (!event)
                self.onPreWriteRow(self.R, row);
            if (self.multiHeader)
                header = header[header.length - 1];

            if (header != null && header.length > 0) {
                for (var C = 0; C < header.length; C++) {
                    if (header[C].hidden)
                        continue;

                    var val = getCellValue(header[C], row);
                    var dateType = header[C].column.dataType;
                   
                    writeCell(r, C, val, dateType);
                }
            }
            else {
                var C = 0;
                for (var p in row) {
                    var val = row[p];
                    writeCell(r, C, val);
                    C += 1;
                }
            }
            self.R += 1;
        }

        /**
         * 写入尺码横排
         * @param row
         * @returns {number}
         */
        function writeV2HRow(row, event) {
            var s = -1;
            var C = 0;
            self.rs.length += 1;
            var maxCell = self.context.maxCell;
            if (!event)
                self.onPreWriteRow(self.R - 1, row);
            var offset = self.options.convert ? 0 : 1; //服务器端很排从1开始计数
            $.each(self.options.header, function (j, field) {
                if (field.name == sizekKindField) {
                    writeCell(self.R, C, row[sizekKindField]);
                    C += 1;
                    var sizes = Excel.utils.size[row[sizekKindField]];
                    if (sizes) {
                        $.each(sizes, function (k, size) {
                            writeCell(self.R, C, row[SIZE_PREFIX + (k + offset)], 'number');
                            C += 1;
                        });
                    }
                    s = 1;
                }
                else {
                    var val = getCellValue(field, row);
                    var c = C;
                    if (s > 0) {
                        c = maxCell + s;
                        s += 1;
                    }
                    writeCell(self.R, c, val);
                    C += 1;
                }
            });
            self.R += 1;

        }

        function getCellValue(field, row) {
            var val = row[field.name];
            if ($.isFunction(field.formatter))
                val = field.formatter.call(self, val, row, self.R, true);
            return val;
        }

        /**
         * 写入单元格
         * @param R 行号
         * @param C 列
         * @param val 值
         */
        function writeCell(R, C, val, type) {
            var sheet = self.wb.Sheets[sheetName];
            var range = self.range;
            if (range.s.r > R) range.s.r = R;
            if (range.s.c > C) range.s.c = C;
            if (range.e.r < R) range.e.r = R;
            if (range.e.c < C) range.e.c = C;
            if (val != null) {
                var cell_ref = XLSX.utils.encode_cell({c: C, r: R});
                var cell = null;
                if (typeof val === 'number' || type == 'number')
                    cell = Excel.utils.getCell('n', val);//cell.t = 'n';
                else if (typeof val === 'boolean') cell = {v: val, t: 'b'};
                else if (val instanceof Date)
                    cell = {t: 'n', z: XLSX.SSF._table[14], v: datenum(val)};
                else
                    cell = Excel.utils.getCell('s', val); //  cell.t = 's';
                sheet[cell_ref] = cell;
            }
        }

        function calculateColumnWidth(sheet) {
            var header = self.options.header;
            if (header == null || header.length == 0)
                return;

            var item = header;
            if (header[header.length - 1] instanceof Array)
                item = header[header.length - 1];
            var styles = [];
            for (var i = 0; i < item.length; i++) {
                var field = item[i];
                var width = parseInt(field.width);
                if (isNaN(width))
                    width = 80;
                width = Math.max(60, width);
                var o = {min: i + 1, max: i + 2, wch: width / 6.5};
                if (item[i].hidden)
                    o.hidden = 1;
                styles.push(o);
            }
            sheet['!cols'] = styles;
        }

        function exportFile(wb) {
            self.onPreSave();
            var filename = self.fileName;
            self.controls.info.text("正在准备导出文件...");
            wb.Sheets[sheetName]['!ref'] = XLSX.utils.encode_range(self.range);
//            wb.Sheets[sheetName]['!merges'] = [
//                {
//                    s: {r: 0, c: 0},
//                    e: {r: 0, c: 3}
//                }
//            ];
//            wb.Sheets[sheetName]['!cols'] = [
//                {
//                    min: 1, max: 2, wch: 20.375
//                }
//            ];
            calculateColumnWidth(wb.Sheets[sheetName]);
            //setTimeout(function () {
            //    onLoaded();
            //    if (!self.stoped) {
            //        var buff = XLSX.write(wb, {bookType: self.options.bookType, bookSST: true, type: 'binary'});
            //        File.saveAs(new Blob([s2ab(buff)], {type: "application/octet-stream"}), filename);
            //    }
            //    clear();
            //    self.controls.panel.window('close');
            //}, 50);
            onLoaded();
            if (!self.stoped) {
                XLSX.write(wb, {bookType: self.options.bookType, bookSST: true, type: 'binary'}, function (buff) {
                    File.saveAs(new Blob([s2ab(buff)], {type: "application/octet-stream"}), filename);
                    clear();
                    self.controls.panel.window('close');
                })
            }
        }

        function insert(index, datas) {
            var sheet = self.wb.Sheets[sheetName];
            var range = self.range;
            if (!(datas instanceof Array)) {
                datas = [datas];
            }
            var step = datas.length;
            moveTo(index, step);
            for (var r = index; r < index + step; r++) {
                var row = datas[r - index];
                writeRow(r, row);
            }
        }

        function moveTo(index, step) {
            var sheet = self.wb.Sheets[sheetName];
            var range = self.range;
            var cell = {r: 0, c: 0};
            var rc = range.e.r;
            var cc = range.e.c;
            for (var R = rc; R >= 0; R--) {
                if (R < index)
                    break;
                for (var C = 0; C <= cc; C++) {
                    cell.r = R;
                    cell.c = C;
                    var src = XLSX.utils.encode_cell(cell);
                    var val = sheet[src];
                    sheet[src] = undefined;
                    cell.r = R + step;
                    var desc = XLSX.utils.encode_cell(cell);
                    sheet[desc] = val;
                }
            }
            range.e.r += step;
        }

        function datenum(v, date1904) {
            if (date1904) v += 1462;
            var epoch = Date.parse(v);
            return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
        }

        function s2ab(s) {
            var buf = new ArrayBuffer(s.length);
            var view = new Uint8Array(buf);
            for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
            return buf;
        }

        function clear() {
            self.rs = self.rows = null;
            self.total = 0;
            self.R = 0;
            self.sh = null;
            self.wb = null;
            self.range = null;
            self.context = null;
            self.stoped = true;
            var p = self.controls.panel;
            self.timer.stop();
            $('.excel-cancel', p).linkbutton('enable');
            $('.excel-ok', p).linkbutton({text: '导出'});
        }

        /**
         * 写入尺码横排到内存
         * @param data
         * @returns {number}
         */
        function writeV2H(data) {
            var rows = self.rows;
            var r = 0;
            if (!self.options.convert) {
                rows.push(data);
                data.__r__ = rows.length;
                var sizeKind = data[sizekKindField];
                if (sizeKind) {
                    Excel.utils.getSizeInfo(sizeKind);
                    self.sh[sizeKind] = true;
                }
                return r;
            }
            var key = getGroupKey(data);
            var row = self.rs[key];
            if (!row) {//写入第一行行头
                self.rs[key] = row = {};
                rows.push(row);
                row.__r__ = rows.length;
                row[sizekKindField] = data[sizekKindField];
                row['sizeNo'] = data['sizeNo'];

                $.each(self.options.primaryKey, function (j, field) {
                    row[field] = data[field];
                });
                $.each(self.options.include, function (j, field) {
                    row[field] = data[field];
                });
            }
            var field = Excel.utils.getSizeField(data);//getSizeIndex(data);
            self.sh[data[sizekKindField]] = true;
            $.each(self.options.valuefield, function (i, f) {
                var val = data[f.field];
                if (i == 0)
                    row[field] = val;
                else
                    row[field + "_" + i] = val;
            });
            return r;
        }

        function writeV2Hheader() {
            var ary = [];
            for (var p in self.sh) {
                ary.push(p);
            }
            var count = ary.length;
            self.R = 0;
            var vflength = self.options.valuefield.length;
            var index = self.options.primaryKey.length;
            var maxCell = 0;
            for (var i = 0; i < count; i++) {
                if (i == count - 1 && vflength == 1) {
                    writeHeaderRow();
                    self.R -= 1;
                }
                var c = index;
                var sizes = Excel.utils.size[ary[i]]; //self.size[ary[i]];
                writeCell(self.R, c, ary[i]);
                c += 1;
                for (var j = 0; j < sizes.length * vflength; j++) {
                    maxCell = Math.max(c + j, maxCell);
                    if (j % vflength != 0 && vflength > 1)
                        continue;
                    var s = sizes[j / vflength];
                    writeCell(self.R, c + j, s);
                    if (vflength > 1)
                        self.merge(self.R, c + j, self.R, c + j + vflength - 1);
                }
                if (i == count - 1 && vflength == 1) {
                    var ci = 0;
                    for (var k = 0; k < self.options.header.length; k++) {
                        var field = self.options.header[k];
                        if (field.name == sizekKindField) {
                            ci += 1;
                            continue;
                        }
                        if (ci < 1)
                            continue;
                        writeCell(self.R, maxCell + ci, field.title);
                        ci += 1;
                    }
                }
                self.R += 1;
            }

            if (vflength > 1) {
                writeHeaderRow();
                self.R -= 1;
                var c = index + 1;
                for (var i = c; i <= maxCell; i++) { //多列
                    var f = self.options.valuefield[i % vflength];
                    writeCell(self.R, i, f.name);
                }
                //写入尺码最右边列
                var ci = 0;
                for (var k = 0; k < self.options.header.length; k++) {
                    var field = self.options.header[k];
                    if (field.name == sizekKindField) {
                        ci += 1;
                        continue;
                    }
                    if (ci < 1)
                        continue;
                    writeCell(self.R, maxCell + ci, field.title);
                    ci += 1;
                }

                self.R += 1;
            }
            return maxCell;
        }

        function flush() {
            if (!self.options.V2H)
                return null;
            self.context.maxCell = writeV2Hheader();
            self.info("正在格式化数据...");
            return flushbody();
        }

        function flushbody() {
            self.info("写入数据...");
            self.processValue(0, self.rows.length);
            var def = $.Deferred();
            var count = self.rows.length;
            var index = 0;
            var task = function () {
                while (index < count) {
                    var row = self.rows[index];
                    writeV2HRow(row, false);
                    self.processValue(index, self.rows.length);
                    index += 1;
                    if (index >= count) {
                        def.resolve();
                    }
                    if ((index % 1000) == 0)
                        break;
                }
                if (index < count)
                    window.setTimeout(task, 10);
            };
            setTimeout(task, 50);
            return def;
            //};
            //$.each(self.rows, function (i, row) {
            //    writeV2HRow(row, false);
            //    self.processValue(self.R, self.rows.length);
            //});
        }

        function getGroupKey(row) {
            var key = '';
            for (var i = 0; i < self.options.primaryKey.length; i++) {
                var f = self.options.primaryKey[i];
                var alias = f.replace('Name', 'No');
                var val = row[f] || "_";
                if (row.hasOwnProperty(alias)) {
                    val = row[alias] || "_";
                }
                key += "&" + val;
            }
            return key;
        }

        function createPanel() {
            var panel = '<div>'
                + '<div class="excel-export" title="导出" >'
                + '<div style="padding:10px">' +
                '<div style="height: 24px;"><span class="timer"></span>&nbsp;<span class="info"></span></div>'
                + '<div class="progress">'
                + '<div class="easyui-progressbar p" style="width:410px;"></div>'
                + '</div><div style="display: none"><label><input type="radio" name="excelType" value="xlxs">XLXS</label>'
                + '<label><input type="radio" name="excelType" value="xlx">XLX</label>' +
                '</div>'
                + '<div class="excel-buttons" >'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-ok" style="margin: 2px">导出</a>'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-cancel"  style="margin: 2px">关闭</a>'
                + '</div>'
                + '</div></div></div>';

            var p = $(panel).appendTo($('body'));
            var prg = $('.easyui-progressbar', p);
            $.parser.parse(p);
            self.prg = prg;

            $('.excel-cancel', p).bind('click', function () {
                self.controls.panel.window('close');
            });
            $('.excel-ok', p).bind('click', function () {
                if (self.stoped) {
                    $('.excel-ok', p).linkbutton({text: '停止'});
                    self.load();
                }
                else {
                    $.messager.confirm('导入', '确定要停止导出么?', function (r) {
                        if (r) {
                            self.stoped = true;
                            self.info("正在尝试停止导出...");
                        }
                    });
                }
            });

            self.controls = {
                prg: prg,
                panel: p.first(),
                info: $('.info', p),
                timer: $('.timer', p),
                excelType: $('radio:first', p)
            };
            self.timer = new Excel.utils.timer(self.controls.timer);
            if (self.options.auto) {
                setTimeout(function () {
                    $('.excel-ok', p).click();
                }, 50);
            }
        }

        function onCancel(msg) {
        	if( msg )
            $.messager.alert(msg);
            	self.stoped = true;
            self.timer.stop();
            clear();
        }

        var onBegin = function () {
            self.stoped = false;
            self.context = {total: 0, params: self.params, id: (new Date).getTime()};
            self.R = self.options.headerIndex;
            self.sh = {};//明细中包含的尺码类型
            self.rs = {length: 0}; //尺码横排时合并数据结果集
            self.rows = [];//横排后数据集
            self.wb = new Workbook();
            self.wb.SheetNames.push(sheetName || "Sheet1");
            self.range = {s: {c: 10000000, r: 10000000}, e: {c: 0, r: 0}};
            self.wb.Sheets[sheetName] = {};
            self.sizeIndex = 0;//尺码列位置
            self.dataIndex = 0;//数据记录数
            if (!self.options.V2H) { //如果非尺码横排，先写入表头
                writeHeaderRow();
            }
            self.status = 1;
            self.controls.timer.text("00:00:00");
            self.timer.start();
            var p = self.controls.panel;
            $('.excel-ok', p).linkbutton({text: '停止'});
            $('.excel-cancel', p).linkbutton('disable');
            self.info("正在提取数据...");
            self.onBegin.call(this);
        };

        this.showPanel = function () {
            if (!self.controls) {
                createPanel();
                var options = {
                    iconCls: 'icon-export', modal: true, closable: false, minimizable: false,
                    maximizable: false, resizable: false, collapsible: false,
                    title: '导出',
                    width: 450,
                    height: 200
                };
                self.controls.panel.window(options);
            } else {
                self.controls.panel.window('open');
            }
            self.controls.prg.progressbar('setValue', 0);
        };

        this.loadData = function (url, params) {
            return $.post(url, params);
        };

        var ajaxLoader = {
            close: function () {

            },
            load: function () {
                onBegin();
                self.info("正在获取数据...");
                var url = self.options.url;
                var params = self.context.params;
                var progress = self.context.progress = {}; //加载进度
                self.total = 0;
                params.rows = self.options.pageSize;
                params.page = 1;
                params.export = "true";
                params.fileName = self.fileName;
                var concurrency = self.options.concurrency;

                var pageCount = 0;
                var getData = function (first) {
                    if (params.page > pageCount && pageCount != 0)
                        return;
                    var p = $.extend({}, params);
                    if (self.total > 0)
                        p.total = self.total;

                    if ($.isFunction(self.getParameter))
                        p = self.getParameter(params);
                    if (first || concurrency == 1) {
                        self.loadData(url, p).then(function (data) {
                            process(data, true);
                        });
                    }
                    var promises = [];
                    for (var i = 0; i < concurrency; i++) {
                        if (params.page + 1 > pageCount)
                            break;
                        var tmp = $.extend({}, p);
                        tmp.page = params.page = params.page + 1;
                        promises.push(self.loadData(url, tmp));
                    }
                    $.when.apply(null, promises).then(function () {
                        if (promises.length == 1)
                            process(arguments[0], true);
                        else
                            for (var index = 0; index < arguments.length; index++)
                                process(arguments[index][0], index == arguments.length - 1);
                    });
                };

                var process = function (data, last) {
                    if (self.stoped) {
                        onCancel('操作取消成功');
                        return;
                    }
                    if (data.total == 0 || data.billNo == "") {
                        onCancel('没有数据');
                        return;
                    }
                    if (typeof data.ticket !== 'undefined')
                        params.ticket = data.ticket;

                    if (data.errorCode) {
                        onCancel(data.errorMessage);
                        return;
                    }
                    if (self.total == 0) {
                        params.total = self.total = data.total;
                        params.pageCount = pageCount = Math.ceil(params.total / self.options.pageSize);
                    }
                    if (typeof data.rows == 'string') {
                        data.rows = $.parseJSON(data.rows);
                    }
                    data.rows = self.onLoadedData(data.rows);
                    self.processValue(params.page, params.pageCount);
                    if (data.rows.length > 0) {
                        var ok = write(data.rows);
                        if (!ok)
                            return;
                    }
                    if (params.page >= pageCount && last) {
                        var def = flush();
                        if (def == null) {
                            exportFile(self.wb);
                        }
                        else
                            def.then(function () {
                                exportFile(self.wb)
                            });

                        return;
                    }
                    if (last)
                        getData(false);
                };

                getData(true);
            }
        };

        var wsLoader = {
            load: function () {
                onBegin();
                this.connect().then(c=> {
                    var url = self.options.url + self.context.id;
                    var params = $.extend({}, self.context.params);
                    if ($.isFunction(self.getParameter))
                        params = self.getParameter(params);
                    //"/mdm/brand/asyncexport/"
                    $.get(url, params)
                        .fail(e=>this.close());
                }).fail(e=>{
                    onCancel(e);
                });

            },
            close: function (reson) {
                if (this.client != null) {
                    this.client.disconnect();
                }
            },
            connect: function () {
                var def = $.Deferred();
                seajs.use(["core/io/sockjs", "core/io/stomp"], ()=> {
                    var socket = new SockJS(APP_SETTINGS.contextPath + '/_websocket');
                    var client = Stomp.over(socket);
                    client.debug = null;                    
                    this.client = client;
                    var id = self.context.id;
                    var url = APP_SETTINGS.contextPath + '/_topic/asyncexported/' + id;
                    
                    client.connect({}, (frame) => {
                        if( frame.command != "CONNECTED") {
                            def.reject('服务器异常');
                            return;
                        }
                        client.subscribe(url, (r) => {
                            this.loadData($.parseJSON(r.body));
                        });
                        def.resolve();
                    });
                });

                return def;
            }
            , loadData: function (data) {
                if (self.stoped) {
                    onCancel('操作取消成功');
                    return;
                }
                if (data.total == 0) {
                    onCancel('没有数据');
                    return;
                }
                if (data.errorCode) {
                    onCancel(data.errorMessage);
                    return;
                }
                if (data.rows == null) {
                    var def = flush();
                    if (def == null) {
                        exportFile(self.wb);
                        this.close();
                    }
                    else {
                        def.then(()=> {
                            exportFile(self.wb);
                            this.close();
                        });
                    }
                    return;
                }
                var rows = self.onLoadedData(data.rows);
                if (!self.context.index)
                    self.context.index = data.rows.length;
                self.context.index += data.rows.length;
                var count = data.total;
                self.processValue(self.context.index, count);
                if (rows.length > 0) {
                    if (!write(rows))
                        return;
                }
            }
        };

        this.load = function () {
            let loader = self.options.method == "ws" ? wsLoader : ajaxLoader;

            loader.load();
        };

        this.info = function (msg) {
            self.controls.info.text(msg);
        };

        this.processValue = function (index, count) {
            var value = parseInt((index / count) * 100);
            self.controls.prg.progressbar('setValue', value);
        };

        this.merge = function (r1, c1, r2, c2) {
            var sheet = self.wb.Sheets[sheetName];
            var ary = sheet['!merges'];
            if (typeof ary == 'undefined')
                sheet['!merges'] = ary = [];
            ary.push({
                s: {r: r1, c: c1},
                e: {r: r2, c: c2}
            });
        };

        /**
         * 导出数据
         * @param fileName
         * @param params
         */
        this.export = function (fileName, params) {
            self.params = $.extend({}, params);
            sheetName = fileName;
            self.fileName = fileName + ".xlsx";
            this.showPanel();
        };
    };

    /**
     * 工具类
     */
    function utils() {
        var self = this;
        var cell_s = {t: 's', v: '', _: 1};
        var cell_val = [];
        var cell_n = [];
        var useMuchString = ['店仓', '仓库', '销售', '订货', '入在途', '库存', '男', '女', '童', '无性别', '中性',
            '春', '夏', '秋', '冬', '春夏', '秋冬', '无季节', '鞋', '童鞋', '服', '包饰', '护鞋用品',
            '促销品', '其他辅助保养用品', '其他实物', '券', '纸袋', '包装用品',
            '物料', '黑色', '灰色', '红色', '白色', '粉色', '深蓝', '蓝色', '透明', '绿色',
            '单鞋', '靴', '低靴', '银色', '百丽', 'IstBelle', 'Bevivo', '百思图', '真美诗',
            '森达', '妙丽', '他她', '思加图'];
        var normalCells = {};
        !(function make() {
            for (var i = -10; i < 100; i++) {
                cell_n[i] = {t: 'n', v: i, _: i};
                cell_val.push(i);
            }
            $.each(useMuchString, function (i, val) {
                normalCells[val] = {t: 's', v: val, _: (i + 2)};
            });
        })();

        //require(['gms/repository' ], function (repository) {
        //    self.repository = repository;
        //});

        this.getCell = function (type, val) {
            if (type == 'n' && ( val > -10 && val < 100 && cell_val.contains(val)) && Math.ceil(val) == val)
                return cell_n[val];
            else if (type == 's' && (!val || normalCells[val])) {
                if (!val) {
                    return cell_s;
                } else {
                    return normalCells[val];
                }
            }
            else
                return {t: type, v: val};
        };

        /**
         * 行转列
         * @constructor
         */
        this.H2V = function (row, sizes, excludefields, valuefield) {
            var size = sizes;//self.hdr.size[row.sizeKind];
            if (!size)
                return null;
            var C = {};
            for (var p in row) {
                if (p.indexOf('_') >= 0 || p == "skus")
                    continue;
                if (/v\d+/.test(p) || excludefields.indexOf(p) >= 0)
                    continue;
                C[p] = row[p];
            }
            row.skus = [];
            for (var p in row) {
                if (/v\d+/.test(p) == false
                    || p.indexOf('_') >= 0)
                    continue;
                if (!row[p])
                    continue;
                var obj = $.extend({}, C);
                var index = parseInt(p.substr(SIZE_PREFIX.length));
                obj.sizeNo = size[index];
                obj[valuefield] = parseInt(row[p]);
                row.skus.push(obj);
            }
            return row;
        };


        this.updateItem = function (items, callback) {

        };
        function syncSku(src, desc, callback) {

        }

        this.getManyItemSku = function (items, callback) {
            var ary = [];

            return ary;
        };

        this.timer = function (element) {
            var now = new Date();
            this.interval = null;
            this.start = function () {
                now = new Date();
                this.interval = window.setInterval(function () {
                    $(element).text(formatTime(new Date() - now));
                }, 1000);
                return this;
            };
            this.stop = function () {
                window.clearInterval(this.interval);
            };
            //return
        };

        /**
         * 压缩上传至服务器的字符串数据
         * */
        this.zipJSONStr = function (datas) {
            var result = {zipFlag: false, zipData: "$"};
            if (!datas)
                return result;
            var JSZip = require("./jszip");
            var zip = new JSZip();
            if (typeof datas !== "string") {
                datas = JSON.stringify(datas);
            }
            zip.file("data.txt", datas);
            var expected = zip.generate({compression: "DEFLATE"});
            //比较压缩后的字段长度与压缩前的字段长度
            /*var actual = JSZip.utils.string2binary(datas);
             //若压缩后的字符串长度大于或者等于压缩前的，则不必压缩
             if (actual.length <= expected.length) {
             return result;
             }else{
             return expected ? {zipFlag:true,zipData:expected} : result;
             }*/

            return expected ? {zipFlag: true, zipData: expected} : result;
        };

        /**
         * 压缩上传至服务器的数组数据
         * */
        this.zipJSONArray = function (datas) {
            var result = {zipFlag: false, zipData: ["$"]};
            if (!datas || !datas.length)
                return result;
            var JSZip = require("./jszip");
            var zip = new JSZip();
            var zipArray = [];
            var actual = 0;
            var expected = 0;

            $.each(datas, function (i, value) {
                if (typeof value !== "string") {
                    value = JSON.stringify(value);
                }
                zip.file("data.txt", value);
                var zipValue = zip.generate({compression: "DEFLATE"});
                actual += (JSZip.utils.string2binary(value)).length;
                expected += zipValue.length;
                zipArray.push(zipValue);
            });

            //比较压缩后的字段长度与压缩前的字段长度
            //若压缩后的字符串长度大于或者等于压缩前的，则不必压缩
            if (actual <= expected) {
                return result;
            } else {
                return zipArray.length > 0 ? {zipFlag: true, zipData: zipArray} : result;
            }

            return zipArray.length > 0 ? {zipFlag: true, zipData: zipArray} : result;
        };

        this.getColumns = function (H2V, grid) {
            var options = $(grid).datagrid('options');
            if (!H2V)
                return getNormalColumns(options);

            var preCols = options.preCols;
            var frozenCols = options.frozenColumns;
            var endCols = options.endCols;
            var ary = [];
            if (typeof frozenCols !== 'undefined') {
                var frozenColsTemp = $.extend(true, [], frozenCols);
                $.each(frozenColsTemp, function (i, o) {
                    $.each(o, function (j, o2) {
                        if (o2.hasOwnProperty('notexport') && !o2.notexport) {
                            ary.push(getColumn(o2));
                        } else if (o2.hasOwnProperty('notexport') && o2.notexport) {
                            //不导出
                        } else {
                            if (!o2.hidden)
                                ary.push(getColumn(o2));
                        }

                    });
                });
            }

            if (typeof preCols !== 'undefined') {
                var preColsTemp = $.extend(true, [], preCols);
                $.each(preColsTemp, function (i, o) {
                    if (o.hasOwnProperty('notexport') && !o.notexport) {
                        ary.push(getColumn(o));
                    } else if (o.hasOwnProperty('notexport') && o.notexport) {
                        //不导出
                    } else {
                        if (!o.hidden)
                            ary.push(getColumn(o));
                    }

                });
            }

            ary.push({name: 'sizeKind', title: ''});
            if (typeof endCols !== 'undefined') {
                var endColsTemp = $.extend(true, [], endCols);
                $.each(endColsTemp, function (i, o) {
                    if (o.hasOwnProperty('notexport') && !o.notexport) {
                        ary.push(getColumn(o));
                    } else if (o.hasOwnProperty('notexport') && o.notexport) {
                        //不导出
                    } else {
                        if (!o.hidden)
                            ary.push(getColumn(o));
                    }
                });
            }

            return ary;
        };

        function getNormalColumns(options) {
            var headersTemp = [];
            var tmp = [];
            var len = Math.max(options.frozenColumns.length, options.columns.length);
            for (var f = 0; f < len; f++) {
                var ary = null;
                if (options.columns.length <= f)
                    ary = $.merge([], options.frozenColumns[f]);
                else if (options.frozenColumns.length <= f)
                    ary = $.merge([], options.columns[f]);
                else
                    ary = $.merge($.merge([], options.frozenColumns[f]), options.columns[f]);
                headersTemp.push(ary);
                tmp.push([]);
            }

            var headers = [];

            for (var j = 0; j < headersTemp.length; j++) {
                var headersT = $.grep(headersTemp[j], function (o, i) {
                    if (o.field == 'sizeKind' && (!o.title)) {
                        o.title = '尺码类型';
                    }
                    if (o.title) {// 查询类表导出时，checkBox列没有title属性
                        o.title = o.title.replace("<span class='ui-color-red' style='font-size:20px'>*</span>", "");
//	        			if(o.notexport){
//	        				return true;
//	        			}else{
//	        				if(o.hidden){
//	        					return false;
//	        				}else{
//	        					return false;
//	        				}
//	        			}
                        if (o.hasOwnProperty('notexport') && !o.notexport) {
                            return false;
                        } else if (o.hasOwnProperty('notexport') && o.notexport) {
                            return true;
                        } else {
                            if (!o.hidden)
                                return false;
                        }
                    } else {
                        return true;
                    }
                }, true);
                headers.push(headersT);
            }

            for (var j = 0; j < headers.length; j++) {
                if (j == 0 || (j != 0 && tmp[j].length < tmp[j - 1].length)) {
                    var colsTemp = $.extend(true, [], headers[j]);
                    $.each(colsTemp, function (i, o) {
                        var rowspan = parseInt(o.rowspan);
                        var colspan = parseInt(o.colspan);
                        var current = tmp[j];
                        var item = getColumn(o);
                        current.push(item);
                        if (!isNaN(rowspan)) {
                            for (var r = 1; r < rowspan; r++) {
                                tmp[r][current.length - 1] = $.extend({__skip__: true}, item);
                            }
                        }
                        if (!isNaN(colspan)) {
                            for (var r = 1; r < colspan; r++) {
                                var val = $.extend({__skip__: true}, item);
                                current.push(val);
                            }
                        }
                    });
                }
            }

            var len = tmp[0].length;
            for (var i = 1; i < tmp.length; i++) {
                var ary = tmp[i];
                var index = 0;
                var count = Math.max(len, ary.length);
                for (var j = 0; j < count; j++) {
                    if (!ary[j]) {
                        var colsTemp = $.extend(true, [], headers[i][index++]);
                        ary[j] = getColumn(colsTemp);
                    }
                    if (ary[j])
                        ary[j].rowspan = 0;
                }
            }
            console.log(tmp);
            return tmp;
        }

        function getColumn(col) {
            //单据去掉*号
            if (col.title) {
                col.title = col.title.replace("<span class='ui-color-red' style='font-size:20px'>*</span>", "");
            }
            delete col.hidden; //删掉隐藏属性
            return new Field(col);
            //var obj = {
            //    name: col.field,
            //    title: col.title,
            //    rowspan: col.rowspan,
            //    colspan: col.colspan,
            //    width: col.width,
            //    hidden: col.hidden,
            //    align: col.align,
            //    formatter: col.formatter
            //};
            //return obj;
        }

        var formatTime = function (unixTimestamp) {
            var dt = new Date(unixTimestamp);

            var hours = dt.getHours();
            var minutes = dt.getMinutes();
            var seconds = dt.getSeconds();

            // the above dt.get...() functions return a single digit
            // so I prepend the zero here when needed
            hours -= 8;
            if (hours < 10)
                hours = '0' + hours;

            if (minutes < 10)
                minutes = '0' + minutes;

            if (seconds < 10)
                seconds = '0' + seconds;

            return hours + ":" + minutes + ":" + seconds;
        };
    }

    function Field(column) {
        if (typeof column == 'undefined' || column == null)
            this.column = {};
        else
            this.column = column;
    }

    var properties = ['name', 'title', 'rowspan', 'colspan', 'width', 'hidden', 'align', 'formatter'];
    for (var i = 0; i < properties.length; i++) {
        var p =
            (function (p) {
                Field.prototype.__defineGetter__(p, function () {
                    return this.column[p];
                });
                Field.prototype.__defineSetter__(p, function (val) {
                    this.column[p] = val;
                });
            })(properties[i]);
        Field.prototype.__defineGetter__('name', function () {
            return this.column.field;
        });
        Field.prototype.__defineSetter__('name', function (val) {
            this.column.field = val;
        });
    }

    Excel.utils = new utils();

    return Excel;
});
