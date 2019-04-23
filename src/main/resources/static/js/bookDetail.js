var dataForChild
    ,hello
    ,actionType;
layui.use(['layer','element','table','form','laydate'], function(){
    var $ = layui.jquery;
    var element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
        ,layer = layui.layer
        ,table = layui.table
        ,form = layui.form
        ,laydate=layui.laydate;

    // console.log(parent.actionType);
    if(parent.actionType=='detail'){
        $("form input").attr("readonly","readonly");
        $("form textarea").attr("readonly","readonly");
        $("button").addClass("layui-hide");
        // console.log(parent.dataForChild);
        MOD.Form.fillForm($('#bookDetail'),parent.dataForChild);
        form.render();
    }else if(parent.actionType=='add'){
        $("#IDinput").addClass("layui-hide");
    }else if(parent.actionType=='edit'){
        // console.log(parent.dataForChild);
        MOD.Form.fillForm($('#bookDetail'),parent.dataForChild);
        form.render();
    }
    //填写select
    $.ajax({
        url: basePath + "tushuxinxiguanli/listAllBookMsgIdAndName"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#bookName"), res.data, "bookMessageId", "bookMassageName");
            form.render()
        }
    });
    $.ajax({
        url: basePath + "data/listAllBookStatus"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#bookStatus"), res.data, "bookStatusId", "bookStatusName");
            form.render()
        }
    });
    $.ajax({
        url: basePath + "   "
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#"), res.data, "", "");
            form.render()
        }
    });
    form.on('submit(save)',function(data){//保存
        var myurl;
        if(parent.actionType=='edit')
        {
           //修改
            myurl = "churukuguanli/updateBook";
        }
        else if(parent.actionType=='add')
        {
            //添加
            myurl = "churukuguanli/addBook";
        }
        $.ajax({
            url:basePath+myurl
            , type: 'post'
            , data: data.field
            , success: function (res) {
                if (res.code === 0) {
                    layer.alert("操作成功！", function () {
                        parent.layui.table.reload('table2');
                        var myWindow = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(myWindow); //再执行关闭
                    });
                }
                else layer.alert(""+res.msg, {icon: 5});
            },
            error:function(jqXHR)
            {
                // console.log('请求错误,错误的原因为:'+jqXHR.status)
            }
        });
        return false;
    });

    //关闭按钮
    $('button[type=close]').click(function(){
        var mywindow = parent.layer.getFrameIndex(window.name);
        parent.layer.close(mywindow); //再执行关闭
    });

});