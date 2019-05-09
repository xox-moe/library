var dataForChild
    ,hello
    ,actionType;
layui.use(['layer','element','table','form','laydate','upload'], function(){
    var $ = layui.jquery;
    var element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
        ,layer = layui.layer
        ,table = layui.table
        ,form = layui.form
        ,laydate=layui.laydate
        ,upload=layui.upload;

    $.ajax({
        url: basePath + "data/listAllBookKind"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("#kindId"), res.data, "kindId", "kindName");
            form.render();
            if(parent.actionType=='detail'||parent.actionType=='edit'){
                // $("#kindId").val(parent.dataForChild.kindId);
                MOD.Form.fillForm($('#bookMsgDetail'),parent.dataForChild);
                $('#demo1').attr('src', parent.dataForChild.img);
                form.render();
            }
        }
    });
    console.log(parent.actionType);
    if(parent.actionType=='detail'){
        $("form input").attr("readonly","readonly");
        $("form textarea").attr("readonly","readonly");
        $("form select").attr("readonly", "readonly").attr("disabled", "disabled");
        $("button").addClass("layui-hide");
    }else if(parent.actionType=='add'){
        // $("#test1").addClass("layui-hide");
        // $("#demo1").addClass("layui-hide");
        $("#IDinput").addClass("layui-hide");
    }else if(parent.actionType=='edit'){
        console.log(parent.dataForChild);
    }
    if(parent.actionType!='add'){
        var myurl = 'tushuxinxiguanli / setBookMsgImg';
        var myBID=parent.dataForChild.bookMessageId
    }else {
        var myurl = 'setImg';
        var myBID=""
    }
    upload.render({
        elem: '#test1'
        ,url: basePath+myurl
        ,data:{
            bookMessageId: myBID
        }
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功
            $("input[name='imgName']").val(res.data);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
    form.on('submit(save)',function(data){//保存
        var myurl;
        if(parent.actionType=='edit')
        {
           //修改
            myurl = "tushuxinxiguanli/updateBookMsg";
        }
        else if(parent.actionType=='add')
        {
            //添加
            myurl = "tushuxinxiguanli/addBookMsg";
        }
        console.log(basePath);
        // data.field.bookMessageId = null;
        $.ajax({
            url:basePath+myurl
            , type: 'post'
            , data: data.field
            , success: function (res) {
                if (res.code === 0) {
                    layer.alert("操作成功！", function () {
                        parent.layui.table.reload('table1');
                        var myWindow = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(myWindow); //再执行关闭
                    });
                }
                else layer.alert(""+res.msg, {icon: 5});
            },
            error:function(jqXHR)
            {
                console.log('请求错误,错误的原因为:'+jqXHR.status)
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