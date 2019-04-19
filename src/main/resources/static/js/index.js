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
    getChoose.onclick = function() {
        alert(layedit.getSelection(index));
    };
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
        })
    });
    //商品详情页
    $('.goods').on('click',function () {
        layer.open({
            type: 2,
            title:"商品详情",
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
        }, 2000);
    });

    carousel.render({
        elem: '#test2'
        ,interval: 1800
        ,anim: 'fade'
        ,height: '120px'
    });



});

