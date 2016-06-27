<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<section class="vbox">
    <header class="bg-green bg header navbar navbar-fixed-top-xs bg-blue">
        <div class="navbar-header aside logo_b">
            <a href="#" class="navbar-brand logo_ab" data-toggle="fullscreen">
                <img src="${resPrefixUrl }/images/index/logo.png" class="m-r-sm logo_w2">
            </a>
        </div>
        <ul class="nav navbar-nav hidden-xs nav_b">
            <li class="dropdown">
                <a href="index.html" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="">商户管理控制台</span>
                </a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right hidden-xs nav-user nav_b">
            <li class="dropdown hidden-xs a-color"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-fw fa-book"></i>文档中心
            </a></li>
            <li class="hidden-xs"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-bell"></i> <span class="badge badge-sm up bg-danger m-l-n-sm count">2</span> </a>
                <section class="dropdown-menu aside-xl">
                    <section class="panel bg-white">
                        <header class="panel-heading b-light bg-light"> <strong>您有<span class="count">2</span>通知</strong> </header>
                        <div class="list-groulist-group-alt animated fadeInRight"><a href="#" class="media list-group-item"> <span class="pull-left thumb-sm"> <img src="${resPrefixUrl }/images/avatar.jpg" alt="John said" class="img-circle"> </span> <span class="media-body block m-b-none">提醒<br>
                            <small class="tmuted">10分钟以前</small></span></a><a href="#" class="media list-group-item"> <span class="media-body block m-b-none">待办<br>
                            <small class="text-muted">1小时以前</small></span></a></div>
                        <footer class="panel-footer text-sm">
                            <!-- <a href="#" class="pull&#45;right"> -->
                            <!--   <i class="fa fa&#45;cog"></i>  -->
                            <!-- </a> -->
                            <a href="#notes" data-toggle="class:show animated fadeInRight">查看所有通知</a>
                        </footer>
                    </section>
                </section>
            </li>
            <li class="dropdown a-color"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="thumb-sm avatar pull-left"> <img src="${resPrefixUrl }/images/avatar.jpg"> </span>渠道商<b class="caret"></b> </a>
                <ul class="dropdown-menu animated fadeInRight">
                    <span class="arrow top"></span>
                    <li> <a href="#">设置</a> </li>
                    <li> <a href="modal.lockme.html" data-toggle="ajaxModal" >退出</a> </li>
                </ul>
            </li>
        </ul>
    </header>