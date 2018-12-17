(function ($) {
    // 当domReady的时候开始初始化
    $(function () {
        btnclick()
    });

    window.onload=function(){

        $("#file-upload");
    }

    function btnclick() {
        $("#btnclick").click(function () {
            $.ajax({
                url: '/file/filePath',
                page:1,
                pageSize:10,
                type: 'GET',
                success: function (data) {


                    $('#file-Model').modal({ show: true, backdrop: 'static' });
                    $("#fileId").text(data[0].fileId);
                }
            });

        });
    }
})(jQuery);