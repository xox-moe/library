//JavaScript代码区域
var dataForChild;
var childData;
layui.use(['layer','element','table','form','code','layedit'], function() {

    var $ = layui.jquery;
    var element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
        , layer = layui.layer;
    var table = layui.table;
    form = layui.form;
    layui.code({
        about: false
    }); //引用code方法
    console.log(dataForChild);
    $.ajax({
        url: basePath + 'tushuxinxiguanli/getBookMessageById'
        , data: {
            bookMessageId: parent.dataForChild
        }
        , success: function (res) {
            if (res.code == 0) {
                console.log(res.data);
                $("#introduction").text(res.data.introduction);
                $("#name").text(res.data.name);
                $("#author").text(res.data.author);
                $("#publisher").text(res.data.publisher);
                $("#totalNum").text(res.data.couldOutNum-res.data.orderNum);
                childData = res.data;
                if (childData.ifOrder == true) {
                    $("#deleteBorrow").removeClass("layui-hide");
                    $("#borrow").addClass("layui-hide");
                }
                if (childData.ifCollection == true) {
                    $("#deleteCollection").removeClass("layui-hide");
                    $("#collection").addClass("layui-hide");
                }
            }
        }
    });
    $("#borrow").on('click',function () {
        $.ajax({
            url:basePath+'order/orderBook'
            ,data:{
                bookMessageId: parent.dataForChild
            }
            ,success:function (res) {
                if (res.code == 0) {
                    layer.msg('预约成功你的预约号码为:' + res.data.code);
                    childData.orderId = res.data.orderId;
                    $("#borrow").addClass("layui-hide");
                    $("#deleteBorrow").removeClass("layui-hide");
                    // location.reload();
                }
            }
        })
    });
    $("#deleteBorrow").on('click',function () {
        $.ajax({
            url:basePath+'order/cancelOrder'
            ,data:{
                orderId: childData.orderId
            }
            ,success:function (res) {
                if (res.code == 0) {
                    layer.tips('取消预约', '.borrow', {
                        tips: [2, '#78BA32']
                    });
                    $("#deleteBorrow").addClass("layui-hide");
                    $("#borrow").removeClass("layui-hide");
                    // location.reload();
                }
            }
        })
    });
$("#collection").on('click',function () {
 $.ajax({
     url:basePath+'yonghuguanli/collectionBook'
     ,data:{
         bookMessageId: parent.dataForChild
     }
     ,success:function (res) {
         if (res.code == 0) {
             layer.tips('收藏成功', '.collection', {
                 tips: [2, '#78BA32']
             });
             childData.collectionId = res.data.collectionId;
             $("#collection").addClass("layui-hide");
             $("#deleteCollection").removeClass("layui-hide");
             // location.reload();
         }
     }
 })
});
    $("#deleteCollection").on('click',function () {
        $.ajax({
            url:basePath+'yonghuguanli/deleteCollectionById'
            ,data:{
                collectionId: childData.collectionId
            }
            ,success:function (res) {
                if (res.code == 0) {
                    layer.tips('取消收藏', '.collection', {
                        tips: [2, '#78BA32']
                    });
                    $("#deleteCollection").addClass("layui-hide");
                    $("#collection").removeClass("layui-hide");
                    // location.reload();
                }
            }
        })
    });

    //关闭按钮
    $('button[type=close]').click(function(){
        location.reload(mywindow);
        var mywindow = parent.layer.getFrameIndex(window.name);
        parent.layer.close(mywindow); //再执行关闭
    });
});

