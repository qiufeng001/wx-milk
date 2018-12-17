(function () {
     
    function globalEval(content) {
        if (content && /\S/.test(content)) {
            /*eval() 与 window.execScript() 都是让提供的某一段字符串脚本运行
             1. 部分浏览器下 window.execScript() 函数不能运行，而 eval() 在一般浏览器下都能正常运行；
             2. window.execScript() 所执行后的脚本上文是针对整个全局域的，而 eval() 对字符串脚本执行的上下文只针对于调用 eval 函数本身的上下文；*/
            (window.execScript || function (content) {
                (window.eval || eval).call(window, content)
            })(content)
        }
    }

    function compile(id, name) {
        var js = `define('./${id}',function(require){
                    return require('${name}');
                });`;

        globalEval(js);
    }

    class Directive {
        constructor(views) {
            this._views = views;
            this.parse();
        }

        get views() {
            return this._views
        }

        render(v) {
            var $view = $('#' + v.id);
            var $e = $(v.t);//获取view对应组件的html
            v.$e = $e;            
            $e.insertBefore($view);//将组件对应的html插入到view元素之前
            $view.remove();//移除view元素
            $.parser.parse($e.parent());
        }

        parse() {
            for (var v of this.views) {
                this.render(v);
            }
        }

        apply(page) {
            for (var v of this.views) {
                let view =  new v.V(v.$e);
                page[v.id] = view;
                view.page = page;
            }
        }

    }
    window.Directive = Directive;

    function parse(name, $jq) {
        var $views = $("view", $jq);
        var ids = [];
        $views.each((i, v)=> {
            var $v = $(v);
            var $pre = $v.prev();
            var id = $v.attr('id');
            var name = $v.attr('name');
            var tmpl = name.replace(/(\.view)/, "") + ".tpl";

            ids.push(`{id:'${id}',V:require('./${name}'),t:require('./${tmpl}')}`);
        });
        //define('order/views', function (require, exports, module) {
        //    var views = {loginView: require('./login.view'),infoView:require('./info.view')};
        //     module.exports =  new Directive(views);
        //});


        var base = `define('${name}',function(require, exports, module){
                        var views = [${ids.join(',')}];
                        module.exports =  new Directive(views);
                    });`;

        globalEval(base);
    }

    //parse("order/views", $(document));

    $.fn.parseView = function (name) {
        var $e = $(this);
        parse(name, $e);
    };
})();