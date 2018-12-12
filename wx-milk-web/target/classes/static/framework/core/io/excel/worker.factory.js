define(function (require, exports, module) {
    function Factory(scriptURL, poolsize, callback) {
        var self = this;

        function create() {
            var tasks = [];
            for (var i = 0; i < poolsize; i++) {
                var worker = new Worker(scriptURL);
                tasks.push(worker);
            }
            return tasks;
        }

        this.tasks = create();
        var i =0;
        this.post = function (getData) {
            var defs = [];
            self.tasks.map(function (w, index) {
                var def = $.Deferred();
                w.addEventListener('message', function (e) {
                    console.log(i++);
                    def.resolve(e.data);
                });
                defs.push(def);
            });
            self.tasks.map(function (w, index) {
                var data = getData(index);
                w.postMessage(data);
            });
            return $.when.apply($,defs);
        };

        this.terminate = function () {
            self.tasks.map(function (w, index) {
                w.terminate();
            });
        };
    }
    module.exports  = Factory;
});

    //function createworker(file){
    //    var def = $.Deferred();
    //    var worker = new Worker(file);
    //
    //}
