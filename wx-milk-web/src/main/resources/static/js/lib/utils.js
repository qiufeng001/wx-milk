"use strict";
!(function(){
    /**
	 * @param {String}
	 *            errorMessage 错误信息
	 * @param {String}
	 *            scriptURI 出错的文件
	 * @param {Long}
	 *            lineNumber 出错代码的行号
	 * @param {Long}
	 *            columnNumber 出错代码的列号
	 * @param {Object}
	 *            errorObj 错误的详细信息，Anything
	 */
    window.onerror = function(errorMessage, scriptURI, lineNumber,columnNumber,errorObj) {
    	var obj = {
    			errorMessage,scriptURI,lineNumber,columnNumber,errorObj
    	};
       console.log("错误信息：",obj);
    }
})();
/**
 * 查询数组中是否存在符合条件的元素
 * 
 * @param f
 * @returns {boolean}
 */
Array.prototype.any = function (f) {
    for (var v of this) {
        if (f(v))
            return true;
    }
    return false;
};
(function () {
    var env = APP_SETTINGS.env;
    var options = {
        beforeSend: function (xhr) {
            console.log(xhr);
        }
    };
    if (env && env.indexOf('dev') >= 0) {
        options.crossDomain = true;
        options.xhrFields = {withCredentials: true};
    }
    $.ajaxSetup(options);
})();
/**
 * "hello {name}!".format({name:"Jack"}) => hello Jack!;
 * 
 * @param data
 * @returns {string}
 */
String.prototype.format = function (data) {
    var args = arguments;
    return this.replace(/\{(\w+)(:\w+)?\}/g,
        function (m, name, f) {
            return data[name];
        });
};

(function(){
    if (!$.parser)
        return;
	var commonValidRule = {};
	/**
	 * 只能输入数字和英文字母
	 */
	commonValidRule.numberOrLetter = function(str) {
		var reg = /^[0-9a-zA-Z]+$/gi;
		if (reg.test(str)) {
			return true;
		}
		return false;
	};

	commonValidRule.isNumberOrLetter = {
		isNumberOrLetter : {
			validator : function(value, param) {
				for ( var i = 0; i < value.length; i++) {
					if (!commonValidRule.numberOrLetter(value[i])) {
						return false;
					}
				}
				return true;
			},
			message : '请输入数字或英文字母'
		}
	};
	$.extend($.fn.validatebox.defaults.rules, commonValidRule.isNumberOrLetter);
})();
$(function ($){
	if (!$.parser)
        return;
	$.extend($.fn.combobox.defaults, {
		onHidePanel : function() {
    	    var _options = $(this).combobox('options');  
    	    var _data = $(this).combobox('getData');/* 下拉框所有选项 */  
    	    var _value = $(this).combobox('getValue');/* 用户输入的值 */  
    	    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */  
    	    for (var i = 0; i < _data.length; i++) {  
    	        if (_data[i][_options.valueField] == _value) {  
    	            _b = true;  
    	            break;  
    	        }  
    	    }
    	    if(!_options.multiple){
	    	    if(!_b){  
	    	        $(this).combobox('setValue', ""); 
	    	        $(this).combobox('setText', "");
	    	    }
    	    }
	    }
	});
});
$(function () {
    class Hotkeys {
        constructor() {

        }

        enable() {
            this._enabled = true;
        }

        disable() {
            this._enabled = false;
        }

        bind(target, flag) {
            var _self = this;
            target.on("shown.bs.modal",function(e){
                $('input[tab-index="0"]', target).focus();
            });
            $(target).off('keydown').on('keydown', function (e) {
                if (!_self._enabled)
                    return;
                let tag = e.target.tagName;

                if (!(tag == "INPUT" || tag == "SEELCT" || tag == "TEXTAREA" || tag == "BUTTON" || tag == "A")) {
                    return;
                }
                var self = $(e.target)
                    , form = self.parents('form:eq(0)')
                    , focusable
                    , next
                    , prev
                    ;
                focusable = target.find('input,a,select,button,textarea').filter(':visible');
                next = focusable.eq(focusable.index($(e.target)) + 1);
                prev = focusable.eq(focusable.index($(e.target)) - 1);
                switch (e.keyCode) {
                    case 13:
                        if (!flag) {
                            if (tag == "INPUT") {
                                if (target.attr('id') == "giftSaleBox") {
                                    $('button[tab-index="1"]', target).focus();
                                    return;
                                }
                                $('button[tab-index="2"]', target).focus();
                                $('button[tab-index="2"]', target).click();
                            } else if (tag == "BUTTON" || tag == "A") {
                                $(e.target).click();
                            }
                        } else {
                            if (next.length) {
                                if (next.get(0).tagName == "BUTTON" || next.attr('type') == "button") {
                                    next.click();
                                } else
                                    next.focus();
                            } else {
                                // form.submit(); TODO:
                            }
                        }
                        break;
                    case 37:
                        if (prev.length) {
                            prev.focus();
                        }
                        break;
                    case 39:
                        if (next.length) {
                            next.focus();
                        }
                        break;
                   case 38:
                	   var id = $(e.target).attr('id');
                	   if ( id == "shiftNo" || id == "productStatus" || id=='saleType')
                		   return false;
                       if (prev.length) {
                           prev.focus();
                       }
                       break;
                   case 40:
                	   var id = $(e.target).attr('id');
                	   if ( id == "shiftNo" || id == "productStatus" || id=='saleType')
                		   return false;
                       if (next.length) {
                           next.focus();
                       }
                       break;
                }
            });
        }
    }
    window.Hotkeys = Hotkeys;
});
/*
 * async function excute(size, tasks) { var deff = $.Deferred(); var length =
 * tasks.length; var i = 0; var errors = []; var batch = 0; var results = [];
 * var active = 0; var fn = () => { next(); function next() {
 * console.log(batch++); var index = 0;
 * 
 * while (index < size && i < length) { i += 1; index += 1; tasks[i - 1]()
 * .then((d) => { results.push(d); active += 1; if (active === length)
 * errors.length === 0 ? deff.resolve(results) : deff.reject(errors); else if
 * (active % size == 0) next(); }) .fail(err => { errors.push(err); // if (i ===
 * length) deff.reject(errors); }); } } }; setTimeout(fn, 1); return deff; }
 * 
 * $.fn.excute = excute;
 */
(function () {
    APP_SETTINGS.isDev = APP_SETTINGS.env.indexOf(',dev') > -1;
    APP_SETTINGS.modules = ["mdm", "admin", "portal", "mps", "pos", "card", "mcs", "fas"];
    APP_SETTINGS.getUrl = function (module) {
        if (!APP_SETTINGS.isDev) {
            if (module == "admin" || module == "mdm" || module == "portal")
                return "//" + APP_SETTINGS.dns;
            else
                return "//" + APP_SETTINGS.dns + "/" + module;
        }
        if (module == "admin" || module == "mdm" || module == "portal")
            return "//" + APP_SETTINGS.dns;
        return "//" + module + "." + APP_SETTINGS.domain + "/" + module;
    };

    APP_SETTINGS.config = function (module) {
        let path = "/" + module + "/resources/js";
        let libPath = APP_SETTINGS.resourcesUrl + "/resources/js";
        let conf = { 
            "core": libPath + "/core",
            "lib": libPath + +"/lib",
            "security": libPath + "/security",
            "portal": libPath + "/portal"
        };

        for (var m of APP_SETTINGS.modules) {
            if (m == module)
                continue;
            conf[m] = APP_SETTINGS.getUrl(m);
            if (m == "admin" || m == "mdm" || m == "portal")
                conf[m] += "/resources/js/" + m;
        }
        return conf;
    }
})();

$(function ($) {
    if (!$.parser)
        return;
    for (var p of $.parser.plugins) {
        var plugin = $.fn[p];
        if (!plugin)
            continue;
        var options = plugin.defaults;
        if (!options)
            continue;
        // options.method = "get";
    }
});

$(function () {
    $.ajaxSetup({
        beforeSend: function (xhr) {
// xhr.setRequestHeader('ticket','true');
// var val = $.data(window, "token");
// if (val)
// xhr.setRequestHeader('X-timestamp', val);
        },
        complete: function (xhr, status) {
            if (this.type == "POST" || this.type == "DELETE"
                || this.type == "PUSH")
                $.data(window, "token", xhr.getResponseHeader('X-timestamp'));
        }
    });
});

/**
 * 返回数组中的一个符合条件的元素
 * 
 * @param f
 * @returns {*}
 */
Array.prototype.first = function (f) {
    for (var v of this) {
        if (f(v))
            return v;
    }
    return null;
};
// if (typeof NormalStatement == "undefined") {
class NormalStatement {
    constructor(name, nodeType, value) {
        this.name = name;
        this.nodeType = nodeType;
        this.value = value;
    }
}

class NodeStatement {
    constructor(left, nodeType, right) {
        this.left = left;
        this.nodeType = nodeType;
        this.right = right;
    }

    and(node) {
        return new NodeStatement(this, "AND", node);
    }

    or(node) {
        return new NodeStatement(this, "OR", node);
    }

    json() {
        return JSON.stringify(this);
    }
}
// }
var Q = {
    Equals: function (name, value) {
        return new NormalStatement(name, "=", value);
    },
    NotEquals: function (name, value) {
        return new NormalStatement(name, "!=", value);
    },
    Contains: function (name, value) {
        new NormalStatement(name, "like", "%" + value + "%");
    },
    StartWith: function (name, value) {
        return new NormalStatement(name, "like", value + "%");
    },
    EndWith: function (name, value) {
        return new NormalStatement(name, "like", "%" + value);
    },
    LessThen: function (name, value) {
        return new NormalStatement(name, "<", value);
    },
    GreatThen: function (name, value) {
        return new NormalStatement(name, ">", value);
    },
    LessThenEquals: function (name, value) {
        return new NormalStatement(name, "<=", value);
    },
    GreatThenEquals: function (name, value) {
        return new NormalStatement(name, ">=", value);
    },
    Between: function (name, val1, val2) {
        return new NormalStatement(name, "between", [val1, val2]);
    },
    And: function (left, rigth) {
        return new NodeStatement(left, "AND", rigth);
    },
    Or: function (left, rigth) {
        return new NodeStatement(left, "OR", rigth);
    }
};


if (_) {
    _.each(['each',
        'map',
        'reduce',
        'reduceRight',
        'find',
        'filter',
        'where',
        'findWhere',
        'reject',
        'every',
        'some',
        'contains',
        'invoke',
        'pluck',
        'max',
        'min',
        'sortBy',
        'groupBy',
        'indexBy',
        'countBy',
        'shuffle',
        'sample',
        'toArray',
        'size',
        'partition',
        'initial',
        'last',
        'rest',
        'compact',
        'flatten',
        'without',
        'union',
        'intersection',
        'difference',
        'uniq',
        'zip',
        'object',
        'indexOf',
        'lastIndexOf',
        'sortedIndex',
        'range'], function (p) {
        Array.prototype[p] = function (...args) {
            var params = _.union([this], args);
            return _[p].apply(this, params);
        }
    });
}


/**
 * 
 * @param format
 * @returns {*}
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minutePersonalController
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds() // millisecond
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

/**
 * 
 * @param obj
 * @param prop
 * @param desc
 * @returns {*}
 */
function defineProperty(obj, prop, desc) {
    if ('get' in desc) {
        obj.__defineGetter__(prop, desc.get)
    }
    if ('set' in desc) {
        obj.__defineSetter__(prop, desc.set)
    }

    if ('value' in desc) {
        obj[prop] = desc.value
    }
    return obj
};

/**
 * 
 * @param obj
 * @param descs
 * @returns {*}
 */
function defineProperties(obj, descs) {
    for (var prop in descs) {
        if (descs.hasOwnProperty(prop)) {
            defineProperty(obj, prop, descs[prop])
        }
    }
    return obj
};

function addCookie(name, value, expires, path, domain) {
    var str = name + "=" + escape(value);
    if (expires != "") {
        var date = new Date();
        date.setTime(date.getTime() + expires * 24 * 3600 * 1000);// expires单位为天
        str += ";expires=" + date.toGMTString();
    }
    if (path != "") {
        str += ";path=" + path;// 指定可访问cookie的目录
    }
    if (domain != "") {
        str += ";domain=" + domain;// 指定可访问cookie的域
    }
    document.cookie = str;
}

function getCookie(name) {
    if (document.cookie.length > 0) {
        var start = document.cookie.indexOf(name + "=");
        if (start != -1) {
            start = start + name.length + 1;
            var end = document.cookie.indexOf(";", start);
            if (end == -1)
                end = document.cookie.length;
            return unescape(document.cookie.substring(start, end));
        }
    }
    return ""
}

/** ES 7 suport 升级浏览器到chrome 5.5 * */
/*
 * async function sleep(timeout) { return new Promise((resolve, reject) => {
 * setTimeout(function() { resolve(); }, timeout); }); }
 */

Promise.new = function (callback) {
    var promise = new Promise(function (resolve, reject) {
        setTimeout(function () {
            try {
                var data = callback();
                resolve(data);
            } catch (e) {
                reject(e);
            }
        }, 5);
    });
};

/**
 * data为数组时，提交必须使用该方法
 * 
 * @param url
 * @param data
 * @returns {*}
 */
$.xpost = function (url, data) {
    return $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(data)
    });
};

/**
 * 同步ajax请求
 * 
 * @param url
 * @param params
 * @param type
 * @param cached
 * @returns {*}
 */
$.ajaxSync = function (url, params = null, type = 'GET', cached = true) {
    var data = null;
    $.ajax({
        url: url,
        type: type,
        cache: cached,
        data: params,
        async: false
    }).then(d=>data = d);

    return data;
};

$(function ($) {
    $.tryInvoke = function (fn, obj, ...args) {
        if ($.isFunction(fn)) {
            return fn.apply(obj, args);
        }
        return null;
    };
    /**
	 * 自定义插件构造器
	 * 
	 * @param name
	 *            插件名称
	 * @param bind
	 *            构造器回调函数
	 * @param parents
	 *            指定从那个组件继承，如果有继承链需要指定多个,按照继承关系从高到低， 例如：$.fn.plugin("lookup",
	 *            bind,"combo","combobox");
	 */
    $.fn.plugin = function (name, bind, ...parents) {
        $.parser.plugins.push(name); // 插入easyui呈现插件列表
        $.fn.form.explugins.push(name);// 插入form呈现插件列表,form clear/getdata
        // 方法依赖插件列表
        return (function (name, bind, parents) {
            var fn = function (options, params) {
                if (typeof options == 'string') {
                    if ($.fn[name].methods)
                        return $.fn[name].methods[options](this, params);
                }
                options = options || {};
                return this.each(function () {
                    var state = $.data(this, name);
                    if (state) {
                        $.extend(state.options, options);
                    } else {
                        var op = {};
                        if ($.isFunction($.fn[name].parseOptions))
                            op = $.fn[name].parseOptions(this);

                        options = $.extend({}, $.fn[name].defaults, options, op);
                        $.data(this, name, {
                            options: options
                        });
                    }
                    bind(this, options);
                });
            };
            var pm = {};
            if (parents) {
                for (var p of parents)
                    pm = $.extend(pm, $.fn[p].methods);
            }
            fn.methods = $.extend(pm, {
                options: function (jq) {
                    return $.data(jq[0], name).options;
                }
            });

            $.fn[name] = fn;

            /**
			 * 生成datagrid插件
			 * 
			 * @type {{init: init, destroy: destroy, getValue: getValue,
			 *       setValue: setValue, resize: resize}}
			 */
            $.fn.datagrid.defaults.editors[name] = {
                init: function (container, options) {
                    var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
                    input[name]();
                    return input;
                },
                destroy: function (target) {
                    $(target).remove();
                },
                getValue: function (target) {
                    return $(target)[name]('getValue');
                },
                setValue: function (target, value) {
                    $(target)[name]('setValue', value);
                },
                resize: function (target, width) {
                    $(target)[name]('resize', width);
                }
            };
        })(name, bind, parents);
    }
});

$(function ($) {
    let name = "xcombobox"
    if (!$.parser)
        return;
    $.parser.plugins.push(name); // 插入easyui呈现插件列表
    $.fn.form.explugins.push(name);

    $.fn.plugin(name, bind, "combo", "combobox");

    function bind(target, options) {
        if (options.parent) {
            var $parent = $('#' + options.parent);
            options._url = options.url;
            delete options.url;
            var parentOptions = $parent.xcombobox('options');
            var _onChange = parentOptions.onChange;
            $parent.combobox({
                onChange: function (n, o) {
                    var val = {parent: n};
                    var url = options._url.format(val);
                    $(target).combobox('reload', url);
                    if ($.isFunction(_onChange))
                        _onChange.call($parent, n, o);
                }
            });

            options.onLoadSuccess = function (data) {
                var options = $(this).combobox('options');
                var val = $(this).combobox('getValue');
                var item = null;
                if (data) {
                    item = data.find(c=>c[options.valueField] == val);
                }
                if (!item)
                    $(target).combobox('clear');
            };
        }

        $(target).combobox(options);
    }

    function _disable(jq) {
        $(jq).attr("readonly", true).combobox("disable");
    }

    function _enable(jq) {
        $(jq).attr("readonly", true).combobox("enable");
    }

});
/**
 * checkboxlist
 */
$(function () {
    let name = "checkboxlist"
    if (!$.parser)
        return;
    $.parser.plugins.push(name); // 插入easyui呈现插件列表
    $.fn.form.explugins.push(name);

    $.fn[name] = function (options, params) {
        if (typeof options == 'string') {
            if ($.fn[name].methods)
                return $.fn[name].methods[options](this, params);
        }
        options = options || {
                multiple: true,
                width: "100px"
            };
    };

    $.fn[name].methods = {
        getValue: function (target) {

        },
        setValue: function (target, values) {

        },
        load: function (target, values) {

        },
        reset: function (target, values) {

        },
        clear: function (target, values) {

        }
    }
});

$(function () {
    var cache = {};

    /**
	 * datagrid column formatter生成器,通过扫描column的$formatter属性
	 * 来构造column的formatter函数
	 * 
	 * @param url
	 *            数据源地址
	 * @param options
	 *            参数设置:valueField,textField
	 */
    function columnFormat(url, options) {
        if (!url)
            throw new Error('必须先制定数据源地址.');
        if (_.isArray(options)) {
            $.each(options, (k, op)=> {
                dealOptions(op)
            });
        } else {
            dealOptions(options);
        }

        function dealOptions(options) {
            var columns = options.columns; // frozenColumns
            $.each(columns, (i, cols)=> {
                $.each(cols, (j, c)=> {
                    if (!c.$formatter || c.formatter)
                        return;
                    c.formatter = getFormatter(c.$formatter);
                });
            });
            if (options.frozenColumns)
                $.each(options.frozenColumns, (i, cols)=> {
                    $.each(cols, (j, c)=> {
                        if (!c.$formatter || c.formatter)
                            return;
                        c.formatter = getFormatter(c.$formatter);
                    });
                });
        }

        function convert(data, options) {
            var valueField = options.valueField;
            // fas数据库里面存的是costNo,因此需要在这里特殊转化
            if(valueField=='costNo'){
            	valueField='deductionNo';
            }
            // mdm_property_company 这个在业务系统用的是mallNo,在MDM用的是companyNo
            if(valueField=='mallNo'){
            	valueField='companyNo';
            }
            var item = {};
            $.each(data, (i, d)=> {
                item[d[valueField]] = d;
            });
            return item;
        }

        /**
		 * 同步获取数据
		 * 
		 * @param options
		 *            ajax标准参数
		 * @returns {*}
		 */
        function getData(options) {
            var type = options.type;
            var params = options.params;
            var uri = options.url;
            if (!uri)
                uri = ( url + "/" + type + "/query" );

            var key = type + "_" + (params ? JSON.stringify(params) : "");
            if (cache[key] == null) {
                $.ajax({
                    url: uri,
                    type: 'GET',
                    cache: true,
                    data: params,
                    async: false
                }).then(c=> {
                    cache[key] = convert(c, options);
                });
            }
            return cache[key];
        }

        /**
		 * 生成column formatter
		 * 
		 * @param options
		 * @returns {Function}
		 */
        function getFormatter(options) {
            if (!options.type)
                throw new Error('必须指定$formatter的type');

            var data = getData(options);

            return (value, row, index)=> {
                if (!value)
                    value = row[options.valueField];
                if (!value)
                    return "";
                if (data[value] && data[value][options.textField]) {
                    if (options.textField.indexOf('}') > 0)
                        return options.textField.format(data[value]);
                    else
                        return data[value][options.textField];
                } else {
                    return value;
                }

            }
        }
    }

    $.gridFormat = columnFormat;
    if ($.fn.datagrid) {
        $.extend($.fn.datagrid.methods, {
            addEditor: function (jq, param) { // 给指定列增加编辑器 eg:
                // $('grid').datagrid('addEditor',{field:'name,gender',editor:'textbox')
                var array = param.field.split(',');
                for (var i = 0; i < array.length; i++) {
                    var temp = array[i];
                    var e = $(jq).datagrid('getColumnOption', temp);
                    e.editor = param.editor;
                }
            },
            removeEditor: function (jq, param) {// 移除列编辑器 eg:
                // $('grid').datagrid('addEditor',{field:'name,gender')
                var params = param.split(",");
                if (params instanceof Array) {
                    $.each(params, function (index, item) {
                        var e = $(jq).datagrid('getColumnOption', item);
                        e.editor = {};
                    });
                } else {
                    var e = $(jq).datagrid('getColumnOption', param);
                    e.editor = {};
                }
            }
        });
    }

});

/** base controls * */
/** form * */
(function ($) {
    function isNotBlank(val) {
        return val ? true : false;
    }

    /**
	 * submit the form
	 */
    function ajaxSubmit(target, options) {
        options = options || {};
        var param = {};
        if (options.onSubmit) {
            if (options.onSubmit.call(target, param) == false) {
                return;

            }
        }
        var form = $(target);
        if (options.url) {
            form.attr('action', options.url);
        }
        var frameId = 'easyui_frame_' + (sysDateReal.getTime());
        var frame = $(
            '<iframe id=' + frameId + ' name=' + frameId + '></iframe>')
            .attr(
                'src',
                window.ActiveXObject ? 'javascript:false'
                    : 'about:blank').css({
                position: 'absolute',
                top: -1000,
                left: -1000
            });
        var t = form.attr('target'), a = form.attr('action');
        form.attr('target', frameId);
        var paramFields = $();
        try {
            frame.appendTo('body');
            frame.bind('load', cb);
            for (var n in param) {
                var f = $('<input type="hidden" name="' + n + '">').val(
                    param[n]).appendTo(form);
                paramFields = paramFields.add(f);
            }
            checkState();
            form[0].submit();
        } finally {
            form.attr('action', a);
            t ? form.attr('target', t) : form.removeAttr('target');
            paramFields.remove();
        }
        function checkState() {
            var f = $('#' + frameId);
            if (!f.length) {
                return
            }
            try {
                var s = f.contents()[0].readyState;
                if (s && s.toLowerCase() == 'uninitialized') {
                    setTimeout(checkState, 100);
                }
            } catch (e) {
                cb();
            }
        }

        var checkCount = 10;

        function cb() {
            var frame = $('#' + frameId);
            if (!frame.length) {
                return
            }
            frame.unbind();
            var data = '';
            try {
                var body = frame.contents().find('body');
                data = body.html();
                if (data == '') {
                    if (--checkCount) {
                        setTimeout(cb, 100);
                        return;
                    }
                    // return;
                }
                var ta = body.find('>textarea');
                if (ta.length) {
                    data = ta.val();
                } else {
                    var pre = body.find('>pre');
                    if (pre.length) {
                        data = pre.html();
                    }
                }
            } catch (e) {

            }
            if (options.success) {
                options.success(data);
            }
            setTimeout(function () {
                frame.unbind();
                frame.remove();
            }, 100);
        }
    }

    function post(target, options) {
        options = options || {};
        var param = {};
        var form = $(target);
        var url = options.url;
        var data1 = {};
        $.each($('input,textarea,select', form), function (i, input) {
            var name = $(input).attr('name');
            if (name)
                data1[name] = $(input).val();
        });
        var data2 = _getCombo(target);
        param = $.extend(param, data1, data2);
        if (options.onSubmit) {
            param = options.onSubmit.call(target, param);
        }
        var def = $.Deferred();
        $.post(url, param).success(function (data, state, xhr) {
            if (data) {
                if (isNotBlank(data.errorMessage)) {
                    showError(data.errorMessage + " " + data.errorDefined);
                    def.reject();
                } else {
                    if (data.hasOwnProperty('errorCode')) {
                        def.reject();
                    } else {
                        def.resolve(data);
                    }
                }
            } else {
                showError('操作失败,请联系管理员!');
                def.reject();
            }
        }).error(function () {
            def.reject();
        });
        return def;
    }

    function _getCombo(target) {
        var form = $(target);
        var cc = $.fn.form.plugins;
        var data = {};
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('getValue')) {
                var editors = form.find(".easyui-" + name);
                if (editors != undefined && editors.length > 0) {
                    $.each(editors, function (i, jq) {
                        var editor = $(jq);
                        var field = editor.attr('name') || editor.attr('comboName');
                        data[field] = editor[name]('getValue');
                    });
                }
            }
        });

        for (var i = 0; i < cc.length; i++) {
            var type = cc[i];
            var cmbs = form.find("." + type + '-f');
            $.each(cmbs, function (i, e) {
                var c = $(e);
                var name = c.attr('comboName');
                if (!data.hasOwnProperty(name)) {
                    var val = null;
                    if (c[type]('options').multiple) {
                        val = c[type]('getValues');
                    } else {
                        val = c[type]('getValue');
                    }
                    data[name] = val;
                }
            });
        }
        return data;
    }

    // daixiaowei
    function _disable(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('disable')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('disable');// 禁用控件
                }
            }
        });
    }

    function _enable(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('enable')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('enable');// 启用控件
                }
            }
        });
    }

    // 设置默认值 取权限数据
    function _setDefaultValue(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('setDefaultValue')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('setDefaultValue');// 设置默认值 取权限数据
                }
            }
        });
    }

    /**
	 * load form data if data is a URL string type load from remote site,
	 * otherwise load from local data object.
	 */
    function load(target, data) {
        if (!$.data(target, 'form')) {
            $.data(target, 'form', {
                options: $.extend({}, $.fn.form.defaults)
            });
        }
        var opts = $.data(target, 'form').options;
        if (typeof data == 'string' && data !== "") {
            var param = {};
            if (opts.onBeforeLoad.call(target, param) == false)
                return;
            var def = $.Deferred();
            $.ajax({
                url: data,
                data: param,
                dataType: 'json',
                success: function (data) {
                    _load(data);
                    def.resolve(data);
                },
                error: function () {
                    opts.onLoadError.apply(target, arguments);
                    def.reject();
                }
            });
            return def;
        } else {
            _load(data);
        }

        function _load(data) {
            var form = $(target);
            for (var name in data) {
                var val = data[name];
                var rr = _checkField(name, val);
                // 方法提前，防止直接load input, element造成combo不触发onchange事件
                if (!_loadCombo(name, val)) {
                    if (!rr.length) {
                        var count = _loadOther(name, val);
                        if (!count) {
                            $('input[name="' + name + '"]', form).val(val);
                            $('textarea[name="' + name + '"]', form).val(val);
                            $('select[name="' + name + '"]', form).val(val);
                        }
                    }
                }
            }
            opts.onLoadSuccess.call(target, data);
            validate(target);
        }

        /**
		 * check the checkbox and radio fields
		 */
        function _checkField(name, val) {
        	var rr = $(target).find('input[value="' + val + '"][name="' + name + '"][type=radio], input[name="' + name + '"][type=checkbox]');
            rr._propAttr('checked', false);
            rr.each(function () {
                var f = $(this);
                if (f.val() == String(val) || $.inArray(f.val(), $.isArray(val) ? val : [val]) >= 0) {
                    f._propAttr('checked', true);
                }
            });
            rr.val(val);
            return rr;
        }

        function _loadOther(name, val) {
            var count = 0;
            var pp = ['numberbox', 'slider', 'textbox'];
            for (var i = 0; i < pp.length; i++) {
                var p = pp[i];
                var f = $(target).find('input[' + p + 'Name="' + name + '"]');
                if (f.length) {
                    f[p]('setValue', val);
                    count += f.length;
                }
            }
            return count;
        }

        function _loadExtend(name, val) {
            var result = false;
            $.each($.fn.form.explugins, function (i, type) {
                var plug = $.fn[type];
                var form = $(target);
                if (!result && plug.methods.hasOwnProperty('setValue')) {
                    var editor = form.find(".easyui-" + type);
                    if (editor != undefined && editor.length > 0) {
                        var options = editor[type]('options');
                        if (isNotBlank(options.isNormarl)) {
                            result = false;
                        } else {
                            var field = options.valueFeild;
                            if (isNotBlank(options.inputValueFeild)) {
                                field = options.inputValueFeild;
                            }
                            if (field == name) {
                                editor[type]('setValue', val);
                                result = true;
                            }
                        }
                    }
                }
            });
            return result;
        }

        function _loadCombo(name, val) {
            var result = false;
            if (_loadExtend(name, val)) {
                result = true;
            } else {
                var form = $(target);
                var cc = $.fn.form.plugins;
                var c = form.find('[comboName="' + name + '"]');
                if (c.length) {
                    for (var i = 0; i < cc.length; i++) {
                        var type = cc[i];
                        if (c.hasClass(type + '-f')) {
                            if (c[type]('options').multiple) {
                                var vals = val;
                                if (val == null)
                                    vals = [];
                                else if (typeof val == "string") vals = val.split(',');

                                c[type]('setValues', vals);
                            } else {
                                c[type]('setValue', val);
                            }
                            result = true;
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
	 * clear the form fields
	 */
    function clear(target) {
        /** 将该段代码执行提前，防止直接clear input element造成combo不触发onchange事件 * */
        var t = $(target);
        var plugins = ['textbox', 'combo', 'combobox', 'combotree', 'combogrid', 'slider', 'managercity', 'bizCity'];
        for (var i = 0; i < plugins.length; i++) {
            var plugin = plugins[i];
            var r = t.find('.' + plugin + '-f');
            if (r.length && r[plugin]) {
                r[plugin]('clear');
            }
        }
        // validate(target);错误的触发combobox层弹出
        /** -------------------------------end ----------------------* */
        $('input,select,textarea', target).each(
            function () {
                var t = this.type, tag = this.tagName.toLowerCase();
                if (t == 'text' || t == 'hidden' || t == 'password'
                    || tag == 'textarea') {
                    if (this.className != 'disableClear') {
                        this.value = '';
                    }
                } else if (t == 'file') {
                    var file = $(this);
                    var newfile = file.clone().val('');
                    newfile.insertAfter(file);
                    if (file.data('validatebox')) {
                        file.validatebox('destroy');
                        newfile.validatebox();
                    } else {
                        file.remove();
                    }
                } else if (t == 'checkbox' || t == 'radio') {
                    this.checked = false;
                } else if (tag == 'select') {
                    this.selectedIndex = -1;
                }
            });

        // 清空查询精灵
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('clear')) {
                var editor = t.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('clear');// 清空控件
                }
            }
        });
    }

    /**
	 * 清空控件QueryParams
	 */
    function clearQueryParams(target) {
        var t = $(target);
        // 清空查询精灵
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('clearQueryParams')) {
                var editor = t.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('clearQueryParams');// 清空控件QueryParams
                }
            }
        });
    }

    function reset(target) {
        target.reset();
        var t = $(target);
        var plugins = ['combo', 'combobox', 'combotree', 'combogrid',
            'datebox', 'datetimebox', 'spinner', 'timespinner',
            'numberbox', 'numberspinner', 'slider', 'managercity', 'bizCity'];
        for (var i = 0; i < plugins.length; i++) {
            var plugin = plugins[i];
            var r = t.find('.' + plugin + '-f');
            if (r.length && r[plugin]) {
                r[plugin]('reset');
            }
        }
        validate(target);
    }

    /**
	 * set the form to make it can submit with ajax.
	 */
    function setForm(target) {
        var options = $.data(target, 'form').options;
        var form = $(target);
        form.unbind('.form').bind('submit.form', function () {
            setTimeout(function () {
                ajaxSubmit(target, options);
            }, 0);
            return false;
        });
    }

    function validate(target) {
        if ($.fn.validatebox) {
            var t = $(target);
            t.find('.validatebox-text:not(:disabled)').validatebox('validate');
            var invalidbox = t.find('.validatebox-invalid');
            invalidbox.filter(':not(:disabled):first').focus();
            return invalidbox.length == 0;
        }
        return true;
    }

    function setValidation(target, novalidate) {
        $(target).find('.validatebox-text:not(:disabled)').validatebox(
            novalidate ? 'disableValidation' : 'enableValidation');
    }

    $.fn.form = $.fn.form || {methods: {}};

    $.fn.form.methods.load = function (jq, data) {
        return load(jq[0], data);
    };

    $.fn.form.methods.getData = function (jq, data) {
        var data1 = {};
        $.each($('input,textarea,select', jq), function (i, input) {
            var name = $(input).attr('name');
            var type = $(input).attr('type');
            if (!(type == "radio") && !(type == "checkbox") && name) {
                data1[name] = $(input).val();
            } else if ((type == "radio") && name) {
                if (input.checked == true)
                    data1[name] = $(input).val();
            } else if ((type == "checkbox") && name) {
                if (input.checked == true)
                    data1[name] = 1;
                else
                    data1[name] = 0;
            }
        });
        var data2 = _getCombo(jq);
        return $.extend({}, data1, data2);
    };

    $.fn.form.methods.clear = function (jq) {
        return jq.each(function () {
            clear(this);
        });
    };

    $.fn.form.methods.clearQueryParams = function (jq) {
        return jq.each(function () {
            clearQueryParams(this);
        });
    };

    $.fn.form.methods.reset = function (jq) {
        return jq.each(function () {
            reset(this);
        });
    };

    $.fn.form.methods.post = function (jq, param) {
        return post(jq[0], param);
    };

    $.fn.form.methods.getCombo = function (jq) {
        return _getCombo(jq[0]);
    };

    $.fn.form.methods.disable = function (jq) {
        return _disable(jq[0]);
    };

    $.fn.form.methods.enable = function (jq) {
        return _enable(jq[0]);
    };

    $.fn.form.methods.setDefaultValue = function (jq, data) {
        _setDefaultValue(jq[0], data);
    };

    $.fn.form.explugins = [];

    $.fn.form.plugins = ['combobox', 'combotree', 'combogrid', 'datetimebox',
        'datebox', 'combo'];
})(jQuery);

$(function () {
    var template = `<div>
          <div class="excel-import" id="e-' + self.id + '" title="导入" >
                <div style="padding:10px">
                    <div>导入:</div>
                        <input type="file" class="inputfile" name="file2" data-options="prompt:\'选择导入文件...\',buttonText:\'浏览\'"
                            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" style="width:480px"/>'
                </div>
            <div class="progress">
                <div style="height: 24px;"><span class="timer"></span><span class="excel-info"></span></div>
                <div class="easyui-progressbar excel-p" style="width:480px;"></div>
            </div>
            <div class="label"><span>错误信息:</span></div>
            <div class="excel-output" >
                <ul class="excel-out">
                    <li class="error">注意：Excel中列的名称不能修改必须和模板一致.</li>
                </ul>
            </div>
           <div class="excel-buttons" >
               <a href="javascript:void(0)" class="easyui-linkbutton excel-ok" style="margin: 2px">导入</a>
               <a href="javascript:void(0)" class="easyui-linkbutton excel-tmpl" style="margin: 2px">下载模板</a>
               <a href="javascript:void(0)" class="easyui-linkbutton excel-cancel"  style="margin: 2px">关闭</a>
           </div>
        </div>
       </div>'`;

    function bind(target, options) {
        var panel = $(template).appendTo($(target));
        var prg = $('.excel-p', p);
        $.data(target, "options", options);
        $('.excel-cancel', p).bind('click', function () {
            panel.window('close');
            if ($.isFunction(options.onClose)) {
                options.onClose.call(target);
            }
        });
        $('.excel-ok', p).bind('click', function () {
            var options = $.data(target, "options");
            if (!options.status || options.status == 0) {
                var files = $('input[type=file]', panel)[0].files;
                if (files.length == 0) {
                    $.messager.alert('警告', '请选择一个导入文件!');
                    return;
                }
                if ($.isFunction(options.onChange)) {
                    options.onChange.call(target, files);
                }
            }
            else {
                if ($.isFunction(options.onStop)) {
                    options.onStop.call(target);
                }
            }
        });
        $('.excel-tmpl', p).bind('click', function () {
            window.location.href = options.downloadUrl + '/download?fileName=' + options.templateName;
        });

        options.timer = new Timer($('.timer', panel));
        $.parser.parse(p);
    }

    function start(target) {
        $('.excel-ok', $(target)).linkbutton({text: '停止'});
        $('.excel-cancel', $(target)).linkbutton('disable');
        var options = $.data(target, "options");
        options.timer.start();
    }

    $.fn.importPanel = function (options, params) {
        if (typeof options == 'string') {
            if ($.fn["importPanel"].methods)
                return $.fn["importPanel"].methods[options](this, params);
        }
        bind(this, options);
    };

    $.fn.importPanel.methods = {
        open: function () {
            $('.excel-import', $(target)).window('open');
        },
        start: function () {
            start(this);
        },
        stop: function () {
            var options = $.data(this, "options");
            options.timer.stop();
            $('.excel-import', $(target)).window('close');

        },
        options: function () {
            return $.data(this, "options");
        }
        , info: function (msg) {
            $('.excel-info', $(this)).text(msg);
        },
        process: function (index, count) {
            var value = parseInt((index / count) * 100);
            $('.excel-p', $(this)).progressbar('setValue', value);
        },
        error: function (data, formatter) {
            var $ul = $('.excel-out', $(this));
            $ul.empty();
            data.each((v)=> {
                    var txt = v;
                    if ($.isFunction(formatter))
                        txt = formatter(v);
                    $ul.append($(`<li>${txt}</li>`));
                }
            );
        }
    };

    class Timer {
        constructor(elment) {
            this.$e = $(elment);
            this.now = new Date();
            this.interval = null;
        }

        start() {
            this.now = new Date();
            this.interval = window.setInterval(function () {
                $(this.$e).text(formatTime(new Date() - now));
            }, 1000);
            return this;
        };

        stop() {
            window.clearInterval(this.interval);
        };
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

});