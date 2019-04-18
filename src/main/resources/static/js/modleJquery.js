/**
 * @fileoverview函数模块
 * @author (jquery工具库)
 * @version 0.1
 */

;(function(window,document,undefinded){
	var MOD = {};
	MOD.version = '0.1';
	window.MOD =  MOD;
	/**
	 * @description 实用函数集合 
	 */
	MOD.Util = {
        /**
         * @description 1
         * @param {string} startTime
		 * @param {string} endTime
		 * @param {string} diffType
         * @returns 计算两个日期之间的天数差
         */
        GetDateDiff:function(startTime, endTime, diffType) {
			//将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
			startTime = startTime.replace(/-/g, "/");
			endTime = endTime.replace(/-/g, "/");
			//将计算间隔类性字符转换为小写
			diffType = diffType.toLowerCase();
			var sTime = new Date(startTime); //开始时间
			var eTime = new Date(endTime); //结束时间
			//作为除数的数字
			var divNum = 1;
			switch (diffType) {
				case "second":
					divNum = 1000;		break;
				case "minute":
					divNum = 1000 * 60;	break;
				case "hour":
					divNum = 1000 * 3600;break;
				case "day":
					divNum = 1000 * 3600 * 24;	break;
				default:	break;
			}
			return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum)); //17jquery.com
		}
		/**
		 * @description 1
		 * @param {string} str
		 * @returns 字符串
		 */
		,trim:function(str){
			return str.trim?str.trim():str.replace(/^\s+|\s+$/g,'');
		}
        /**
         * @description 输入的数据必须为大于等于0的整数
         * @param {string} value
         * @returns value
         */
		,integerCheck:function(value){
			if(!/^(0|[1-9][0-9]*)$/.test(value))  value = '';
            return value;
		}
        /**
         * @description 输入的数据必须为大于0的整数
         * @param {string} value
         * @returns value
         */
        ,positiveCheck:function(value){
            if(value.length==1) value = value.replace(/[^0-9]/g,'');
            else value= value.replace(/\D/g,'');
        }
        ,multiImagesForCompress:function (demoListView,upload,successFiles) {
            //var successFiles = '';
            var uploadListIns = upload.render({
                elem: '#testList'
                ,url: basePath + '/student/uploadMaterial'
                ,accept: 'images'
                ,acceptMime: 'image/*'
                //,size:1024
                ,data:{
                    courseId:parent.parent.dataForChild.id
                    , materialId: parent.dataForChild.id
                }
                ,multiple: true
                ,auto: false
                ,bindAction: '#testListAction'
                ,choose: function(obj){
                    var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                    $('.forImags').removeClass('display');
                    //读取本地文件
                    obj.preview(function(index, file, result){
                        var fileSize ,tr;
                        MOD.Util.photoCompress(file, {
                            quality: 0.5,
                        }, function(base64Codes) {
                            var bl = MOD.Util.convertBase64UrlToBlob(base64Codes);

                            obj.resetFile(index, bl, file.name);
                            fileSize = (bl.size/1014).toFixed(1);
                            tr = $(['<tr id="upload-' + index + '">'
                                , '<td>' + file.name + '</td>'
                                , '<td>' + fileSize + 'kb</td>'
                                // ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                                , '<td>等待上传</td>'
                                , '<td>'
                                , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                                , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                                , '</td>'
                                , '</tr>'].join(''));
                            //单个重传
                            tr.find('.demo-reload').on('click', function(){
                                obj.upload(index, file);
                            });

                            //删除
                            tr.find('.demo-delete').on('click', function(){
                                delete files[index]; //删除对应的文件
                                tr.remove();
                                uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                            });
                            demoListView.append(tr);
                        });

                    });
                }
                ,done: function(res, index, upload){
                    if(res.code == 0){ //上传成功
                        successFiles.push(res.fileNames) ;
                        var tr = demoListView.find('tr#upload-'+ index)
                            ,tds = tr.children();
                        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                        tds.eq(3).html(''); //清空操作
                        return delete this.files[index]; //删除文件队列已经上传成功的文件
                    }
                    this.error(index, upload);
                }
                ,error: function(index, upload){
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                    tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
                }
            });
            //return successFiles;
        }

        ,photoCompress:function(file, w, objDiv) {
            var ready = new FileReader();
            /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
            ready.readAsDataURL(file);
            ready.onload = function() {
                var re = this.result;
                MOD.Util.canvasDataURL(re, w, objDiv);
            }
        }
        ,canvasDataURL :function(path, obj, callback) {
            var img = new Image();
            img.src = path;
            img.onload = function() {
                var that = this;
                // 默认按比例压缩
                var w = that.width,
                    h = that.height,
                    scale = w / h;
                w = obj.width || w;
                h = obj.height || (w / scale);
                var quality = 0.5; // 默认图片质量为0.7
                //生成canvas
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');
                // 创建属性节点
                var anw = document.createAttribute("width");
                anw.nodeValue = w;
                var anh = document.createAttribute("height");
                anh.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);
                ctx.drawImage(that, 0, 0, w, h);
                // 图像质量
                if(obj.quality && obj.quality <= 1 && obj.quality > 0) {
                    quality = obj.quality;
                }
                // quality值越小，所绘制出的图像越模糊
                var base64 = canvas.toDataURL('image/jpeg', quality);
                // 回调函数返回base64的值
                callback(base64);
            }
        }
        ,convertBase64UrlToBlob:function(urlData) {
            var arr = urlData.split(','),
                mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]),
                n = bstr.length,
                u8arr = new Uint8Array(n);
            while(n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], {
                type: mime
            });
        }
	};
	MOD.Display = {
        weekDay : function (weekDay) {
        	var day;
			switch(parseInt(weekDay)){
				case 0 : day = '一';break;
				case 1 : day = '二';break;
				case 2 : day = '三';break;
				case 3 : day = '四';break;
				case 4 : day = '五';break;
				case 5 : day = '六';break;
				case 6 : day = '日';break;
				default :day = '无';break;
			}
			return day;
        }
	};
	MOD.Form=   {
		 fillForm:function($form,data){
			var jsonObj = data;		    
		    if(typeof jsonObj === 'string' )
		        jsonObj = $.parseJSON(data);		
		    //遍历json字符串
		    for(var key in jsonObj){
		        //var str = jsonObj[key];
		        $("[name=" + key + "]", $form).val(jsonObj[key]);
		    }
		}
		,fillSelect:function($select,data,selectId,selectName){
            $select.html('');
			for(var length= 0;length < data.length;length++)
				$select.append('<option value="'+data[length][selectId]+'">'+ 
				     data[length][selectName] + '</option>');
			//form.render('select');	
		}
		,fillImagesOnlyRead : function($list,data){//只预览图片
			if(typeof data === 'undefined' || data === ''){
				$list.append('<input type="text" class="layui-input" value="无！" readonly/>');
			}
			else{
				data = data.split(',');
				for(var count=0;count<data.length-1;count++){
					$list.append('<div class="layui-input-inline"><img src="'
						+basePath+'/upload/showActPic?fileName='
						+data[count]+'"/></div>' );
				}
			}
			//form.render();
		}
		,fileBASE64ImageOnlyRead :function ($list,data) {  //预览base64图片
            if(typeof data === 'undefined' || data === ''){
                // $list.append('<input type="text" class="layui-input" value="无！" readonly/>');
                $list.append('<h3 style="color:orangered">未上传任何图片</h3>')
            }
            else{
                data = data.split('^');
                // console.log(data.length)
                for(var count=0;count<data.length;count++){
                    $list.append('<div class="layui-input-inline"><img src="' + data[count] + '"/></div>');
                }
            }
        }
        ,fillImages:function($list,data,code,name) {//预览图片可修改
			if(typeof name  === 'undefined' || name === '')
				name = 'deleteImgs';
            if (typeof data === 'undefined' || data === '') {
                $list.append('<input type="text" class="layui-input" value="无！" readonly/>');
            }
            else {
                data = data.split(',');
                if (parent.actionType === 1 || parent.actionType === 3 || parent.actionType === 4) {
                    for (var count = 0; count < data.length; count++) {
                        if(data[count] !== '')
                            $list.append('<div class="layui-input-inline"><img src="'
                                + basePath + '/upload/uploadActPic='
                                + data[count] + '&file='+code + '"/></div>');
                    }
                }
                else {
                    for (var count = 0; count < data.length ; count++) {
                        $list.append('<div class="layui-input-inline"><div><img src="'
                            + basePath + '/upload/showFile?fileName='
                            + data[count] + '&code='+code + '"/></div>'
                            + '<input type="checkbox" name="'+name+'" title="删除" lay-skin="primary"/></div>');
                    }
                }
            }
        }
		,fillCheckbox:function($checkbox,data,checkboxId,checkboxName){
            $checkbox.html("");
            for(var count=0; count < data.length; count++){
                $checkbox.append('<input type="checkbox" name="'+checkboxId+'['
                    + data[count][checkboxId] + ']" value="'
                    + data[count][checkboxId] + '" title="'
                    + data[count][checkboxName] + '"/>');
            }
		}
		,fillSchoolYear:function($year,yearNum){
            $year.html("");
            $year.append('<option value="">直接选择或搜索选择</option>');
			var year = new Date(),num,current;
			year = year.getFullYear();
			for(num=0; num<yearNum; num++){
				current = year - num;
				$year.append('<option value="' + current + '">' + 
				      current + '~' + (current+1) + '</option>');
			}
			//form.render('select');
		}
		,fillweekSeq : function($select,weekNum) {
            for(var num = 1; num <= weekNum; num++){
                $select.append('<option value="' + num + '">' +
                    '第 ' + num + ' 周' + '</option>');
            }
        }
        ,fillweekDay : function($select) {
            for(var num = 0; num < 7; num++){
            	MOD.Display.weekDay(num)
                $select.append('<option value="' + num + '">' +
                    MOD.Display.weekDay(num) + '</option>');
            }
        }
        ,fillLesson : function($select,lessonNum) {
            for(var num = 1; num <= lessonNum; num++){
                $select.append('<option value="' + num + '">' +
                    '第 ' + num + ' 节' + '</option>');
            }
        }
		,displayRequired:function($form,form){
			var $this;
			$form.find('[lay-verify]').each(function(){
				$this = $(this);
				if($this.hasClass('layui-input'))  $this.addClass('highlight');
				else
					$this.find('.layui-input').addClass('highlight');
				form.render();

			});
            $form.find('input[name=teachBook]').addClass('highlight');
            //alert($('input[name=probationTeacher]').attr('lay-verify'));
		}
	};
}(window,document));
