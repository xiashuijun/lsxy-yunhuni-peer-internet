<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/inc/import.jsp" %>
<!DOCTYPE html>
<html>

<!-- header -->
<head>
    <%@include file="/inc/meta.jsp" %>

</head>
<body>
<section class="vbox">
    <%@include file="/inc/headerNav.jsp"%>
    <section class='aside-section'>
        <section class="hbox stretch">
            <!-- .aside -->
            <aside class="bg-Green lter aside hidden-print" id="nav"><%@include file="/inc/leftMenu.jsp"%></aside>
            <!-- /.aside -->

        <section id="content">
            <section class="hbox stretch">
                <!-- 如果没有三级导航 这段代码注释-->
                <aside class="bg-green lter aside-sm hidden-print ybox" id="subNav">
                    <section class="vbox">
                        <div class="wrapper header"><span class="margin_lr"></span><span class="margin_lr border-left">&nbsp;应用管理</span>
                        </div>
                        <section class="scrollable">
                            <div class="slim-scroll">
                                <!-- nav -->
                                <nav class="hidden-xs">
                                    <ul class="nav">
                                        <li>
                                            <div class="aside-li-a">
                                                <a href="${ctx}/console/app/list">应用列表</a>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="aside-li-a active">
                                                <a href="${ctx}/console/app/index">创建应用</a>
                                            </div>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </section>
                    </section>
                </aside>
                <aside>
                    <section class="vbox xbox">
                        <!-- 如果没有三级导航 这段代码注释-->
                        <div class="head-box"><a href="#subNav" data-toggle="class:hide"> <i
                                class="fa fa-angle-left text"></i> <i class="fa fa-angle-right text-active"></i> </a>
                        </div>
                        <section class=" w-f application_create">
                            <div class="wrapper header">
                                <span class="border-left">&nbsp;创建应用</span></div>
                            <div class="row m-l-none m-r-none bg-light lter">
                                <div class="row">

                                    <form role="form" action="./index.html" method="post" class="register-form" id="application_create">
                                        <div class="form-group">
                                            <lable class="col-md-3 text-right">应用名称：</lable>
                                            <div class="col-md-4">
                                                <input type="text" name="name" placeholder="" class="form-control input-form limit20"/>
                                            </div>
                                        </div>
                                        <p class="tips">20字符以内，符合<a href="">应用审核规范</a></p>

                                        <div class="form-group">
                                            <lable class="col-md-3 text-right">应用描述：</lable>
                                            <div class="col-md-4">
                                                <input type="text" name="description" placeholder="" class="form-control input-form limit20"/>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <lable class="col-md-3 text-right">应用类型：</lable>
                                            <div class="col-md-4 ">
                                                <select name="type" class="form-control notEmpty">
                                                    <option value="">请选择应用类型</option>
                                                    <option value="餐饮">餐饮</option>
                                                    <option value="金融">金融</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <lable class="col-md-3 text-right">所属行业：</lable>
                                            <div class="col-md-4 ">
                                                <select name="industry"  class="form-control notEmpty">
                                                    <option value="">请选择应用类型</option>
                                                    <option value="餐饮">餐饮</option>
                                                    <option value="金融">金融</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <lable class="col-md-3 text-right">服务器白名单：</lable>
                                            <div class="col-md-4">
                                                <input type="text" name="whiteList" placeholder="" class="form-control input-form "/>
                                            </div>
                                        </div>
                                        <p class="tips">
                                            允许IP地址，以英文输入法分号分隔，例如，8.8.8.8; 8.8.8.8 设定白名单地址后，云呼你服务器在识别该应用请求时将只接收白名单内服务器发送的请求，能有效提升账号安全性。如未设置默认不生效
                                        </p>
                                        <div class="form-group">
                                            <lable class="col-md-3 text-right">回调URL：</lable>
                                            <div class="col-md-4">
                                                <input type="text" name="url" placeholder="" class="form-control input-form"/>
                                            </div>
                                        </div>

                                        <p class="tips">
                                            <input type="checkbox" name="isAuth"> 鉴权 (网络直拨，回拨，互联网语音，视频通话会涉及鉴权流程，勾选但未实现会呼叫失效)
                                            <a href="">回调说明文档</a> </a>
                                        </p>
                                        <div class="form-group min-height20">
                                            <span class="hr text-label" ><strong>选择服务:</strong></span>
                                        </div>
                                        <div class="form-group app-createbox border-bottom">
                                            <lable class="col-md-3 text-right"></lable>
                                            <div class="col-md-9" >
                                                <p><strong>基础语音服务</strong></p>
                                                <p><input type="checkbox" name="isVoiceDirectly" checked='checked'> 启用 &nbsp;&nbsp;语音外呼(嵌入CRM、OA、呼叫中心等产品中发起通话)</p>
                                                <p><input type="checkbox" name="isVoiceCallback" checked='checked'> 启用 &nbsp;&nbsp;双向回拨(以不同的通话方式实现茂名通话功能，保护双方号码隐私)</p>

                                            </div>
                                        </div>
                                        <div class="form-group app-createbox" >
                                            <lable class="col-md-3 text-right"></lable>
                                            <div class="col-md-9" >
                                                <p><strong>高级语音定制服务</strong></p>
                                                <p><input type="checkbox" name="isSessionService" checked> 启用 &nbsp;&nbsp;<a href="">会议服务</a>(可与互联网会议、视频 会议融合参会，提供丰富的会议管理功能)</p>
                                                <p><input type="checkbox" name="isRecording" checked> 启用 &nbsp;&nbsp;<a>录音服务</a>(以不同的通话方式实现茂名通话功能，保护双方号码隐私)</p>
                                                <p><input type="checkbox" name="isVoiceValidate" checked> 启用 &nbsp;&nbsp;<a>语音验证码</a>(嵌入CRM、OA、呼叫中心等产品中发起通话)</p>
                                                <p><input type="checkbox" name="isIvrService" checked> 启用 &nbsp;&nbsp;<a>IVR定制服务</a>(以不同的通话方式实现茂名通话功能，保护双方号码隐私)</p>
                                                <div class="tips ml-36">
                                                    <p class="app-tips ">IVR定制服务开启后，该应用将产生100元/月的功能费，上线时开始收取，多个应用开启并上线会叠加收费</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="col-md-9">
                                                <a id="validateBtn" class="validateBtnNormal btn btn-primary  btn-form">创建</a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                        </section>
                    </section>
                </aside>
            </section>
        </section>
    </section>
</section>
</section>
<div class="tips-toast"></div>
<div class="tips-toast"></div>
<%@include file="/inc/footer.jsp"%>
<script type="text/javascript" src='${resPrefixUrl }/js/application/create.js'></script>

<script>
    $('#validateBtn').click(function(){
        var result = $('#application_create').data('bootstrapValidator').isValid();
        if(result==true){
            //提交表单
            var tempVal = $('#application_create').serialize().split("&");
            var dataVal = { '${_csrf.parameterName}':'${_csrf.token}'};
            for(var i=0;i<tempVal.length;i++){
                var temp = tempVal[i].split("=");
                if(temp[0].indexOf("is")==0){
                    dataVal[temp[0]]=temp[1]=='on'?'1':'0';
                }else{
                    dataVal[temp[0]]=decodeURI(decodeURI((temp[1]),"UTF-8"));
                }
            }
            $.ajax({
                url : "${ctx}/console/app/create",
                type : 'post',
                async: false,//使用同步的方式,true为异步方式
                data :dataVal,
                dataType: "json",
                success : function(data){
                    showtoastAppIndex(data.msg);
                },
                fail:function(){
                    showtoast('网络异常，请稍后重试');
                }
            });
        }
        else{
            $('#application_create').bootstrapValidator('validate');
        }

    });
    function showtoastAppIndex(tips) {
        $('.tips-toast').css('display','block').html(tips);
        setTimeout("hidetoastAppIndex()",2000);
    }
    function hidetoastAppIndex(){
        $('.tips-toast').fadeOut(1000);
        window.location.href="${ctx}/console/app/list";
    }
</script>

</body>
</html>