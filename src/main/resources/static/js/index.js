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

    //填写select
    $.ajax({
        url: basePath + "data/listAllBookKind"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("select[name='kindId']"), res.data, "kindId", "kindName");
            from.render();
        }
    });
    $.ajax({
        url: basePath + "data/listAllBookStatus"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("select[name='bookStatusId']"), res.data, "bookStatusId", "bookStatusName");
            form.render()
        }
    });
    $.ajax({
        url: basePath + "data/listAllQuality"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("select[name='qualityId']"), res.data, "qualityId", "qualityName");
            form.render()
        }
    });
    $.ajax({
        url: basePath + 'data/listAllRole'
        , type: 'get'
        , success: function (res) {
            MOD.Form.fillSelect($("select[name='roleId']"), res.data,"roleId", "roleName");
            form.render();
        }
    });
    //主页书籍
    function homeGoods(unionSearch){
        $.ajax({
            url:basePath+'tushuxinxiguanli/listBookMsgHomePage'
            ,type:'get'
            ,data:{
                unionSearch: unionSearch
            }
            ,success:function (res) {
                $.each(res.data,function (i,item) {
                    console.log(item.bookMessageId);
                    if (i == 4) {
                        return false;
                    }
                    $("#showGoods").last().append(
                        '<div class=" goods layui-col-xs2 animated fadeIn" id="showGoods-'+item.bookMessageId+'">' +
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
                //图书详情页
                $('.goods').on('click',function (data) {
                    dataForChild=data.currentTarget.id.split("-")[1];
                    console.log(dataForChild);
                    layer.open({
                        type: 2,
                        title:"图书详情",
                        area: ['1000px', '680px'],
                        skin: 'layui-layer-rim', //加上边框
                        content:basePath+'good'
                    });
                });
            }
        });
    }
    //图书推荐书籍
    function recomendGoods(){
        $.ajax({
            url:basePath+'tushuxinxiguanli/listBookMsgRandom'
            ,type:'get'
            ,data:{
                limit:5
            }
            ,success:function (res) {
                $("#recommend").empty();
                $.each(res.data, function (i, item) {
                    $("#recommend").last().append(
                        '<div class="goods layui-col-sm2 animated zoomIn" style="height: 30em;" id=recommendId-"'+item.bookMessageId+'">' +
                        '<div class="grid-demo grid-demo-bg1">' +
                        '<div class="layui-row grid-demo" style="height: 28em; background-color: #a9c6de;overflow: hidden; text-overflow: ellipsis;">' +
                        '<div class="layui-col-md12">' +
                        '<div class="grid-demo grid-demo-bg1">' +
                        '<img style="width: 100%;" src="img/商品.jpg">' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-col-md12">' +
                        '<div class="cmdlist-text" style="">' +
                        '<p class="info">' +item.introduction+
                        '</p> </div> </div> </div> </div> </div>'
                    )
                });
                //图书详情页
                $('.goods').on('click',function (data) {
                    console.log(data);
                    dataForChild=data.currentTarget.id.split("-")[1];
                    console.log(dataForChild);
                    layer.open({
                        type: 2,
                        title:"图书详情",
                        area: ['1000px', '680px'],
                        skin: 'layui-layer-rim', //加上边框
                        content:basePath+'good'
                    });
                });
            }
        })
    }
    homeGoods('');
    //我的借阅
    function collectionPane(){
        $.ajax({
            url: basePath + 'yonghuguanli/listUserCollection'
            , type: 'get'
            , success: function (res) {
                $("#bookul").empty();
                if (res.code == '0') {
                    $.each(res.data, function (i, item){
                        $("#bookul").last().append(
                            '<li style="display: flex;">' +
                            '<div style="flex-grow: 1;width: 5em;">' +
                            '<span style="font-size: 1em;">应还日期</span>' +
                            '</div>' +
                            '<div style="flex-grow: 1.2;width: 5em;">' +
                            '<img style="width: 100%" src="img/商品.jpg">' +
                            '</div>' +
                            '<div style="flex-grow: 3;width: 15em;">' +
                            '<p>书名:' + item.bookMessageName + '</p>' +
                            '<p>作者:' + item.author + '</p>' +
                            '<p>类别:' + item.kindName + '</p>' +
                            '<p>出版社:' + item.publisher + '</p>' +
                            '<p>' + "????" + '</p>' +
                            '</div>' +
                            '<div style="flex-grow: 2;width: 10em;">' +
                            '<p>一个暂时没有的订单号</p>' +
                            '</div>' +
                            '<div style="flex-grow: 1;width: 5em;">' +
                            '<button class="layui-btn" id="delay-"' + res.data.bookMessageId + '>取消收藏</button>' +
                            '</div>' +
                            '</li>'
                        );

                        // $("#delay-" + res.data.bookMessageId).click(function (data) {
                        //     $('.goods').on('click',function (data) {
                        //         console.log(data);
                        //         dataForChild=data.currentTarget.id.split("-")[1];
                        //         console.log(dataForChild);
                        //         layer.open({
                        //             type: 2,
                        //             title:"图书详情",
                        //             area: ['1000px', '680px'],
                        //             skin: 'layui-layer-rim', //加上边框
                        //             content:basePath+'good'
                        //         });
                        //     });
                        // })
                    })

                }

            }
        });

    }
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
            recomendGoods();
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
        if (layuiId == 7) {
            collectionPane();
        }
    });


    //修改个人信息
    element.on('button(setmyinfo)', function(elem){

    });
    //搜索按钮点击
    $('#index_search').on('click',function () {
        layer.load();
        setTimeout(function(){
            layer.closeAll('loading');
        }, 300);
        $("#showGoods").empty();
        var unionSearch = $("input[name='index_search']").val();
        homeGoods(unionSearch);
    });
    //换一批
    $("#reloadRecommend").on('click', function () {
        $("#recommend").empty();
        recomendGoods();
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

