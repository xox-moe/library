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

    //填写select
    $.ajax({
        url: basePath + "tushuxinxiguanli/listAllBookMsgIdAndName"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#bookName"), res.data, "bookMessageId", "bookMassageName");
            if(parent.actionType=='detail'||parent.actionType=='edit'||parent.actionType == 'borrow'){
                MOD.Form.fillForm($('#bookDetail'),parent.dataForChild);
                form.render();
            }
            form.render()
            console.log($("#bookName").val())
            $.ajax({
                url:basePath+'tushuxinxiguanli/getAuthorAndPublisherByBookMsgId'
                ,data:{
                    bookMessageId:$("#bookName").val()
                }
                ,success:function (res) {
                    console.log(res);
                    $("input[name='author']").val(res.data.author);
                    $("input[name='publisher']").val(res.data.publisher);
                    form.render();
                }
            });
        }
    });
    $.ajax({
        url: basePath + "data/listAllBookStatus"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#bookStatusId"), res.data, "bookStatusId", "bookStatusName");
            if(parent.actionType=='detail'||parent.actionType=='edit'||parent.actionType == 'borrow'){
                MOD.Form.fillForm($('#bookDetail'),parent.dataForChild);
                form.render();
            }
            form.render()
        }
    });
    $.ajax({
        url: basePath + "data/listAllQuality"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#qualityId"), res.data, "qualityId", "qualityName");
            if(parent.actionType=='detail'||parent.actionType=='edit'||parent.actionType == 'borrow'){
                MOD.Form.fillForm($('#bookDetail'),parent.dataForChild);
                form.render();
            }
            form.render()
        }
    });
    console.log(parent.actionType);
    if(parent.actionType=='detail'){
        $("form input").attr("readonly","readonly");
        $("form textarea").attr("readonly","readonly");
        $("form select").attr("readonly", "readonly").attr("disabled", "disabled");
        $("button").addClass("layui-hide");
    }else if(parent.actionType=='add'){
        $("input[name='publisher']").attr("readonly", "readonly");
        $("input[name='author']").attr("readonly", "readonly");
        $("#IDinput").addClass("layui-hide");
        $("#bookNum").removeClass("layui-hide")
    }else if(parent.actionType=='edit'){
        // console.log(parent.dataForChild);
        $("#bookName").attr("readonly", "readonly").attr("disabled", "disabled");
        $("input[name='publisher']").attr("readonly", "readonly");
        $("input[name='author']").attr("readonly", "readonly");
    }else if (parent.actionType == 'borrow') {
        $("form input").attr("readonly","readonly");
        $("form textarea").attr("readonly","readonly");
        $("form select").attr("readonly", "readonly").attr("disabled", "disabled");
        $("#save").addClass("layui-hide");
        $("#borrow").removeClass("layui-hide");
    }else if (parent.actionType == 'fastBorrow') {
        $("#save").addClass("layui-hide");
        $("#borrow").removeClass("layui-hide");
        $("input").attr("readonly", "readonly");
        $("form textarea").attr("readonly","readonly");
        $("form select").attr("readonly", "readonly").attr("disabled", "disabled");
        $("input[name='bookId']").prop("readonly",false)
        $("input[name='bookId']").blur(function (data) {
            $.ajax({
                url:basePath+''
                ,type:'get'
                ,success:function (res) {
                    if (res.code == 0) {
                        MOD.Form.fillForm($('#bookDetail'),res.data);
                    }
                }
            })
        });
    }

    form.on('select(bookName)', function(d){
        $.ajax({
            url:basePath+'tushuxinxiguanli/getAuthorAndPublisherByBookMsgId'
            ,data:{
                bookMessageId:d.value
            }
            ,success:function (res) {
                $("input[name='author']").val(res.data.author);
                $("input[name='publisher']").val(res.data.publisher);
            }
        })
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

    $("#borrow").click(function (data) {
        layer.prompt({title: '请询问订单号，填写并确认', formType: 0}, function (pass, index1) {
            if (pass == data)
                layer.close(index1);
            layer.prompt({title: '请输入用户名，并确认', formType: 0}, function (userId, index2) {
                layer.closeAll();
                $.ajax({
                    url: basePath + 'borrow/borrowBook'
                    , data: {
                        email: userId
                        , code: pass
                        , bookMessageId: parent.dataForChild.bookMessageId
                    }
                    , success: function (res) {
                        if (res.code == 0) {
                            layer.msg(text + "借出" + data.bookMessageName + "<br>" + "消息:" + res.msg);
                        }
                    }
                    , error: function (res) {
                        layer.msg(res.msg);
                    }
                });
            });
        });
    });

    //关闭按钮
    $('button[type=close]').click(function(){
        var mywindow = parent.layer.getFrameIndex(window.name);
        parent.layer.close(mywindow); //再执行关闭
    });

    form.verify({
        moreThanOne:function (value) {
            if (value < 0||value>1000) {
                return "数值不合理";
            }
        }
    });

});