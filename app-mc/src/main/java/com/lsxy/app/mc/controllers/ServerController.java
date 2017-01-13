package com.lsxy.app.mc.controllers;

import com.lsxy.app.mc.exceptions.ScriptFileNotExistException;
import com.lsxy.app.mc.service.ScriptService;
import com.lsxy.app.mc.utils.RunShellUtil;
import com.lsxy.app.mc.utils.ShellExecuteException;
import com.lsxy.app.mc.vo.AreaNodeVO;
import com.lsxy.app.mc.vo.AreaServerHostVO;
import com.lsxy.app.mc.vo.AreaServerVO;
import com.lsxy.app.mc.vo.ServerVO;
import com.lsxy.framework.cache.manager.RedisCacheService;
import com.lsxy.framework.config.Application;
import com.lsxy.framework.config.SystemConfig;
import com.lsxy.framework.core.utils.DateUtils;
import com.lsxy.framework.core.utils.JSONUtil2;
import com.lsxy.framework.core.utils.StringUtil;
import com.lsxy.framework.web.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.lsxy.framework.core.utils.JSONUtil2.jsonToList;

/**
 * Created by tandy on 16/11/19.
 */
@Controller
public class ServerController extends AdminController{

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);
    
    @Autowired
    private RedisCacheService cacheService;

    @Autowired
    private ScriptService scriptService;

    @RequestMapping("/servers")
    public String servers(Model model){

        //查询所有服务器节点运行情况
        List<ServerVO> servers = getAllServerList();
        model.addAttribute("servers",servers);

        //查询区域服务连接情况
        List<AreaServerVO> areaServers = getAllAreaServers();
        model.addAttribute("areaServers",areaServers);

        return "servers";
    }

    @RequestMapping("/server/stop")
    @ResponseBody
    public RestResponse<String> stopServer(String host,String app){
        try {
            Application application = Application.getApplication(app);
            if(application == null) {
                throw new IllegalArgumentException(app);
            }
            String script = scriptService.prepareScript(ScriptService.SCRIPT_STOP);
            String result = RunShellUtil.run("sh "+script + " -a "+application.getModuleName()+" -h "+host+"",10);
            if(logger.isDebugEnabled()){
                logger.debug("stop completed and result is :");
                logger.debug(result);
            }
        } catch (ScriptFileNotExistException | ShellExecuteException e) {
            logger.error("update exception: " + host + ":" + app,e);
            return RestResponse.failed("00001","execute exception");
        }
        return RestResponse.success("OK");
    }

    @RequestMapping("/server/start")
    @ResponseBody
    public RestResponse<String> startServer(String host,String app,String version){
//        String script = "/opt/lsxy_yunwei/lsxy_server.sh -j start -a app-portal-api -r 1.2.0-RC3 -h p05";

        try {
            Application application = Application.getApplication(app);
            if(application == null) {
                throw new IllegalArgumentException(app);
            }
            String script = scriptService.prepareScript("start.sh");
            String result = RunShellUtil.run("sh "+script + " -a "+app+" -h " + host,10);
            if(logger.isDebugEnabled()){
                logger.debug("start completed and result is :");
                logger.debug(result);
            }
        } catch (ScriptFileNotExistException | ShellExecuteException e) {
            e.printStackTrace();
            return RestResponse.failed("00001","execute exception");
        }
        return RestResponse.success("OK");
    }

    /**
     * 更新服务
     * @param host
     * @param app
     * @return
     */
    @RequestMapping("/server/update")
    @ResponseBody
    public RestResponse<String> updateServer(String host,String app){
        try {
            Application application = Application.getApplication(app);
            if(application == null) {
                throw new IllegalArgumentException(app);
            }
            String script = scriptService.prepareScript(ScriptService.SCRIPT_UPDATE);
            String result = RunShellUtil.run("sh "+script + " -a "+application.getModuleName()+" -h "+host+"",10);
            if(logger.isDebugEnabled()){
                logger.debug("update completed and result is :");
                logger.debug(result);
            }
        } catch (ScriptFileNotExistException | ShellExecuteException e) {
            logger.error("update exception: " + host + ":" + app,e);
            return RestResponse.failed("00001","execute exception");
        }
        return RestResponse.success("OK");
    }
    /**
     * 从配置文件中获取所有服务器并根据服务器配置获取服务器当前状态并返回
     * @return
     */
    private List<ServerVO> getAllServerList() {
        String serverListJson = SystemConfig.getProperty("app.mc.serverlist","[]");
        List<ServerVO> result = jsonToList(serverListJson,ServerVO.class);
        Set<String> serversCache = cacheService.keys("monitor_*");
        if(logger.isDebugEnabled()){
            logger.debug("search monitor_* from redis and result is : {}" ,serversCache);
        }

        for(ServerVO server:result){
            String key = "monitor_"+server.getAppName()+"_"+server.getServerHost()+"_";
            if(logger.isDebugEnabled()){
                logger.debug("find server connection status, key is :{}",key);
            }
            if(serversCache.contains(key)){
                server.setStatus(ServerVO.STATUS_OK);
                //缓存中存储格式 [version startdatetime]
                String xx[] = cacheService.get(key).split(" ");
                server.setVersion(xx[0]);
                if(xx.length > 1){
                    server.setStartDt(DateUtils.formatDate(new Date(Long.parseLong(xx[1]))));
                }
            }else{
                server.setStatus(ServerVO.STATUS_FAILED);
            }
        }
        return result;
    }


    private List<AreaServerVO> getAllAreaServers(){
        String areaServerListJson = SystemConfig.getProperty("app.mc.areaservers","[]");
        if(logger.isDebugEnabled()){
            logger.debug("get all area servers using json:{}" , areaServerListJson);
        }

        List<AreaServerVO> areaServers = JSONUtil2.jsonToList(areaServerListJson,AreaServerVO.class);
        for(AreaServerVO areaServer:areaServers){
            for(AreaServerHostVO areaServerHost :areaServer.getHosts()){
                String cacheKey = "monitor_area.server_"+areaServerHost.getHostName()+"_sessionContext";
                String sessionContextJson = cacheService.get(cacheKey);

                if(logger.isDebugEnabled()){
                    logger.debug("cachekey {} result is : {}",cacheKey,sessionContextJson);
                }

                if(StringUtil.isNotEmpty(sessionContextJson)){
                    for(AreaNodeVO areaNode:areaServerHost.getNodes()){
                        if(sessionContextJson.indexOf(areaNode.getNodeId())>0){
                            areaNode.setStatus(AreaNodeVO.STATUS_OK);
                        }
                    }
                }
            }
        }
        return areaServers;
    }


}
