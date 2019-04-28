//JavaScript代码区域
var dataForChild;

layui.use(['layer','element','table','form','code','layedit'], function() {

    var $ = layui.jquery;
    var element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
        , layer = layui.layer;
    var table = layui.table;
    form = layui.form;
    layui.code({
        about: false
    }); //引用code方法

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
                $("#totalNum").text(res.data.totalNum);
            }
        }
    });
$('.collection').on('click',function () {
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
         }
     }
 })
});


$('.borrow').on('click',function () {
    $.ajax({
        url:basePath+'borrow/borrowBook'
        ,data:{
            bookMessageId: parent.dataForChild
        }
        ,success:function (res) {
            if (res == 0) {
                layer.msg('借出图书<br>'+'图书Id:'+id, {
                    time: 20000, //20s后自动关闭
                    btn: ['确认', '取消']
                    ,yes: function(){
                        layer.closeAll();
                        layer.tips('借出成功','.borrow',{
                            tips: [2, '#78BA32']
                        })
                    }
                    ,btn2: function(){
                        layer.closeAll();
                    }
                });
            }
        }
        ,error:function (res) {
            layer.msg(res.msg);
        }
    });

})
});

