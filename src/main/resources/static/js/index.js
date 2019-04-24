//JavaScript代码区域
var dataForChild;

layui.use(['layer','element','table','form','code','layedit','carousel'], function() {
    var $ = layui.jquery;
    var element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
        , layer = layui.layer;
    var table = layui.table;
    form = layui.form;
    var carousel = layui.carousel;
    layui.code({
        about: false
    }); //引用code方法
    //意见反馈框
    var layedit = layui.layedit;
    var index = layedit.build('demo', {
        //hideTool: ['image']
        uploadImage: {
            // url: 'json/upload/demoLayEdit.json',
            // type: 'get'
        }
        //,tool: []
        //,height: 100
    });
    //监听导航点击
    element.on('nav(demo)', function(elem){
        var layuiId =( $(this).parent().attr('id').split('-')[1])%16;//标号
        console.log("页面:"+layuiId);
        $('#nav-content-'+layuiId).removeClass('layui-hide').siblings().addClass('layui-hide');//显示当前隐藏其他

        $('.reStatus').each(function () {
            // alert($(this).text());
            if($(this).text()==="已还书"){
                $(this).removeClass('layui-hide').siblings().addClass("layui-hide");
            }
        });
        if (layuiId == 4) {//图书推荐
            $.ajax({
                url:basePath+''
                ,type:'get'
                ,success:function (res) {

                }
            })
        }
        if(layuiId==5){//个人信息
            $.ajax({
                url: basePath + 'data/listAllRole'
                , type: 'get'
                , success: function (res) {
                    MOD.Form.fillSelect($("#roleId"), res.data,"roleId", "roleName");
                    form.render();
                    $.ajax({
                        url:basePath+'yonghuguanli/getCurrentUserInfo'
                        ,type:'get'
                        ,success:function (rul) {
                            console.log($("#userMsg"));
                            console.log(rul.data);
                            MOD.Form.fillForm($("#userMsg"),rul.data);
                            if (rul.data.sex == '0') {
                                $("input[name='sex'][title='女']").attr("checked", "checked");
                                $("input[name='sex'][title='男']").removeAttr("checked");
                                form.render();
                            }else {
                                $("input[name='sex'][title='男']").attr("checked", "checked");
                                $("input[name='sex'][title='女']").removeAttr("checked");
                                form.render()
                            }
                            form.render();
                        }
                    })
                }
            });
        }
    });
    $.ajax({
        url:basePath+'tushuxinxiguanli/listBookMsgHomePage'
        ,type:'get'
        ,data:{
            unionSearch: ''
        }
        ,success:function (res) {
            $.each(res.data,function (i,item) {
                if (i == 4) {
                    return false;
                }
                $("#showGoods").last().append(
                    '<div class=" goods layui-col-xs2 animated fadeIn" id="'+item.bookMessageId+'">' +
                    '<div class="cmdlist-container" style="overflow: hidden; text-overflow: ellipsis;">' +
                    '<a href="javascript:;">' +
                    '<img style="width: 100%;" src="img/商品.jpg">' +
                    '</a>' +
                    '<a href="javascript:;">' +
                    '<div class="cmdlist-text">' +
                    '<p class="info">书名:' + item.bookMassageName + '</p>' +
                    '<p class="info" style="color: grey">作者:' + item.author + '</p>' +
                    '</div>' +
                    '</a>' +
                    '</div>' +
                    '</div>'
                );
            });
        }
    });

    //图书详情页
    $('.goods').on('click',function (data) {
        dataForChild=data.id;
        console.log(data);
        layer.open({
            type: 2,
            title:"图书详情",
            area: ['1000px', '680px'],
            skin: 'layui-layer-rim', //加上边框
            content:basePath+'good'
        });
    });
    //修改个人信息
    element.on('button(setmyinfo)', function(elem){


    });
    //搜索按钮点击
    $('#index_search').on('click',function () {
        layer.load();
        setTimeout(function(){
            layer.closeAll('loading');
        }, 1500);
        $.ajax({
            url:basePath+'tushuxinxiguanli/listBookMsgHomePage'
            ,type:'get'
            ,data: {
                unionSearch: $("input[name='index_search']").val()
            }
            ,success:function (res) {
                $("#showGoods").empty();
                $.each(res.data,function (i,item) {
                    $("#showGoods").last().append(
                        '<div class=" goods layui-col-xs2 animated fadeIn" id="'+item.bookMessageId+'">' +
                        '<div class="cmdlist-container" style="overflow: hidden; text-overflow: ellipsis;">' +
                        '<a href="javascript:;">' +
                        '<img style="width: 100%;" src="img/商品.jpg">' +
                        '</a>' +
                        '<a href="javascript:;">' +
                        '<div class="cmdlist-text">' +
                        '<p class="info">书名:' + item.bookMassageName + '</p>' +
                        '<p class="info" style="color: grey">作者:' + item.author + '</p>' +
                        '</div>' +
                        '</a>' +
                        '</div>' +
                        '</div>'
                    )
                });
            }
        });
    });
    //重新填写按钮
    $('#userInfoReset').on('click',function () {
        $("textarea[name='remarks']").val(null);
    });
    form.on('submit(saveMyInfo)',function (data) {
        $.ajax({
            url:basePath+'yonghuguanli/updateUser'
            , type: 'post'
            ,data:data.field
            , success: function (res) {
                if (res.code === 0) {
                    layer.alert("操作成功！", function () {
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
        })
    });
    carousel.render({
        elem: '#test2'
        ,interval: 1800
        ,anim: 'fade'
        ,height: '120px'
    });



});

