"use strict";
/**
 * UI
 * @module core/ui
 */
define(function (require, exports, module) {
/*    let author = require('security/author');*/
    let Renderer = require('core/renderer');
    const fields = ["field", "title", "width", "dataType", "dataField", "align", "colspan", "rowspan"];

    /**
     * Class 页面基类
     */
    class Page {
        /**
         *
         * @param {String} moduleNo 页面对应的模块编号
         * @param {Jquery} container  页面渲染的容器
         */
        constructor(moduleNo, container) {
            this.moduleNo = moduleNo;
            this.mainPanel = container;
            window.$page = this;
            //easyui.keyboard['Ctrl+N'] =function(){
            //    console.log('aaaa');
            //    return false;
            //}
        }

        /**
         * 页面视图
         * @returns {*}
         */
        get views() {
            return this._views;
        }

        set views(views) {
            this._views = views;
            var self = this;
            $.each(views, (i, v)=>v.page = self);
        }

        /**
         * 当前视图
         * @returns {*}
         */
        get currentView() {
            return this._currentView;
        }

        /**
         * 设置当前视图
         * @param view
         */
        set currentView(view) {
            this.switchView(view);
        }

        /**
         * 切换视图
         * @param {View} view 视图对象
         * @see View
         */
        switchView(view) {
            var v = null;
            if ($.isNumeric(view))
                v = this._views[view];
            else
                v = view;
            if (this._currentView != v) {
                this._currentView = v;
                this.onCurrentViewChanged();
                v.load();
            }
            var index = this._views.indexOf(v);
            // this.mainTab.tabs('select', index);
        }

        /**
         * 视图变更事件
         *
         */
        onCurrentViewChanged() {

        }

        /**
         * 双击查询网格--跳至明细面板
         */
        switchTab(rowData, rowIndex) {
            this.mainTab.tabs('select', 0);
            this.loadDtlPage(rowData, rowIndex);
        }

        /**
         * 加载明细行数据
         * @param rowData
         * @param rowIndex
         */
        loadDtlPage(rowData, rowIndex) {
            /**加载表头数据*/
            this.currentView.searchForm.form('load', rowData);
            this.currentView.searchForm.form('disable');
            this.currentView.curIndex = rowIndex;

            /**查询生效店铺*/
            this.currentView.loadedData(rowData, rowIndex);
        }

        /**
         * 呈现页面
         */
        render() {
            var self = this;
            this.renderLayout();
            this.renderView();
            this.rendered();//after
            if (this._views.length > 1) {
                this.mainTab.tabs({
                    border: false,
                    showHeader: true,
                    onSelect: function (title, index) {
                        self.currentView = self.views[index];
                    }
                });
            }
        }

        /**
         * 呈现结束
         */
        rendered() {
            $('#loading').remove();
        }

        /**
         * 呈现view
         * @param {View} view
         * @see View
         */
        renderView() {
            for (var v of this.views) {
                var container = this.getViewContainer(v);
                v.render(container);
                this.currentView = v;
            }

        }

        /**
         * 呈现布局
         */
        renderLayout() {
            //this.mainPanel = this.parent;
            if (this.views.length > 1)
                this.mainTab = $('<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">').appendTo(this.mainPanel);

        }

        /**
         * 获取view对应的dom元素
         * @param {View} view
         * @see View
         * @returns {*|jQuery}
         */
        getViewContainer(view) {
            var parent = null;
            if (!this.mainTab)
                parent = this.mainPanel;
            else
                parent = $(`<div title="${view.title}">`).appendTo(this.mainTab);

            return $('<div class="easyui-layout" style="height:100%;" data-options="fit:true;">').appendTo(parent);
        }

        /**
         * 事件
         * @param target
         * @param event
         */
        onEvent(target, event) {

        }
    }

    /**
     * Class 基本视图
     */
    class View {

        /**
         *
         * @param {String|Integer} viewId 视图Id
         */
        constructor(viewId) {
            this.viewId = viewId;
            this.renderer = new Renderer(this);
        }

        /**
         * 所在Page
         * @returns {*}
         */
        get page() {
            return this._page;
        }

        /**
         * 所在Page
         * @param val
         */
        set page(val) {
            this._page = val;
        }

        /**
         * 获取工具栏
         */
        getToolbars() {
            throw new Error('方法未实现.');
        }

        /**
         * 获取底部工具栏
         */
        getFootToolbars() {
            return;
        }

        /**
         * 呈现视图
         */
        render() {

            this.renderToolbar();

            this.renderBody();
        }

        /**
         * 校验
         *  @virtual
         */
        validate() {

        }

        /**
         * 预导出
         * @virtual
         */
        preExport() {

        }

        /**
         * 导出
         * @virtual
         */
        export() {

        }

        /**
         * 导出完成
         * @virtual
         */
        exported() {

        }

        /**
         *预打印
         * @virtual
         */
        prePrint() {

        }

        /**
         * 打印
         * @virtual
         */
        print() {

        }

        /**
         * 重置
         * @virtual
         */
        clear() {

        }

        /**
         * 工具栏事件处理
         * @param e
         * @param id
         */
        onToobarClick(id) {
            if (id) {
                var name = id.replace(/\w*[_|-]+/g, '');
                if ($.isFunction(this[name])) {
                    this[name]();
                }
            }
        }

        /**
         * 构建工具栏
         */
        renderToolbar() {/*
            var bar = this.getToolbars();

            let self = this;
            //var ds = author.filterOperation(bar.data);
            var items = [];
            for (var v of ds) {
                items.push(v);
                items.push("-");
                v.handler = e => {
                    self.onToobarClick($(e.currentTarget).attr('id'));
                };
                if (v.hotkey) {
                    (function (node) {
                        $(document).bind("keydown", node.hotkey, ()=> {
                            self.onToobarClick(node.id);
                            return false;
                        });
                    })(v)
                }
            }
            items.pop();
            this.toobar.toolbar({items: items});*/
        }


        /**
         * 构建工具栏
         */
        renderFootToolbar(footbarParent, index) {/*
            var bar = this.getFootToolbars()[index];

            let self = this;
            var ds = author.filterOperation(bar.data);
            var items = [];
            for (var v of ds) {
                items.push(v);
                items.push("-");
                v.handler = e => {
                    self.onToobarClick($(e.currentTarget).attr('id'));
                };
                if (v.hotkey) {
                    (function (node) {
                        $(document).bind("keydown", node.hotkey, ()=> {
                            self.onToobarClick(node.id);
                            return false;
                        });
                    })(v)
                }
            }
            items.pop();
            let footbar = footbarParent.toolbar({items: items});
            return footbar;*/
        }

        /**
         * 呈现页面体
         */
        renderBody() {

        }
        
        loadingEnd(){
        	$(".datagrid-mask").remove();   
            $(".datagrid-mask-msg").remove();
        }
        loadingStart(){
        	$.easyui.loading({
                msg: "正在保存，请稍候。。。"
            });
        }
    }

    let export_default_options = {fileName: "导出", async: false};
    /**
     * 列表查询View
     */
    class ListView extends View {

        constructor(viewId) {
            super(viewId);
            this.gridPanel = $('#gridPanel');
            this.search_bar_cols = 4;
        }

        /**
         * 绑定数据的主键
         * @returns {string}
         */
        get primaryKey() {
            return "id";
        }

        /**
         * 当前网格
         * @returns {*}
         */
        get currentGrid() {
            return this._currentGrid;
        }

        set currentGrid(grid) {
            var g = null;
            var index;
            if ($.isNumeric(grid)) {
                g = $(`table[index="${grid}"]`);
                index = grid;
            }
            else {
                g = grid;
                index = $(grid).attr('index');
            }
            if (g == this._currentGrid)
                return;
            this._currentGrid = g;
//            if (this._gridtabs)
//                this._gridtabs.tabs('select', index);
        }

        /**
         * 当前子工具条
         * @returns {*}
         */
        get currentFootBar() {
            return this._currentFootBar;
        }

        set currentFootBar(bar) {
            var b = null;
            var index;
            if ($.isNumeric(bar)) {
                b = $(`<div id="toolbar${bar}"></div>`);
                index = bar;
            }
            else {
                b = bar;
                index = $(bar).attr('index');
            }
            if (b == this.currentFootBar)
                return;
            this._currentFootBar = b;
        }

        /**
         * 获取Datagrid定义
         * @abstract
         */
        getGridOptions() {
            throw new Error('方法未实现.');
        }

        export() {
            let grid = this.currentGrid;
            if (!grid)
                return;
            var options = this.getExportOption(grid);
            if (options.async)
                this.doAsyncExport(options);
            else
                this.doExport(options);

        }

        getExportOption(grid) {
            var options = grid.datagrid('options');
            var op = $.extend({}, export_default_options);
            if (options.export) {
                op = $.extend(op, export_default_options, options.export);
            }
            if (!op.async) {
                op.columns = options.columns.map(ary=>ary.filter(c=>c.export != undefined ? c.export : !c.hidden).map(c=> {
                    var item = {};
                    for (var p of fields)
                        item[p] = c[p];
                    return item;
                }));
                if (!op.url)
                    op.url = "./export";
            }
            else {
            	if(!op.method)
            		op.method = "ws";
                if (!op.url) {
                    op.url = "./asyncexport/";
                }

            }
            return op;
        }

        doAsyncExport(options) {
        	options.params = this.getSearchParams();
        	options.params.fileName=options.fileName;
            let grid = this.currentGrid;
            //let Excel = require('../io/excel/excel');
            seajs.use(["core/io/excel/excel"], function (Excel) {
                options.header = Excel.utils.getColumns(options.V2H, grid);
                var excel = new Excel.Export(options);
                excel.export(options.fileName||"导出", options.params);
            });
        }

        doExport(options) {
            var params = this.getSearchParams();
            params._columns = JSON.stringify(options.columns);
            params._fileName = options.fileName;

            $("#exportExcelForm").remove();
            $("<form id='exportExcelForm' method='post'></form>")
                .appendTo("body");
            $('#exportExcelForm').form('submit', {
                url: options.url,
                onSubmit: function (ps) {
                    $.extend(ps, params);
                }
            });
        }

        /**
         * 新增
         */
        create() {
            this.page.switchView(1);
        }

        /**
         * 获取查询条件定义
         * @abstract
         */
        getSearchControls() {
            throw new Error('方法未实现.');
        }

        /**
         * 预查询
         * @returns {*}
         */
        preSearch() {
            return this.searchForm.form('validate');
        }

        /**
         * 查询结束
         * @virtual
         */
        onSearched() {

        }

        /**
         * 获取查询参数
         * @returns {*}
         */
        getSearchParams() {
            let data = this.searchForm.form('getData');
            let params = {};
            $.each(data,(i,d)=>{
            	params[i] = $.trim(d);
            });
            return params;
        }

        /**
         * 数据加载完毕
         * @param grid
         * @param data
         * @virtual
         */
        loadedData(grid, data) {

        }

        /**
         * 网格预加载数据
         * @param grid
         * @param params
         * @returns {boolean}
         * @virtual
         */
        preLoadData(grid, params) {
            return true;
        }

        /**
         * 加载数据
         * @virtual
         */
        load() {

        }

        /**
         * 查询
         */
        search() {
            if (!this.preSearch())
                return;

            if (!this.searchForm) {
                console.error("必须指定查询面板的Form");
                return;
            }
            let self = this;

            var params = this.getSearchParams();

            var g = this.currentGrid;
            var options = g.datagrid('options');
            if (!options.url)
                options.url = options._url;
            if (!options.url)
                throw new Error('datagrid的Url不能为空.');

            if (options.onLoadSuccess && !g.onLoadSuccess) {
                g.onLoadSuccess = options.onLoadSuccess;
                options.onLoadSuccess = function (data) {
                    g.onLoadSuccess.call(g, data);
                    self.loadedData(g, data);
                }
            }

            if (self.preLoadData(g, params)) {
                options.queryParams = params;
                g.datagrid('load');
            }
        }

        /*
         * 动态计算数据网格的高度
         */
        resize(container) {
            var viewId = this.viewId;
            var h = null;
            if (window.top == window.self) {//不存在父页面
                h = document.body.clientHeight;
            } else {
                h = $(window.document.body).height();
            }
            let main_toolbarH = $("#main_toolbar").outerHeight();
            let searchH = $(".search-div").outerHeight();
            let hh = h - main_toolbarH - searchH;
            $("#main_sublayer").height(h - main_toolbarH);
            if (container && container.length >= 1){
                container.find(`#${viewId}_main_panel`).height(hh);
            }else{         	
                $(`#${viewId}_main_panel`).height(hh);
            } 
        }

        createPanel(container) {
            var viewId = this.viewId;
            var html = `
            <div easyui-layout id="${viewId}_layer">
                <div data-options="region:'north',border:false" style="height:32px;" >
                    <div id="${viewId}_toolbar"></div>
                </div>
                <div class="easyui-layout" data-options="region:'center'"
                			id="${viewId}_sublayer" >
                    <div data-options="region:'north',border:false">
                        <div class="search-div searcher" id="${viewId}_top_panel"></div>
                    </div>
                    <div data-options="region:'center',border:false" id="${viewId}_main_panel">
                    </div>
                </div>
            </div>`;

            return $(html).appendTo(container);
        }

        clear() {
            this.searchForm.form('clear');
        }

        /**
         * 构建查询面板
         */
        renderSearchPanel() {
            var viewId = this.viewId;
            let options = this.getSearchControls();
            var container = this.searcher;
            if (!options)
                return;
            var cols = options.cols ? options.cols : this.search_bar_cols;
            if (options.list)
                cols = options.list;
            else
                cols = Math.max(2, cols);
            this.searchForm = this.renderer.renderControls(container, options.controls, cols);
        }

        /**
         * 获取datagrid的详细参数,并合并并默认值
         * @param {Jquery} grid grid 元素
         * @param {Option} options  配置
         */
        getGridOption(grid, options) {
            var op = $.extend({
                loadMsg: '请稍等,正在加载...',
                iconCls: 'icon-ok',
                pageSize: 20,
                pageNumber:0,
                pageList: [20, 50],
                checkOnSelect: false,
                pagination: true,
                fitColumns: false,
                singleSelect: true,
                rownumbers: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                emptyMsg: "暂无数据"
            }, options);

            op._url = op.url;
            op.url = null;
            op.title = null;

            return op;
        }

        /**
         * 呈现网格
         * @param {Jquery} parent 父容器
         * @param {Options} options 配置
         * @param {Ingeger} index 序列
         */
        renderGrid(parent, options, index) {
            var grid = $(`<table id="view_grid_${options.id}" index="${index}" ></table>`).appendTo(parent);

            var op = this.getGridOption(grid, options);

            return grid.datagrid(op);
        }

        /**
         * 呈现视图
         * @param {Jquery} parent 对应的dom
         */
        render(parent) {
            let self = this;
            var viewId = this.viewId;
            $(window).resize(function () {
                self.resize();
            });

            this.container = this.createPanel(parent);
            if (this.container.length >= 1) {
                this.toobar = this.container.find(`#${this.viewId}_toolbar`);
                this.searcher = this.container.find(`#${this.viewId}_top_panel`);
                this.gridPanel = this.container.find(`#${this.viewId}_main_panel`);
            } else {
                this.toobar = $(`#${this.viewId}_toolbar`);
                this.searcher = $(`#${this.viewId}_top_panel`);
                this.gridPanel = $(`#${this.viewId}_main_panel`);
            }
            this.renderSearchPanel();
            super.render();


            this.resize(this.container);

            var options = this.getGridOptions();
            var option2 = this.getFootToolbars();
            if (!options)
                return;
            var parent = this.gridPanel;
            if (options.length == 1) {
                let grid = this.renderGrid(parent, options[0], 0);
                this.currentGrid = grid;
                return;
            }


            var tabs = self._gridtabs = $(`<div id="view_${this.viewId}_grid_tabs" ></div>`).appendTo(this.gridPanel);
            var index = 0;
            var parent = self.gridPanel;
            var reduce = 30;
            var height = parent.height();
            var gridH = height - 40;
            for (var option of options) {
            	if(option2 && option2[index].data.length >0){
                    gridH = height - 40 - reduce;
            	}
                if (!option.id)
                    throw new Error("必须指定grid的控件id");

                var tab = `<div title="${option.title}" id="view_${this.viewId}_grid_tab${index}"><div id="toolBox${index}">`;
                if (option2) {
                    if (option2[index])
                        tab += `<div id="${option2[index].id}" index="${index}"></div>`;
                }
                tab += `</div><div id="gridBox${index}" style="height:${gridH}px"></div></div>`;

                $(tab).appendTo(tabs);
                let gridParent = $(`#gridBox${index}`);
                let grid = this.renderGrid(gridParent, option, index);
                if (option2) {
                    if (option2[index] && option2[index].data.length > 0) {
                        let footbarParent = $(`#${option2[index].id}`);
                        var footToolbar = this.renderFootToolbar(footbarParent, index);
                    }
                }
                if (option.default || index == 0) {
                    this.currentGrid = grid;
                }
                if ((option.default || index == 0) && option2)
                    this.currentFootBar = footToolbar;
                index += 1;
            }

            tabs.tabs({
                border: false,
                showHeader: true,
                height: height,
                onSelect: function (title, index) {
                    self.currentGrid = $(`table[index="${index}"]`);
                    self.currentFootBar = $(`<div id="toolbar${index}"></div>`);
                }
            });
        }
    }

    /**
     * Class 明细显示View
     */
    class DetailView extends View {

        /**
         *
         * @param {String|Integer} viewId view Id
         */
        constructor(viewId) {
            super(viewId);
            this.detail_cols = 4;
        }

        /**
         * @readonly
         * @returns {boolean}
         */
        get isNew() {
            return this.params == null;
        }

        /**
         * 明细绑定的实体
         * @returns {*}
         */
        get entry() {
            return this._entry;
        }

        set entry(value) {
            this._entry = value;
        }

        /**
         * 获取后端服务对象
         * @abstract
         */
        get service() {
            throw new Error('方法未实现.');
        }

        /**
         * 明细查询参数
         * @returns {*}
         */
        get params() {
            return this._params;
        }

        set params(vals) {
            this._params = vals;
            this.load();
        }

        /**
         * 获取编辑视图的明细表单的控件定义
         * @abstract
         * @return {Array} 控件定义
         */
        getDetailControls() {
            throw new Error('方法未实现.');
        }

        /**
         * 构造弹出panel
         * @param {jQuery} container 父容器
         * @returns {*|jQuery}
         */
        createPanel(container) {
            var viewId = this.viewId;
            var html = `
            <div easyui-layout id="${viewId}_layer">
                <div data-options="region:'north',border:false" style="height:32px;" >
                    <div id="${viewId}_toolbar"></div>
                </div>
                <div class="easyui-layout" data-options="region:'center'"
                			id="${viewId}_sublayer" >
                    <div data-options="region:'north',border:true,fit:true"  >
                        <div class="view-detail" id="${viewId}_panel"></div>
                    </div>
                </div>
            </div>`;

            return $(html).appendTo(container);
        }

        /**
         * 呈现视图
         * @param parent
         */
        render(parent) {
            let self = this;

            this.container = this.createPanel(parent);

            this.toobar = $(`#${this.viewId}_toolbar`);

            this.detailPanel = $(`#${this.viewId}_panel`);

            super.render();

            this.renderDetailControls();
        }

        /**
         * 构造编辑面板控件
         */
        renderDetailControls() {
            let options = this.getDetailControls();
            if (!options)
                return;

            var container = this.detailPanel;
            var cols = options.cols ? options.cols : this.detail_cols;
            cols = Math.max(2, cols);
            this.detailForm = this.renderer.renderControls(container, options.controls, cols);
        }

        /**
         * 从表单的用户设置信息构造实体对象
         * @returns {Object} 实体
         */
        getEntity() {
            var data = this.detailForm.form('getData');
            return data;
        }

        /**
         * 新建
         * @abstract
         */
        create() {

        }

        /**
         * 验证
         * @returns {*}
         */
        validate() {
            return this.detailForm.form('validate');
        }

        /**
         * 预保存
         */
        preSave() {
            return this.validate();
        }


        /**
         * 保存
         */
        save() {        	
            if (!this.preSave())
                return;
            var entry = this.getEntity();
            this.loadingStart();
            if (!this.isNew)
                this.service.update(entry).then(c=>this.saved(c));
            else
                this.service.create(entry).then(c=>this.saved(c));
        }

        /**
         * 保存完毕
         */
        saved(result) {
            this.params = {id: result.id};
            this.loadingEnd();
            $.messager.show({
                title: '保存',
                msg: '保存成功!',
                timeout: 900,
                showType: 'fade',
                style: {
                    right: '',
                    bottom: ''
                }
            });
            return result;
        }

        /**
         * 预删除
         * @virtual
         */
        preDel() {

        }

        /**
         * 删除完毕
         * @virtual
         */
        deleted() {

        }

        /**
         * 删除
         * @abstract
         */
        delete() {
            throw new Error('未实现');
        }

        /**
         * 实体信息加载完毕回调
         * @virtual
         */
        loaded() {

        }

        /**
         * 加载实体信息
         */
        load() {
            if (this.params == null)
                return;
            if (!this.service)
                throw new Error("Service不能为空");

            var def = null;
            if (this.service.hasOwnProperty('findByParam'))
                def = this.service.findByParam.call(this, this.params);
            else
                def = this.service.getById.call(this.service, this.params);

            var self = this;
            def.then(d=> {
                self.entry = d;
                return d;
            }).then(d=>self.bind()).then(c=>self.loaded());
        }

        /**
         * 将实体信息绑定到表单
         */
        bind() {
            var entry = this.entry;
            $(this.detailForm).form('load', entry);
        }


    }

    /**
     * 可编辑列表
     */
    class EditListView extends ListView {
        /**
         *
         * @param viewId
         */
        constructor(viewId) {
            super(viewId);
            this.controls_cols = 2;
        }

        get isNew() {
            return this.data == null || this.data.rowIndex == -1;
        }

        /**
         * 获取后台服务实例
         * @abstract
         */
        get service() {
            throw new Error("子类必须实现该方法");
        }


        /**
         * 删除完毕
         */
        deleted() {

        }

        /**
         * 删除
         */
        delete() {
            let data = this.getSelectedRows();
            if (data == null || data.length == 0)
                return;
            var self = this;
            let key = this.primaryKey;
            $.messager.confirm('删除', '确认删除所选记录?',
                r=> {
                    if (r) {
                        var ids = data.map(i=>i[key]);
                        self.service.delete(ids).then(c=> {
                            self.search();
                        });
                    }
                }
            );
        }

        /**
         * 新增
         */
        create() {
            this.data = null;
            this.prefEditControl();
            this.showEditPanel();
            this.setDefaultValue();
            
        }
        
        prefEditControl(){
        	
        }

        /**
         * 编辑
         */
        edit() {
    		if (this.data == null || this.data.rowIndex < 0)
                return;
            this.showEditPanel();
        }

        /**
         * 执行保存
         * @returns @returns {Promise.<T>|*}
         */
        doSave() {
            let isNew = (this.data == null || this.data.rowIndex < 0 );
            var data = this.getDetail();
            var def;

            if (!this.validateDetail()) {
            	alert("请填写所有必填项");
            	return;
                def = $.Deferred();
                setTimeout(()=> def.reject(), 10)
                return def;
            }
            this.loadingStart();
            if (!isNew)
                def = this.service.update(data.rowData)
                    .then(this.saved);
            else
                def = this.service.create(data.rowData)
                    .then(this.saved);
            let self = this;
            def.fail(c=>this.loadingEnd());
            return def.done(c=>this.refreshRow(c));
        }

        /**
         * 更新datagrid row
         * @param data
         */
        refreshRow(data) {
        	
            $.extend(this.data.rowData, data);            
            if (this.data.rowIndex >= 0) {
                this.currentGrid.datagrid('updateRow', {index: this.data.rowIndex, row: this.data.rowData});
                this.search();
            }
            else {
                this.search();
            }
        };

        /**
         * 保存完毕事件
         * @returns {Promise.<T>|*}
         */
        saved(result) {
            $.messager.show({
                title: '保存',
                msg: result ? '保存成功!' : '保存失败!',
                timeout: 900,
                showType: 'fade',
                style: {
                    right: '',
                    bottom: ''
                }
            });
            if (!result) {
                this.reject(result);
                return;
            }
            return result;
        }

        /**
         * 验证明细
         * @returns {*}
         */
        validateDetail() {
            return this.detailForm.form('validate');
        }

        /**
         * 获取明细信息
         * @returns {null|{rowData, rowIndex}|*}
         */
        getDetail() {
            if (this.data == null)
                this.data = {rowIndex: -1, rowData: {}};
            $.extend(this.data.rowData, this.detailForm.form('getData'));
            return this.data;
        }

        /**
         * 获取当前grid的选择行
         * @returns {{rowData, rowIndex}}
         */
        getSelectedRow() {
            var row = this.currentGrid.datagrid('getSelected');
            var index = this.currentGrid.datagrid('getRowIndex', row);
            return {rowData: row, rowIndex: index};
            return this.data;
        }

        /**
         * 获取当前grid的所有选择行
         */
        getSelectedRows() {
            return this.currentGrid.datagrid('getSelections');
        }

        /**
         * 获取明细控件定义
         */
        getDetailControls() {
            throw new Error('未实现的方法.');
        }

        /**
         * 创建编辑面板
         */
        createEditPanel() {
            if (this.detailPanel != null)
                return;

            let options = this.detailControls = this.getDetailControls();
            if (!options)
                return;

            this.detailPanel = $(`<div id="view_${this.viewId}_panel_edit" class="easyui-dialog" data-options="closed:true" ></div>`).appendTo('body');
            var len = options.colcount;
            if (!len)
                len = 2;
            this.detailForm = this.renderer.renderControls(this.detailPanel, options.controls, len);
        }

        /**
         * 显示编辑面板
         */
        showEditPanel() {
            this.createEditPanel();

            let self = this;
            var title = (this.data && this.data.rowIndex >= 0) ? "编辑" : "新增";

            this.detailForm.form('clear');

            ygDialog({
                title: title,
                target: this.detailPanel,
                width: Math.max(600, this.detailControls.width),
                height: Math.max(280, this.detailControls.height),
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        self.doSave().done(c=>{
                        	$(self.detailPanel).dialog('close');
                        	self.loadingEnd();
                    	});
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        self.detailPanel.dialog('close');
                    }
                }]
            });
            if (this.data && this.data.rowData) {
                this.detailForm.form('load', this.data.rowData);
                
                this.sufEditControl();
            }
        }
        /**
         * 设置默认值
         */
        setDefaultValue(){
        	
        }
        
        sufEditControl(){
        	
        }

        /**
         * 获取网格定义
         * @param grid
         * @param options
         * @returns {*}
         */
        getGridOption(grid, options) {
            var op = super.getGridOption(grid, options);
            let self = this;
            op.onDblClickRow = function (rowIndex, rowData) {
                self.onGridDblClickRow(grid, rowIndex, rowData);
            };
            op.onSelect = function (rowIndex, rowData) {
                self.onGridSelectRow(grid, rowIndex, rowData);
            };
            let clickFn = op.onClickRow;
            op.onClickRow = function (rowIndex, rowData) {
                self.onGridClickRow(grid, rowIndex, rowData);
                if ($.isFunction(clickFn)) {
                    clickFn.call(grid, rowIndex, rowData);
                }
            };
            return op;
        }

        /**
         * 网格双击事件
         * @param grid
         * @param rowIndex
         * @param rowData
         */
        onGridDblClickRow(grid, rowIndex, rowData) {
            this.data = {rowIndex, rowData};
            this.currentGrid.datagrid('selectRow', rowIndex);
            this.edit();
        }

        /**
         * 网格单选事件
         * @param grid
         * @param rowIndex
         * @param rowData
         */
        onGridClickRow(grid, rowIndex, rowData) {
            this.data = {rowIndex, rowData};
        }

        /**
         *
         * @param {Jquery} grid 网格元素
         * @param {Integer} rowIndex  行索引
         * @param {Object} rowData 行数据
         */
        onGridSelectRow(grid, rowIndex, rowData) {
            this.data = {rowIndex, rowData};
        }
    }

    /**
     * 单据列表
     */
    class BillView extends EditListView {
        /**
         * @param viewId
         */
        constructor(viewId) {
            super(viewId);
            this.foot_cols = 4;
            this.curIndex = -1;
            this.gridId = 'view_grid_billGrid';
        }

        /**
         * 新增
         */
        create() {
            this.searchForm.form('clear');
            this.footerForm.form('clear');
            //
        }

        /**
         * 验证
         */
        validate() {

        }

        /**
         * 预保存
         * @returns {*}
         */
        preSave() {
            return this.validate();
        }

        /**
         * 保存页面数据
         * 查询面板
         * 明细
         */
        save() {
            if (!preSave())
                return;

        }

        /**
         * 删除完毕
         * @virtual
         */
        deleted() {

        }

        /**
         * 删除
         * @abstract
         */
//        delete() {
//            throw new Error('未实现');
//        }

        /**
         * 上一单
         */
        prev() {
            if (this.curIndex < 0) {
                $.messager.show({
                    title: '提示',
                    msg: '不存在上一单,请先查询!',
                    timeout: 900,
                    showType: 'fade',
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
                return;
            }
            this.go(this.curIndex - 1);
        }

        /**
         * 下一单
         */
        next() {
            if (this.curIndex < 0) {
                $.messager.show({
                    title: '提示',
                    msg: '不存在下一单,请先查询!',
                    timeout: 200,
                    showType: 'fade',
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
                return;
            }
            this.go(this.curIndex + 1);
        }

        /**
         * 上单下单公用方法
         * @param dataGridId
         * @param rowIndex
         */
        go(index) {
            var $dg = $(`#${this.gridId}`);
            var rows = $dg.datagrid('getRows');
            if (index > rows.length) {
                $.messager.show({
                    title: '提示',
                    msg: '已经是最后一条!',
                    timeout: 900,
                    showType: 'fade',
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
            } else if (index < 0) {
                $.messager.show({
                    title: '提示',
                    msg: '已经是第一条!',
                    timeout: 900,
                    showType: 'fade',
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
            } else {
                var rowData;
                if (rows) {
                    rowData = rows[index];
                }
                this.page.loadDtlPage(rowData, index);
            }
        }

        /**
         * 当前单据
         * @returns {*}
         */
        get currentBill() {
            return this._currentBill();
        }

        /**
         * 设置当前单据
         *
         */
        set currentBill(bill) {

        }

//      单据切换事件
        oncurrentBillChanged() {

        }

        /**
         * 判断单据状态
         */
        checkBillStatus() {
            return this.service.getStatus();
        }

        /**
         * 提交单据
         */
        submit() {
            if (!checkBillStatus())
                return;

        }

        /**
         * 审核
         */
        verify() {
            this.service.verify(bill).then(c=>this.verified(c));
        }

        /**
         * 审核完毕
         */
        verified(result) {
            $.messager.show({
                title: '审核',
                msg: result ? '审核成功!' : '审核失败!',
                timeout: 900,
                showType: 'fade',
                style: {
                    right: '',
                    bottom: ''
                }
            });
            return result;
        }

        /**
         * @param 构建页脚
         */
        renderFootPanel() {
            var viewId = this.viewId;
            let options = this.getFootControls();
            if (!options)
                return;
            var container = this.footer;
            var cols = options.cols ? options.cols : this.foot_cols;
            if (options.list)
                cols = options.list;
            else
                cols = Math.max(2, cols);
            this.footerForm = this.renderer.renderControls(container, options.controls, cols);
        }

        createPanel(container) {
            var viewId = this.viewId;
            var html = `
           <div easyui-layout id="${viewId}_layer">
               <div data-options="region:'north',border:false" style="height:32px;" >
                   <div id="${viewId}_toolbar"></div>
               </div>
               <div class="easyui-layout" data-options="region:'center'"
               			id="${viewId}_sublayer" >
                   <div data-options="region:'north',border:true">
                       <div class="search-div searcher" id="${viewId}_top_panel"></div>
                   </div>
                   <div data-options="region:'center',border:false" id="${viewId}_main_panel">
                   		
                   </div>
                   <div data-options="region:'south',border:false" id="${viewId}_foot_panel">
                   	
                   </div>
               </div>
           </div>`;
            return $(html).appendTo(container);
        }

        /**
         * 呈现视图
         */
        render(parent) {
            let self = this;
            var viewId = this.viewId;
            $(window).resize(function () {
                self.resize();
            });

            this.container = this.createPanel(parent);
            this.footer = $(`#${this.viewId}_foot_panel`);
            this.renderFootPanel();
            super.render();
            this.resize();
        }
    }

    module.exports = {Page, View, ListView, DetailView, EditListView, BillView};

});