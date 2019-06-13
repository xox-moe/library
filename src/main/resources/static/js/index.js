//JavaScript代码区域
var dataForChild;
layui.use(['layer','element','table','form','code','layedit','carousel','laydate','upload','flow'], function() {
    var $ = layui.jquery;
    var element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
        , layer = layui.layer
        , upload = layui.upload
        , flow = layui.flow
        , laydate = layui.laydate;
    var table = layui.table;
    form = layui.form;
    var carousel = layui.carousel;
    layui.code({
        about: false
    }); //引用code方法
    //意见反馈框
    var layedit = layui.layedit;
    // var index = layedit.build('adviceArear', {
    //     //hideTool: ['image']
    //     uploadImage: {
    //         // url: 'json/upload/demoLayEdit.json',
    //         // type: 'get'
    //     }
    //     //,tool: []
    //     //,height: 100
    // });

    //填写select
    $.ajax({
        url: basePath + "data/listAllBookKind"
        , type: 'get'
        , success: function (res) {
            // console.log(res.data);
            MOD.Form.fillSelect($("select[name='kindId']"), res.data, "kindId", "kindName");
            form.render();
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
    $.ajax({
        url: basePath + 'yonghuguanli/getCurrentUserInfo'
        , type: "get"
        , success: function (res) {
            console.log("用户ID:" + res.data.roleId);
            if(res.data.img!=null)
            $("#headImg").attr("src", res.data.img);
            $("#username").last().append(res.data.nickName)
            if (res.data.roleId == 2) {//普通用户

                $("#otherSystem").addClass("layui-hide");
                $("#nav-16").addClass("layui-hide");
            }else if (res.data.roleId == 3) {//游客
                $("#otherSystem").addClass("layui-hide");
                $("#currentUserSystem").attr("disabled","disabled");
            }
        }
    });
    //打开详情页
    function showGoods(){
        console.log("绑定详情页事件");
        $(".goods img").click(function (data) {
            console.log(data.currentTarget);
            dataForChild=data.currentTarget.attributes.name.value.split("-")[1];
            console.log(dataForChild);
            layer.open({
                type: 2,
                id:"goodsPage",
                title:"图书详情",
                area: ['1000px', '680px'],
                skin: 'layui-layer-rim', //加上边框
                content:basePath+'good'
            });
            return false
        })
    }
    //打开详情页2
    function showGoods2(){
        console.log("绑定详情页事件");
        $(".goods2 img").click(function (data) {
            console.log(data.currentTarget);
            dataForChild=data.currentTarget.attributes.name.value.split("-")[1];
            console.log(dataForChild);
            layer.open({
                type: 2,
                id:"goodsPage",
                title:"图书详情",
                area: ['1000px', '680px'],
                skin: 'layui-layer-rim', //加上边框
                content:basePath+'good'
            });
            return false
        })
    }
    //新进图书
    function newBooks() {
        console.log("ok")
        $.ajax({
            url:basePath+'churukuguanli/listNewBook'
            ,type:'get'
            ,success:function (res) {
                if (res.code == 0) {
                    $("#newBook").empty();
                    $.each(res.data,function (i, item) {
                        if (item.img == null) {
                            item.img = "img/商品.jpg";
                        }
                        $("#newBook").last().append(
                            '<div class="goods layui-col-xs2 animated fadeIn" name="newBook-'+item.bookMessageId+'">'+
                            '<div class="cmdlist-container" style="overflow: hidden; text-overflow: ellipsis;">'+
                            '<a href="javascript:;">'+
                            '<img style="width: 100%;" src="'+item.img+'" name="newBook-'+item.bookMessageId+'">'+
                            '</a>'+
                            '<a href="javascript:;">'+
                            '<div class="cmdlist-text" >'+
                            '<p class="info">书名:'+item.name+'</p>'+
                            '<p class="info" style="color: grey">作者:'+item.author+'</p>'+
                            '</div></a></div></div>'
                        )
                    })
                }
                //图书详情
                showGoods();
            }
        })
    }
    //主页书籍
    function homeGoods(unionSearch){
        var homeGoodsCount=0;
        var homeGoodsPage=0;
        var homeGoodsLimit=5;
        var homeGoodsAllData = [];
        $.ajax({
            url:basePath+'tushuxinxiguanli/listBookMsgHomePage'
            ,type:'get'
            ,data:{
                unionSearch: unionSearch
            }
            ,success:function (res) {
                homeGoodsCount=res.count;
                homeGoodsPage = res.count / homeGoodsLimit;
                $.each(res.data,function (i,item) {
                    if (item.img == null) {
                        item.img = "img/商品.jpg";
                    }
                    console.log(item.bookMessageId);
                    homeGoodsAllData.push(
                        '<div class=" goods layui-col-xs2 animated bounceIn" name="showGoods-'+item.bookMessageId+'">' +
                        '<div class="cmdlist-container" style="overflow: hidden; text-overflow: ellipsis;">' +
                        '<a href="javascript:;">' +
                        '<img style="width: 100%;" src="'+item.img+'" name="showGoods-'+item.bookMessageId+'">' +
                        '</a>' +
                        '<a href="javascript:;">' +
                        '<div class="cmdlist-text">' +
                        '<p class="info">书名:' + item.bookMassageName + '</p>' +
                        '<p class="info" style="color: grey">作者:' + item.author + '</p>' +
                        '</div>' +
                        '</a>' +
                        '</div>' +
                        '</div>');
                });
                //图书详情页
                flow.load({
                    elem: '#showGoods' //流加载容器
                    ,scrollElem: '#showGoods' //滚动条所在元素，一般不用填，此处只是演示需要。
                    ,done: function(page, next){ //执行下一页的回调
                        //数据插入
                        setTimeout(function(){
                            var lis = [];
                            console.log(page);
                            for(var i = (page-1)*homeGoodsLimit; i < (page)*homeGoodsLimit; i++){
                                lis.push(homeGoodsAllData[i]);
                            }
                            //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                            //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                            next(lis.join(''), page < homeGoodsPage); //假设总页数为 10
                            showGoods();
                        }, 250);
                    }
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
                    if (item.img == null) {
                        item.img = "img/商品.jpg";
                    }
                    $("#recommend").last().append(
                        '<div class="goods layui-col-sm2 animated zoomIn" name="recommendId-'+item.bookMessageId+'" style="height: 30em;" >' +
                        '<div class="grid-demo grid-demo-bg1">' +
                        '<div class="layui-row grid-demo" style="height: 28em; background-color: #a9c6de;overflow: hidden; text-overflow: ellipsis;">' +
                        '<div class="layui-col-md12">' +
                        '<div class="grid-demo grid-demo-bg1">' +
                        '<img style="width: 100%;" src="'+item.img+'" name="recommendId-'+item.bookMessageId+'">' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-col-md12">' +
                        '<div class="cmdlist-text" style="">' +
                        '<p class="info">' +item.introduction+
                        '</p> </div> </div> </div> </div> </div>'
                    )
                });
                //图书详情页
                showGoods();
            }
        })
    }
    homeGoods('');
    //我的借阅
    function borrowPane(){
        var flag = 0;
        $("#bookBorrow").empty();
        $.ajax({
            url: basePath + 'order/listMyOrder'
            , type: 'get'
            , success: function (res) {
                if (res.code == 0) {
                    $.each(res.data, function (i, item) {
                        if (item.img == null) {
                            item.img = "img/商品.jpg";
                        }
                        $("#bookBorrow").last().append(
                            '<div class="goods2" style="display: flex; border: 2px solid gainsboro; padding: 1em; border-radius: 10px; margin-bottom: 1.2em;" name="BorrowId-' + item.bookMessageId +'" id="BorrowId-' + item.bookMessageId + '" >' +
                            '<div style="flex-grow: 1;">' +
                            '<span style="font-size: 1em;line-height:6em;">预约:'+item.orderTime+'</span>' +
                            '<br />'+
                            '<p>订单号:'+item.code+'</p>' +
                            '</div>' +
                            '<div  style="flex-grow: 1.2;">' +
                            '<img style="width: 12em;" src="'+item.img+'" name="BorrowId-' + item.bookMessageId +'">' +
                            '</div>' +
                            '<div style="flex-grow: 2;line-height:3em;">' +
                            '<p>书名:' + item.name + '</p>' +
                            '<p>作者:' + item.author + '</p>' +
                            '<p>类别:' + item.kindName + '</p>' +
                            '<p>出版社:' + item.publisher + '</p>' +
                            '</div>' +
                            '<div style="flex-grow: 3;">' +
                            '<p>' + "存放简介" + '</p>' +
                            '</div>' +
                            '<div style="flex-grow: 1;line-height: 12em;width:10em;">' +
                            '<button style="position: relative; left: 4em;" class="layui-btn layui-bg-orange" id="borrow-' + item.bookMessageId + '">取消预约</button>' +
                            '</div>' +
                            '</div>'
                        );
                        $("#borrow-"+item.bookMessageId).on('click',function (event){
                            console.log("OK");
                            $.ajax({
                                url:basePath+'order/cancelOrder'
                                ,data:{
                                    orderId: item.orderId
                                },
                                success:function (res) {
                                    if (res.code == 0) {
                                        $("#BorrowId-" + item.bookMessageId).fadeOut();
                                    }
                                }
                            });
                            event.stopPropagation()
                            return false
                        })
                    });

                    $.ajax({
                        url: basePath + 'borrow/listMyBorrow'
                        , type: 'get'
                        ,data:{
                            page:1
                            ,limit:10
                        }
                        , success: function (res) {
                            if (res.code == 0) {
                                $.each(res.data, function (i, item) {
                                    if (item.img == null) {
                                        item.img = "img/商品.jpg";
                                    }
                                    $("#bookBorrow").last().append(
                                        '<div class="goods2" style="display: flex; border: 2px solid gainsboro; padding: 1em; border-radius: 10px; margin-bottom: 1.2em;" name="BorrowId-' + item.bookMessageId + '">' +
                                        '<div style="flex-grow: 1;">' +
                                        '<span style="font-size: 1em;line-height: 6em;" id="needReturnTime-'+item.borrowId+'">待还:'+item.needReturnTime+'</span>' +
                                        '<br />'+
                                        '<p>' + "借阅:" +item.outTime+ '</p>' +
                                        '<br />'+
                                        '<p id="returnTime-'+item.borrowId+'">归还:'+item.backTime+'</p>' +
                                        '</div>' +
                                        '<div style="flex-grow: 1.2;">' +
                                        '<img style="width: 12em;" src="'+item.img+'" name="BorrowId-' + item.bookMessageId + '">' +
                                        '</div>' +
                                        '<div style="flex-grow: 2;line-height: 3em;">' +
                                        '<p>书名:' + item.bookName + '</p>' +
                                        '<p>作者:' + item.author + '</p>' +
                                        '<p>类别:' + item.kindName + '</p>' +
                                        '<p>出版社:' + item.publisher + '</p>' +
                                        '</div>' +
                                        '<div style="flex-grow: 3;">' +
                                        '<p>' + "存放简介" + '</p>' +
                                        '</div>' +
                                        '<div style="flex-grow: 1;line-height: 12em;width:10em;">' +
                                        '<button style="position: relative; left: 4em;" class="layui-btn" id="delay-' + item.borrowId + '">延期</button>' +
                                        '</div>' +
                                        '</div>'
                                    );
                                    if (item.ifXu == true) {
                                        $("#delay-" + item.borrowId).text("不可以贪心哦").addClass("layui-btn-disabled").attr("disabled", "disabled");
                                    }
                                    if (item.needReturnTime == undefined || item.needReturnTime == null || item.needReturnTime == '') {

                                        $("#needReturnTime-" + item.borrowId).addClass("layui-hide");
                                    }
                                    if (item.ifReturn == false) {
                                        console.log("ifReturn=false");
                                        $("#returnTime-" + item.borrowId).addClass("layui-hide");
                                    }else{
                                        $("#delay-" + item.borrowId).addClass("layui-btn-disabled").attr("disabled", "disabled");
                                    }
                                    $("#delay-"+item.borrowId).on('click',function (event){
                                        console.log("OK");
                                        $.ajax({
                                            url:basePath+'borrow/xuBorrow'
                                            ,data:{
                                                borrowId: item.borrowId
                                            },
                                            success:function (res) {
                                                if (res.code == 0) {
                                                    $("#delay-" + item.borrowId).text("不可以贪心哦").addClass("layui-btn-disabled").attr("disabled", "disabled");
                                                    location.reload();
                                                }else{
                                                    layer.msg(res.msg);
                                                }
                                            }
                                        });
                                        event.stopPropagation();
                                        return false
                                    })
                                });
                                showGoods2();
                            }
                        }
                    });
                }
            }
        });
    }
    //我的收藏
    function collectionPane(){
        $.ajax({
            url: basePath + 'yonghuguanli/listUserCollection'
            , type: 'get'
            , success: function (res) {
                $("#bookul").empty();
                if (res.code === 0) {
                    $.each(res.data, function (i, item) {
                        if (item.img == null) {
                            item.img = "img/商品.jpg";
                        }
                        $("#bookul").last().append(
                            '<div class="goods2" style="display: flex; border: 2px solid gainsboro; padding: 1em; border-radius: 10px; margin-bottom: 1.2em; "id="collectionPane-'+ item.collectionId + '" name="collectionPane-'+item.bookMessageId+'">' +
                            '<div style="flex-grow: 1;">' +
                            '<span style="font-size: 1em;line-height: 6em;">序号:' + (i+1) + '</span>' +
                            '<br>'+
                            '<p><span>库存数量:' + item.bookNum + '</span></p>' +
                            '</div>' +
                            '<div style="flex-grow: 1;">' +
                            '<img style="width: 12em;" src="'+item.img+'" name="collectionPane-'+item.bookMessageId+'">' +
                            '</div>' +
                            '<div style="flex-grow: 2;line-height:3em;">' +
                            '<p>书名:' + item.bookMessageName + '</p>' +
                            '<p>作者:' + item.author + '</p>' +
                            '<p>类别:' + item.kindName + '</p>' +
                            '<p>出版社:' + item.publisher + '</p>' +
                            '</div>' +
                            '<div style="flex-grow: 1;line-height: 12em;">' +
                            '<button style="position: relative; left: 4em;" class="layui-btn layui-bg-red" id="collection-' + item.collectionId + '">取消收藏</button>' +
                            '</div>' +
                            '</div>'
                        );
                        $("#collection-"+item.collectionId).on('click',function (event){
                            console.log("OK");
                            $.ajax({
                                url:basePath+'yonghuguanli/deleteCollectionById'
                                ,data:{
                                    collectionId: item.collectionId
                                },
                                success:function (res) {
                                    if (res.code == 0) {
                                        $("#collectionPane-" + item.collectionId).fadeOut();
                                    }
                                }
                            });
                            event.stopPropagation()
                            return false
                        })
                    });
                    //图书详情页
                    showGoods2();
                }
            }
        });
    }
    //浏览历史
    function history(){
        var historyCount=0;
        var historyPage=0;
        var historyLimit=5;
        var historyAllData = [];
        $.ajax({
            url: basePath + 'yonghuguanli/listUserHistory'
            , type: 'get'
            , success: function (res) {
                $("#history").empty();
                if (res.code == '0') {
                    historyCount = res.count;
                    historyPage = historyCount / historyLimit;
                    $.each(res.data, function (i, item) {
                        if (item.img == null) {
                            item.img = "img/商品.jpg";
                        }
                        historyAllData.push(
                            '<div class="goods2" style="display: flex; border: 2px solid gainsboro; padding: 1em; border-radius: 10px; margin-bottom: 1.2em; "name="history-' + item.bookMessageId + '">' +
                            '<div style="flex-grow: 1;">' +
                            '<span style="font-size: 1em;line-height: 6em;">浏览日期:'+ item.historyTime + '</span>' +
                            '</div>' +
                            '<div style="flex-grow: 1;">' +
                            '<img style="width: 12em;" src="'+item.img+'" name="history-' + item.bookMessageId +'">' +
                            '</div>' +
                            '<div style="flex-grow: 2;line-height: 3em;">' +
                            '<p>书名:' + item.name + '</p>' +
                            '<p>作者:' + item.author + '</p>' +
                            '<p>类别:' + item.kindName + '</p>' +
                            '<p>出版社:' + item.publisher + '</p>' +
                            '</div>' +
                            '<div style="flex-grow: 3;width:20em;height: 6em;">' +
                            '<p>简介:' + item.intorduciton + '</p>' +
                            '</div>' +
                            '</div>');
                    });
                }
                flow.load({
                    elem: '#history' //流加载容器
                    // ,scrollElem: '#history' //滚动条所在元素，一般不用填，此处只是演示需要。
                    ,done: function(page, next){ //执行下一页的回调
                        //数据插入
                        setTimeout(function(){
                            var lis = [];
                            console.log(page);
                            for(var i = (page-1)*historyLimit; i < (page)*historyLimit; i++){
                                lis.push(historyAllData[i]);
                            }
                            //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                            //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                            next(lis.join(''), page < historyPage); //假设总页数为 10
                            showGoods2();
                        }, 250);
                    }
                });
            }
        });
    }
    //监听导航点击
    element.on('nav(demo)', function(elem){
        var layuiId =( $(this).parent().attr('id').split('-')[1])%17;//标号
        console.log(layuiId);
        $('#nav-content-'+layuiId).removeClass('layui-hide').siblings().addClass('layui-hide');//显示当前隐藏其他
        if (layuiId == 0) {
            $.ajax({
                type: 'get',
                url: basePath + 'logOut',
                success: function (res) {
                    if (res.code === 0) {
                        window.location.href = basePath + "login";
                    }
                },
                error: function (jqXHR) {
                    console.log("请求发送失败，原因是：" + jqXHR.status)
                }
            })
        }
        if (layuiId == 2) {//新进图书
            newBooks();
        }
        if (layuiId == 4) {//图书推荐
            recomendGoods();
        }
        if (layuiId == 5) {//个人信息//普通图片上传
            var uploadInst = upload.render({
                elem: '#uploadMyImg'
                ,url: basePath+'yonghuguanli/changeImg'
                ,accept: 'images'
                ,acceptMime: 'image/*'
                // ,multiple: true
                // ,auto: false
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    var loading=layer.load();
                    obj.preview(function(index, img, result){
                        $("input[name='avatar']").val(img.name);
                        $('#myImg').find("img").attr('src', result); //图片链接（base64）
                        MOD.Util.photoCompress(img, {
                            quality: 0.5,
                        }, function(base64Codes) {
                            var bl = MOD.Util.convertBase64UrlToBlob(base64Codes);
                            obj.resetFile(index, bl, img.name);
                            console.log(obj.resetFile);
                        });
                    });
                }
                ,done: function(res){
                    layer.closeAll("loading");
                    //如果上传失败
                    if(res.code > 0){
                        return layer.msg('上传失败');
                    }
                }
            });
            $("#showImg").click(function (data) {
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['500px','500px'],
                    skin: 'layui-layer-nobg', //没有背景色
                    shadeClose: true,
                    content: $("#myImg")
                    ,success: function(layero, index) {
                        $("#myImg").removeClass("layui-hide");
                        $("#myImg").find("img").css("width", "500px").css("height", "500px");
                    }
                    ,end:function () {
                        $("#myImg").find("img").css("width", "118px").css("height", "118px");
                    }
                });
            });
            $.ajax({
                url: basePath + 'data/listAllRole'
                , type: 'get'
                , success: function (res) {
                    MOD.Form.fillSelect($("#roleId"), res.data, "roleId", "roleName");
                    form.render();
                    $.ajax({
                        url: basePath + 'yonghuguanli/getCurrentUserInfo'
                        , type: 'get'
                        , success: function (rul) {
                            console.log($("#userMsg"));
                            console.log(rul.data);
                            MOD.Form.fillForm($("#userMsg"), rul.data);
                            if (rul.data.sex == '0') {
                                $("input[name='sex'][title='女']").attr("checked", "checked");
                                $("input[name='sex'][title='男']").removeAttr("checked");
                                form.render();
                            } else {
                                $("input[name='sex'][title='男']").attr("checked", "checked");
                                $("input[name='sex'][title='女']").removeAttr("checked");
                                form.render()
                            }
                            if (rul.data.img != null) {
                                $("#myImg").find("img").attr("src", rul.data.img);
                            }
                            console.log(rul.data.img);

                            form.render();
                        }
                    })
                }
            });
        }
        if (layuiId == 6) {//我的借阅
            borrowPane();
        }
        if (layuiId == 7) {//我的收藏

            collectionPane();
        }
        if (layuiId == 8) {//浏览历史
            history();
        }
        if (layuiId==16) {
            actionType = 'fastBorrow';
            layer.open({
                type: 2,
                title:"借出与归还",
                area: ['400px', '550px'],
                skin: 'layui-layer-rim layui-layer-molv', //加上边框
                content:basePath+'bookDetail'
            });
        }
        if (layuiId == 9) {
            $("#submitAdvice").click(function (data) {
                $.ajax({
                    url: basePath + 'fankuiguanli/addAdvice'
                    , type: 'post'
                    ,data:{
                        message: $("#adviceArear").val()
                    }
                    ,success:function (res) {
                        if (res.code == 0) {
                            layer.msg("提交成功")
                        }else {
                            layer.msg(res.msg);
                        }
                    }
                })
            })
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
            url:basePath+'yonghuguanli/updateCurrentUser'
            , type: 'post'
            ,data:data.field
            , success: function (res) {
                if (res.code === 0) {
                    layer.alert("操作成功！");
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

