//JavaScript代码区域
var dataForChild;
var actionType;
layui.use(['layer', 'element', 'table', 'form', 'code', 'layedit','laydate'],
    function () {
        var $ = layui.jquery;
        var element = layui.element //导航的hover效果、二级菜单等功能，需要依赖element模块
            ,
            layer = layui.layer,
        laydate = layui.laydate;
        var table = layui.table;
        form = layui.form;
        layui.code({
            about: false
        }); //引用code方法
        //意见反馈框
        var layedit = layui.layedit;
        // var index = layedit.build('demo', {
        //     //hideTool: ['image']
        //     uploadImage: {
        //         // url: 'json/upload/demoLayEdit.json',
        //         // type: 'get'
        //     }
        //     //,tool: []
        //     //,height: 100
        // });
        // getChoose.onclick = function () {
        //     alert(layedit.getSelection(index));
        // };
        var noticeTime = {
            beginTime: ""
            , endTime: ""
        };
        laydate.render({
            elem: "#noticeTime"
            , type: "datetime"
            ,range:true
            ,done:function (value,date,endDate) {
                noticeTime.beginTime = value.split(" - ")[0];
                noticeTime.endTime = value.split(" - ")[1];
                console.log(noticeTime);

            }
        });
        var tableActive ={
            table1:function(){
              table.render({
                  elem:'#table1'//tableId
                  ,url:basePath+'tushuxinxiguanli'//数据接口
                  ,title:'图书信息管理'//表名
                  ,page:true//开启分页
                  ,toolbar:'default'//开启头工具栏
                  // ,totalRow:true//合计行
                  ,cols:[//表头
                     [
                         {type:'checkbox',align:'center'},
                         {title:'序号',sort:'true',align:'center',type:'numbers',width:60},
                         {title:'ID',field:'bookMessageId',align:'center'},
                         {title:'书名',field:'name',align:'center',edit: 'text'},
                         {title:'作者',field:'author',align:'center',edit: 'text'},
                         {title:'分类',field:'kindName',align:'center'},
                         {title:'出版社',field:'publisher',align:'center',edit: 'text'},
                         {title:'描述',field:'introduction',align:'center',edit: 'text',width:300},
                         {title:'可借出/总数',field:'Num',align:'center',templet:function (d) {
                                 return (d.couldOutNum-d.orderNum)+"/"+d.totalNum;
                             }},
                         // {title:'数量',field:'',align:'center',totalRowText:'合计:'},
                         {title:'操作',align:'center',toolbar: '#barDemo'}
                  ]]
                  ,data:[{}]//假数据放这
              });
            } ,
            table2:function(){
                table.render({
                    elem:'#table2'//tableId
                    ,url:basePath+'churukuguanli'//数据接口
                    ,title:'出入库管理'//表名
                    ,page:true//开启分页
                    ,toolbar:'default'//开启头工具栏
                    // ,totalRow:true//合计行
                    ,cols:[//表头
                       [
                        {type:'checkbox',align:'center'},
                        {title:'序号',sort:'true',align:'center',type:'numbers',width:60},
                        {title:'ID',field:'bookId',align:'center'},
                        {title:'书名',field:'bookMessageName',align:'center'},
                        {title:'作者',field:'author',align:'center'},
                        // {title:'类别',field:'kindName',align:'center',edit: 'text'},
                        {title:'出版社',field:'publisher',align:'center'},
                        {title:'品质',field:'qualityName',align:'center'},
                        {title:'借阅人',field:'userName',align:'center',templet:function (d) {
                                if(d.userName==null){
                                    return "暂无";
                                }else {
                                    return d.userName;
                                }
                            }},
                        {title:'状态',field:'bookStatusId',align:'center',templet:function (d) {
                                return d.statusName;
                            }},
                        // {title:'数量',field:'',align:'center',totalRowText:'合计:'},
                        {title:'操作',align:'center',toolbar: '#barDemo2',width:250}
                    ]]
                    ,data:[{}]//假数据放这
                });
              } ,
              table3:function(){
                table.render({
                    elem:'#table3'//tableId
                    ,url:basePath+'gonggaoguanli'//数据接口
                    ,title:'公告管理'//表名
                    ,page:true//开启分页
                    ,toolbar:'default'//开启头工具栏
                    // ,totalRow:true//合计行
                    ,cols:[//表头
                       [
                        {type:'checkbox',align:'center'},
                        {title:'序号',field:'',sort:'true',align:'center',type:'numbers',width:60},
                        {title:'ID',field:'noticeId',align:'center'},
                        {title:'开始时间',field:'beginTime',align:'center'},
                        {title:'结束时间',field:'endTime',align:'center'},
                        {title:'内容',field:'message',align:'center',edit: 'text'},
                        {title:'操作',align:'center',toolbar: '#barDemo3'}
                    ]]
                    ,data:[{}]//假数据放这
                });
              } ,
              table4:function(){
                table.render({
                    elem:'#table4'//tableId
                    ,url:basePath+'yonghuguanli'//数据接口
                    ,title:'用户管理'//表名
                    ,page:true//开启分页
                    ,toolbar:'default'//开启头工具栏
                    // ,totalRow:true//合计行
                    ,cols:[//表头
                       [
                        {type:'checkbox',align:'center'},
                        {title:'序号',sort:'true',align:'center',type:'numbers',width:60},
                        {title:'ID',field:'userId',align:'center'},
                        {title:'姓名',field:'realName',align:'center',edit: 'text'},
                        {title:'昵称',field:'nickName',align:'center',edit: 'text'},
                        {title:'性别',field:'sex',align:'center',edit: 'text',templet:function (d) { 
                            if(d.sex=='1'){
                                return "男"
                            }else if (d.sex=='0') {
                                return "女"
                            }else {
                                return "未填写"
                            }
                            }},
                        {title:'生日',field:'birthday',align:'center',edit: 'text'},
                        {title:'邮箱',field:'email',align:'center'},
                        {title:'账号类型',field:'roleName',align:'center'},
                        {title:'年级',field:'grade',align:'center',edit: 'text'},
                        {title:'学院',field:'department',align:'center',edit: 'text'},
                        {title:'专业',field:'major',align:'center',edit: 'text'},
                        {title:'操作',align:'center',toolbar: '#barDemo4',width:200}
                    ]]
                    ,data:[{}]//假数据放这
                });
              } ,
              table5:function(){
                table.render({
                    elem:'#table5'//tableId
                    ,url:basePath+'fankuiguanli'//数据接口
                    ,title:'反馈管理'//表名
                    ,page:true//开启分页
                    ,toolbar:'default'//开启头工具栏
                    // ,totalRow:true//合计行
                    ,cols:[//表头
                       [
                        {type:'checkbox',align:'center'},
                        {title:'序号',sort:'true',align:'center',type:'numbers',width:60},
                        {title:'ID',field:'adviceId',align:'center'},
                        {title:'建议人ID',field:'userId',align:'center'},
                        {title:'建议人姓名',field:'realName',align:'center'},
                        {title:'信息',field:'message',align:'center',width:350},
                        // {title:'状态',field:'state',align:'center',edit: 'text'},
                        // {title:'数量',field:'',align:'center',totalRowText:'合计:'},
                        {title:'操作',align:'center',toolbar: '#barDemo5'}
                    ]]
                    ,data:[{}]//假数据放这
                });
              } 
          } ;
        // 图书信息管理
        //监听头工具栏事件
        //table1
        table.on('toolbar(test1)',
            function (obj) {
                var checkStatus = table.checkStatus(obj.config.id),
                    data = checkStatus.data; //获取选中的数据
                console.log("checkStatus.data:");
                console.log(checkStatus.data);
                switch (obj.event) {
                    case 'add':
                        actionType = 'add';
                        layer.open({
                            type: 2,
                            title:"新增图书",
                            area: ['400px', '650px'],
                            skin: 'layui-layer-rim layui-layer-molv', //加上边框
                            content:basePath+'bookMsgDetail'
                        });
                        break;
                    case 'update':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else if (data.length > 1) {
                            layer.msg('只能同时编辑一个');
                        } else {

                            dataForChild = data[0];
                            actionType='edit';
                            layer.open({
                                type: 2,
                                title:"图书信息管理详情",
                                area: ['400px', '650px'],
                                skin: 'layui-layer-rim layui-layer-molv', //加上边框
                                content:basePath+'bookMsgDetail'
                            });
                        }
                        break;
                    case 'delete':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('你确定要删除这些数据？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                var idList=[];
                                for(var i=0;i<data.length;i++) {
                                    idList[i]=data[i].bookMessageId;
                                    console.log("data[i].bookMessageId:");
                                    console.log(data[i].bookMessageId);
                                }
                                var mydata={
                                    list:idList
                                };
                                console.log(idList);
                                $.ajax({
                                    url: basePath + 'tushuxinxiguanli/deleteBookMsg'
                                    ,contentType:'application/json'
                                    ,data:JSON.stringify(mydata)
                                    ,type:'post'
                                    ,success:function (res) {
                                        if(res.code==0){
                                            layui.table.reload('table1');
                                            layer.msg('操作成功', {icon: 1});
                                        }else{
                                            layer.alert("错误原因:"+res.msg, {icon: 5});
                                        }
                                    }
                                    ,error:function (obj) {
                                        console.log('请求错误,错误的原因为:'+obj.msg)
                                    }
                                });
                            });
                        }
                        break;
                }
            });
        //监听单元格编辑
        table.on('edit(test1)',
            function (obj) {
                var value = obj.value //得到修改后的值
                    ,data = obj.data //得到所在行所有键值
                    ,field = obj.field; //得到字段
                console.log(data);
                $.ajax({
                    type: 'post'
                    ,url: basePath+"tushuxinxiguanli/updateBookMsg",
                    // ajax请求路径
                    data: data,
                    success: function (data) {
                        layer.msg('[ID: ' + data[0].id + '] ' + field + ' 字段更改为：' + value);
                    }
                });

            });
        //监听行工具事件
        table.on('tool(test1)',
            function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'detail') {
                    actionType='detail';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"图书信息管理详情",
                        area: ['400px', '650px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'bookMsgDetail'
                    });
                } else if (layEvent === 'del') {
                    layer.confirm('真的删除行么',
                        function (index) {
                            var idList=[];
                            idList.push(data.bookMessageId);
                            var mydata={
                                list:idList
                            };
                            $.ajax({
                                url: basePath + 'tushuxinxiguanli/deleteBookMsg'
                                ,contentType:'application/json'
                                , data:JSON.stringify(mydata)
                                ,type:'post'
                                ,success:function (res) {
                                    if(res.code==0){
                                        layui.table.reload('table1');
                                        layer.msg('操作成功', {icon: 1});
                                    }else{
                                        layer.alert("错误原因:"+res.msg, {icon: 5});
                                    }
                                }
                                ,error:function (obj) {
                                    console.log('请求错误,错误的原因为:'+obj.msg)
                                }
                            });
                            //向服务端发送删除指令
                        });
                } else if (layEvent === 'edit') {
                    actionType='edit';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"图书信息管理详情",
                        area: ['400px', '650px'],
                        skin: 'layui-layer-molv', //加上边框
                        content:basePath+'bookMsgDetail'
                    });
                }
            });
        //搜索按钮
        $("#bookMsgSearch").click(function (data) {
            table.reload('table1',{
                url:basePath+'tushuxinxiguanli'//数据接口
                ,where:{
                    bookMessageId: $("#bookMsgForm input[name='bookMessageId']").val()
                    ,name: $("#bookMsgForm [name='name']").val()
                    ,author:  $("#bookMsgForm [name='author']").val()
                    ,kindId: $("#bookMsgForm [name='kindId']").val()
                    ,publisher: $("#bookMsgForm [name='publisher']").val()
                    ,bookNum: $("#bookMsgForm [name='bookNum']").val()
                }
            })
        });
        //出入库管理
        //table2
        table.on('toolbar(test2)',
            function (obj) {
                var checkStatus = table.checkStatus(obj.config.id),
                    data = checkStatus.data; //获取选中的数据
                console.log("checkStatus.data:");
                console.log(checkStatus.data);
                switch (obj.event) {
                    case 'add':
                        actionType = 'add';
                        layer.open({
                            type: 2,
                            title:"新增图书",
                            area: ['400px', '550px'],
                            skin: 'layui-layer-rim layui-layer-molv', //加上边框
                            content:basePath+'bookDetail'
                        });
                        break;
                    case 'update':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else if (data.length > 1) {
                            layer.msg('只能同时编辑一个');
                        } else {
                            dataForChild = data[0];
                            actionType='edit';
                            layer.open({
                                type: 2,
                                title:"图书信息管理详情",
                                area: ['400px', '590px'],
                                skin: 'layui-layer-rim layui-layer-molv', //加上边框
                                content:basePath+'bookDetail'
                            });
                        }
                        break;
                    case 'delete':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('你确定要删除这些数据？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                var idList=[];
                                for(var i=0;i<data.length;i++) {
                                    idList[i]=data[i].bookId;
                                    console.log("data[i].bookMessageId:");
                                    console.log(data[i].bookId);
                                }
                                var mydata={
                                    list:idList
                                }
                                console.log(idList);
                                $.ajax({
                                    url: basePath + 'churukuguanli/deleteBook'
                                    ,contentType:'application/json'
                                    , data: JSON.stringify(mydata)
                                    ,type:'post'
                                    ,success:function (res) {
                                        if(res.code==0){
                                            layui.table.reload('table2');
                                            layer.msg('操作成功', {icon: 1});
                                        }else{
                                            layer.alert("错误原因:"+res.msg, {icon: 5});
                                        }
                                    }
                                    ,error:function (obj) {
                                        console.log('请求错误,错误的原因为:'+obj.msg)
                                    }
                                });
                            });
                        }
                        break;
                }
            });
        //监听单元格编辑
        table.on('edit(test2)',
            function (obj) {
                var value = obj.value //得到修改后的值
                    ,data = obj.data //得到所在行所有键值
                    ,field = obj.field; //得到字段
                console.log(data);
                layui.use('jquery',
                    function () {
                        var $ = layui.$;
                        $.ajax({
                            type: 'post'
                            ,url: basePath+"churukuguanli/updateBook",
                            // ajax请求路径
                            data: data,
                            success: function (data) {
                                layer.msg('[ID: ' + data[0].id + '] ' + field + ' 字段更改为：' + value);
                            }
                        });
                    });
            });
        //监听行工具事件
        table.on('tool(test2)',
            function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值.
                if(layEvent=='return'){
                    actionType='return';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"图书信息管理详情",
                        area: ['400px', '550px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'bookDetail'
                    });
                } else if(layEvent=='borrow'){
                    actionType='borrow';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"图书信息管理详情",
                        area: ['400px', '550px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'bookDetail'
                    });
                } else if (layEvent == 'detail') {
                    actionType='detail';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"图书信息管理详情",
                        area: ['400px', '550px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'bookDetail'
                    });
                } else if (layEvent == 'del') {
                    layer.confirm('真的删除行么',
                        function (index) {
                            var idList=[];
                            idList.push(data.bookId);
                            var mydata={
                                list:idList
                            };
                            $.ajax({
                                url: basePath + 'churukuguanli/deleteBook'
                                ,contentType:'application/json'
                                , data: JSON.stringify(mydata)
                                ,type:'post'
                                ,success:function (res) {
                                    if(res.code==0){
                                        layui.table.reload('table2');
                                        layer.msg('操作成功', {icon: 1});
                                    }else{
                                        layer.alert("错误原因:"+res.msg, {icon: 5});
                                    }
                                }
                                ,error:function (obj) {
                                    console.log('请求错误,错误的原因为:'+obj.msg)
                                }
                            });
                            //向服务端发送删除指令
                        });
                } else if (layEvent == 'edit') {
                    actionType='edit';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"图书信息管理详情",
                        area: ['400px', '590px'],
                        skin: 'layui-layer-molv', //加上边框
                        content:basePath+'bookDetail'
                    });
                }
            });
        //搜索按钮
        $("#bookSearch").click(function (data) {
            table.reload('table2',{
                url:basePath+'churukuguanli'//数据接口
                ,where:{
                    bookId: $("#bookForm [name='bookId']").val()
                    ,bookMessageName: $("#bookForm [name='bookMessageName']").val()
                    ,author: $("#bookForm [name='author']").val()
                    ,qualityId: $("#bookForm [name='qualityId']").val()
                    ,publisher: $("#bookForm [name='publisher']").val()
                    ,bookStatusId:$("#bookForm [name='bookStatusId']").val()
                    ,code: $("#bookForm [name='code']").val()
                }
            })
        });
        //公告管理
        //table3
        table.on('toolbar(test3)',
            function (obj) {
                var checkStatus = table.checkStatus(obj.config.id),
                    data = checkStatus.data; //获取选中的数据
                console.log("checkStatus.data:");
                console.log(checkStatus.data);
                switch (obj.event) {
                    case 'add':
                        actionType = 'add';
                        layer.open({
                            type: 2,
                            title:"新增公告",
                            area: ['600px', '400px'],
                            skin: 'layui-layer-rim layui-layer-molv', //加上边框
                            content:basePath+'noticeDetail'
                        });
                        break;
                    case 'update':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else if (data.length > 1) {
                            layer.msg('只能同时编辑一个');
                        } else {
                            dataForChild = data[0];
                            actionType='edit';
                            layer.open({
                                type: 2,
                                title:"公告信息管理详情",
                                area: ['600px', '400px'],
                                skin: 'layui-layer-rim layui-layer-molv', //加上边框
                                content:basePath+'noticeDetail'
                            });
                        }
                        break;
                    case 'delete':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('你确定要删除这些数据？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                var idList=[];
                                for(var i=0;i<data.length;i++) {
                                    idList[i]=data[i].noticeId;
                                    console.log("data[i].noticeId:");
                                    console.log(data[i].noticeId);
                                }
                                var mydata={
                                    list:idList
                                };
                                console.log(idList);
                                $.ajax({
                                    url: basePath + 'gonggaoguanli/deleteNotice'
                                    ,contentType:'application/json'
                                    , data:JSON.stringify(mydata)
                                    ,type:'post'
                                    ,success:function (res) {
                                        if(res.code==0){
                                            layui.table.reload('table3');
                                            layer.msg('操作成功', {icon: 1});
                                        }else{
                                            layer.alert("错误原因:"+res.msg, {icon: 5});
                                        }
                                    }
                                    ,error:function (obj) {
                                        console.log('请求错误,错误的原因为:'+obj.msg)
                                    }
                                });
                            });
                        }
                        break;
                }
            });
        //监听单元格编辑
        table.on('edit(test3)',
            function (obj) {
                var value = obj.value //得到修改后的值
                    ,data = obj.data //得到所在行所有键值
                    ,field = obj.field; //得到字段
                console.log(data);
                layui.use('jquery',
                    function () {
                        var $ = layui.$;
                        $.ajax({
                            type: 'post'
                            ,url: basePath+"gonggaoguanli/updateNotice",
                            // ajax请求路径
                            data: data,
                            success: function (data) {
                                layer.msg('[ID: ' + data[0].id + '] ' + field + ' 字段更改为：' + value);
                            }
                        });
                    });
            });
        //监听行工具事件
        table.on('tool(test3)',
            function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'detail') {
                    actionType='detail';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"公告信息管理详情",
                        area: ['600px', '400px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'noticeDetail'
                    });
                } else if (layEvent === 'del') {
                    layer.confirm('真的删除行么',
                        function (index) {
                            var idList=[];
                            idList.push(data.noticeId);
                            var mydata={
                                list:idList
                            }
                            $.ajax({
                                url: basePath + 'gonggaoguanli/deleteNotice'
                                ,contentType:'application/json'
                                , data: JSON.stringify(mydata)
                                ,type:'post'
                                ,success:function (res) {
                                    if(res.code==0){
                                        layui.table.reload('table3');
                                        layer.msg('操作成功', {icon: 1});
                                    }else{
                                        layer.alert("错误原因:"+res.msg, {icon: 5});
                                    }
                                }
                                ,error:function (obj) {
                                    console.log('请求错误,错误的原因为:'+obj.msg)
                                }
                            });
                            //向服务端发送删除指令
                        });
                } else if (layEvent === 'edit') {
                    actionType='edit';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"公告信息管理详情",
                        area: ['600px', '400px'],
                        skin: 'layui-layer-molv', //加上边框
                        content:basePath+'noticeDetail'
                    });
                }
            });
        //搜索按钮
        $("#noticeSearch").click(function (data) {
            table.reload('table3',{
                url:basePath+'gonggaoguanli'//数据接口
                ,where:{
                    noticeId: $("#noticeForm [name='noticeId']").val()
                    , beginTime: noticeTime.beginTime
                    , endTime: noticeTime.endTime
                }
            })
        });

        //用户管理
        //table4
        table.on('toolbar(test4)',
            function (obj) {
                var checkStatus = table.checkStatus(obj.config.id),
                    data = checkStatus.data; //获取选中的数据
                console.log("checkStatus.data:");
                console.log(checkStatus.data);
                switch (obj.event) {
                    case 'add':
                        actionType = 'add';
                        layer.open({
                            type: 2,
                            title:"新增用户",
                            area: ['400px', '590px'],
                            skin: 'layui-layer-rim layui-layer-molv', //加上边框
                            content:basePath+'userDetail'
                        });
                        break;
                    case 'update':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else if (data.length > 1) {
                            layer.msg('只能同时编辑一个');
                        } else {
                            dataForChild = data[0];
                            actionType='edit';
                            layer.open({
                                type: 2,
                                title:"用户信息管理详情",
                                area: ['400px', '590px'],
                                skin: 'layui-layer-rim layui-layer-molv', //加上边框
                                content:basePath+'userDetail'
                            });
                        }
                        break;
                    case 'delete':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('你确定要删除这些数据？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                var idList=[];
                                for(var i=0;i<data.length;i++) {
                                    idList[i]=data[i].userId;
                                    console.log("data[i].userId:");
                                    console.log(data[i].userId);
                                }
                                var mydata={
                                    list:idList
                                }
                                console.log(idList);
                                $.ajax({
                                    url: basePath + 'yonghuguanli/deleteUser'
                                    ,contentType:'application/json'
                                    , data:JSON.stringify(mydata)
                                    ,type:'post'
                                    ,success:function (res) {
                                        if(res.code==0){
                                            layui.table.reload('table4');
                                            layer.msg('操作成功', {icon: 1});
                                        }else{
                                            layer.alert("错误原因:"+res.msg, {icon: 5});
                                        }
                                    }
                                    ,error:function (obj) {
                                        console.log('请求错误,错误的原因为:'+obj.msg)
                                    }
                                });
                            });
                        }
                        break;
                }
            });
        //监听单元格编辑
        table.on('edit(test4)',
            function (obj) {
                var value = obj.value //得到修改后的值
                    ,data = obj.data //得到所在行所有键值
                    ,field = obj.field; //得到字段
                if(obj.data.sex=="男"||obj.data.sex==1){
                    obj.data.sex=1;
                } else if(obj.data.sex=="女"||obj.data.sex==0){
                    obj.data.sex=0;
                }else {
                    obj.data.sex=3;
                }
                console.log(data);
                layui.use('jquery',
                    function () {
                        var $ = layui.$;
                        $.ajax({
                            type: 'post'
                            ,url: basePath+"yonghuguanli/updateUser",
                            // ajax请求路径
                            data: data,
                            success: function (data) {
                                layer.msg('[ID: ' + data[0].id + '] ' + field + ' 字段更改为：' + value);
                            }
                        });
                    });
            });
        //监听行工具事件
        table.on('tool(test4)',
            function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'detail') {
                    actionType='detail';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"用户信息管理详情",
                        area: ['400px', '590px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'userDetail'
                    });
                } else if (layEvent === 'del') {
                    layer.confirm('真的删除行么',
                        function (index) {
                            var idList=[];
                            idList.push(data.userId);
                            var mydata={
                                list:idList
                            };
                            $.ajax({
                                url: basePath + 'yonghuguanli/deleteUser'
                                ,contentType:'application/json'
                                , data: JSON.stringify(mydata)
                                ,type:'post'
                                ,success:function (res) {
                                    if(res.code==0){
                                        layui.table.reload('table4');
                                        layer.msg('操作成功', {icon: 1});
                                    }else{
                                        layer.alert("错误原因:"+res.msg, {icon: 5});
                                    }
                                }
                                ,error:function (obj) {
                                    console.log('请求错误,错误的原因为:'+obj.msg)
                                }
                            });
                            //向服务端发送删除指令
                        });
                } else if (layEvent === 'edit') {
                    actionType='edit';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"用户信息管理详情",
                        area: ['400px', '590px'],
                        skin: 'layui-layer-molv', //加上边框
                        content:basePath+'userDetail'
                    });
                }
            });
        //搜索按钮
        $("#userSearch").click(function (data) {

            table.reload('table4',{
                url:basePath+'yonghuguanli'//数据接口
                ,where:{
                    userId: $("#userForm [name='userId']").val()
                    ,realName: $("#userForm [name='realName']").val()
                    ,nickName:$("#userForm [name='nickName']").val()
                    ,birthday: $("#userForm [name='birthday']").val()
                    ,email:$("#userForm [name='email']").val()
                    ,grade: $("#userForm [name='grade']").val()
                    ,department: $("#userForm [name='department']").val()
                    ,major: $("#userForm [name='major']").val()
                    ,roleId: $("#userForm [name='roleId']").val()
                }
            })
        });
        //反馈管理
        //table5
        table.on('toolbar(test5)',
            function (obj) {
                var checkStatus = table.checkStatus(obj.config.id),
                    data = checkStatus.data; //获取选中的数据
                console.log("checkStatus.data:");
                console.log(checkStatus.data);
                switch (obj.event) {
                    case 'add':
                        // actionType = 'add';
                        // layer.open({
                        //     type: 2,
                        //     title:"新增建议",
                        //     area: ['400px', '500px'],
                        //     skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        //     content:basePath+'adviceDetail'
                        // });
                        layer.msg("不可新增");
                        break;
                    case 'update':
                        // if (data.length === 0) {
                        //     layer.msg('请选择一行');
                        // } else if (data.length > 1) {
                        //     layer.msg('只能同时编辑一个');
                        // } else {
                        //     dataForChild = data[0];
                        //     actionType='edit';
                        //     layer.open({
                        //         type: 2,
                        //         title:"建议信息管理详情",
                        //         area: ['400px', '500px'],
                        //         skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        //         content:basePath+'adviceDetail'
                        //     });
                        // }
                        layer.msg("不可修改");
                        break;
                    case 'delete':
                        if (data.length === 0) {
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('你确定要删除这些数据？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                var idList=[];
                                for(var i=0;i<data.length;i++) {
                                    idList[i]=data[i].adviceId;
                                    console.log("data[i].adviceId:");
                                    console.log(data[i].adviceId);
                                }
                                var mydata = {
                                    list: idList
                                };
                                console.log(idList);
                                $.ajax({
                                    url: basePath + 'fankuiguanli/deleteAdvice'
                                    ,contentType:'application/json'
                                    , data: JSON.stringify(mydata)
                                    ,type:'post'
                                    ,success:function (res) {
                                        if(res.code==0){
                                            layui.table.reload('table5');
                                            layer.msg('操作成功', {icon: 1});
                                        }else{
                                            layer.alert("错误原因:"+res.msg, {icon: 5});
                                        }
                                    }
                                    ,error:function (obj) {
                                        console.log('请求错误,错误的原因为:'+obj.msg)
                                    }
                                });
                            });
                        }
                        break;
                }
            });
        //监听单元格编辑
        table.on('edit(test5)',
            function (obj) {
                var value = obj.value //得到修改后的值
                    ,data = obj.data //得到所在行所有键值
                    ,field = obj.field; //得到字段
                console.log(data);
                layui.use('jquery',
                    function () {
                        var $ = layui.$;
                        $.ajax({
                            type: 'post'
                            ,url: basePath+"fankuiguanli/updateAdvice",
                            // ajax请求路径
                            data: data,
                            success: function (data) {
                                layer.msg('[ID: ' + data[0].id + '] ' + field + ' 字段更改为：' + value);
                            }
                        });
                    });
            });
        //监听行工具事件
        table.on('tool(test5)',
            function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'detail') {
                    actionType='detail';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"建议信息管理详情",
                        area: ['400px', '500px'],
                        skin: 'layui-layer-rim layui-layer-molv', //加上边框
                        content:basePath+'adviceDetail'
                    });
                } else if (layEvent === 'del') {
                    layer.confirm('真的删除行么',
                        function (index) {
                            var idList=[];
                            idList.push(data.adviceId);
                            var mydata={
                                list:idList
                            };
                            $.ajax({
                                url: basePath + 'fankuiguanli/deleteAdvice'
                                ,contentType:'application/json'
                                , data: JSON.stringify(mydata)
                                ,type:'post'
                                ,success:function (res) {
                                    if(res.code==0){
                                        layui.table.reload('table5');
                                        layer.msg('操作成功', {icon: 1});
                                    }else{
                                        layer.alert("错误原因:"+res.msg, {icon: 5});
                                    }
                                }
                                ,error:function (obj) {
                                    console.log('请求错误,错误的原因为:'+obj.msg)
                                }
                            });
                            //向服务端发送删除指令
                        });
                } else if (layEvent === 'edit') {
                    actionType='edit';
                    dataForChild=data;
                    layer.open({
                        type: 2,
                        title:"建议信息管理详情",
                        area: ['400px', '500px'],
                        skin: 'layui-layer-molv', //加上边框
                        content:basePath+'adviceDetail'
                    });
                }
            });
        //搜索按钮
        $("#adviceSearch").click(function (data) {
            table.reload('table5',{
                url:basePath+'fankuiguanli'//数据接口
                ,where:{
                    adviceId: $("#adviceForm [name='adviceId']").val()
                    ,userId: $("#adviceForm [name='userId']").val()
                    ,realName:$("#adviceForm [name='realName']").val()
                }
            })
        });
        //点击导航条时，渲染对应的表格
        $('.tablePage').on('click', function () {
            var othis = $(this), type = othis.data('type');
            tableActive[type] ? tableActive[type].call(this, othis) : '';
        });

    });