package com.cloudapp.web.main.call;

import com.cloudapp.core.constants.CallTypeConstant;
import com.cloudapp.core.entity.*;
import com.cloudapp.core.service.*;
import com.cloudapp.lib.HttpUtil;
import com.cloudapp.lib.TimeUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class CallThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CallThread.class);

    private ServerSocket serverSocket = null;

    @Autowired
    private CallerHostService callerHostService;

    @Resource
    private CallerHostHeartbeatService heartbeatService;

    @Autowired
    private CallerService callerService;

    @Autowired
    private CallMessageService callMessageService;

    @Autowired
    private AllCallerService allCallerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CallerCategoryService callerCategoryService;

    @Autowired
    private CallerGroupService callerGroupService;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 18, 5000, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(16), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.warn("执行自定义拒绝策略！");
        }
    });

    public void closeSocketServe(){
        try {
            if(serverSocket != null && !serverSocket.isClosed()){
                serverSocket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            logger.warn("Socket启动...");
            logger.warn("Socket启动2...");
            logger.warn("Socket启动3...");
            logger.warn("hot-fix Socket启动3...");
            serverSocket = new ServerSocket(10086);
            while (true){
                final Socket socket = serverSocket.accept();
                socket.setSoTimeout(5000);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            int len;
                            byte[] bytes = new byte[1024];
                            String str = "";
                            while ((len = inputStream.read(bytes)) != -1){
                                str += new String(bytes,0,len);
                                if(str.contains("AT+ENTM")){
                                    logger.warn("呼叫主机发送的内容异常：发送了AT+ENTM");
                                    break;
                                }
                                if(str.endsWith("\r\n")){
                                    String[] strArr = str.split("\r\n");
                                    for (String s : strArr) {
                                        handleStr(s.replaceAll("\r|\n",""));
                                    }
                                    break;
                                }
                            }
                            if(str.contains("HEART")){
                                OutputStream outputStream = socket.getOutputStream();
                                outputStream.write(String.valueOf(Calendar.getInstance().getTimeInMillis()).getBytes("UTF-8"));
                                outputStream.close();
                            }
                            inputStream.close();
                            socket.close();
                        } catch (IOException e) {
                            logger.warn("socket异常1：" + e.getMessage());
                        }
                    }
                };
                executor.execute(runnable);
            }
        } catch (Exception e) {
            logger.warn("socket异常2：" + e.getMessage());
        }
    }

    /**
     * 处理从接收器发送到服务器的字符串
     * @param s
     */
    private void handleStr(String s){
        try {
            String[] info = s.split("_");
            if(info.length == 2 && !info[0].equals("")){
                String mac = info[0];
                String code = info[1];//呼叫器编码+键码，前5位为编码，后1位时键码
                CallerHost callerHost = new CallerHost();
                callerHost.setMac(mac);
                CallerHost t_callerHost = callerHostService.getCallerHost(callerHost);//判断接收器是否已经存在
                if(t_callerHost != null){
                    if(code.equals("HEART")){//发送呼叫主机心跳
                        CallerHostHeartbeat heartbeat = heartbeatService.getByMac(mac);
                        if(heartbeat == null || heartbeat.getId() ==null){
                            heartbeat = new CallerHostHeartbeat();
                            heartbeat.setMac(mac);
                            heartbeat.sethTime(TimeUtil.now());
                            heartbeatService.save(heartbeat);
                        }else{
                            heartbeat.sethTime(TimeUtil.now());
                            heartbeatService.update(heartbeat);
                        }
                    }else if(code.length() == 6 && t_callerHost != null){//呼叫器呼叫
                        Customer customer = customerService.get(t_callerHost.getCustomerId());
                        String customerName = customer.getName();
                        if(customer.getDiffDay() >= 0){
                            AllCaller allCaller = allCallerService.getByCode(code);//判断呼叫器是否已经录入到总呼叫器表中
                            if(allCaller != null){
                                String keyCode = code.substring(5);//呼叫器键码
                                Integer customerId = t_callerHost.getCustomerId();
                                Caller caller = new Caller();
                                caller.setMac(mac);
                                caller.setCode(code);
                                Caller t_caller = callerService.getCaller(caller);//判断呼叫器是否存在
                                if(t_caller == null){//不存在就创建一个新的呼叫器
                                    CallerCategory callerCategory = new CallerCategory();
                                    callerCategory.setCustomerId(customerId);
                                    List<CallerCategory> callerCategoryList = callerCategoryService.findAll(callerCategory);
                                    if(callerCategoryList != null && callerCategoryList.size() > 0){
                                        caller.setCategoryId(callerCategoryList.get(0).getId());
                                    }else {
                                        logger.warn("异常（" + customerName + ")：" + "呼叫器类型未添加!");
                                        return;
                                    }
                                    CallerGroup callerGroup = new CallerGroup();
                                    callerGroup.setCustomerId(customerId);
                                    List<CallerGroup> callerGroupList = callerGroupService.findAll(callerGroup);
                                    if(callerGroupList != null && callerGroupList.size() > 0){
                                        caller.setGroupId(callerGroupList.get(0).getId());
                                    }else {
                                        logger.warn("异常（" + customerName + ")：" +"呼叫器分组未添加");
                                        return;
                                    }
                                    caller.setName("SPA");//默认名称为SPA
                                    callerService.save(caller);
                                    return;
                                }
                                if(keyCode.equals(CallTypeConstant.GUEST_CALL.getIndex())){//处理客人呼叫
                                    CallMessage callMessage = new CallMessage();
                                    callMessage.setCustomerId(customerId);
                                    callMessage.setCode(code);
                                    callMessage.setContent(CallTypeConstant.GUEST_CALL.getName() + ":" + t_caller.getCategoryName() +
                                            "-" + t_caller.getName() + "," + CallTypeConstant.GUEST_CALL.getName());
                                    callMessage.setStatus((short) 0);
                                    List<CallMessage> callMessages = callMessageService.findAll(callMessage);
                                    if(callMessages == null || callMessages.isEmpty()){
                                        callMessage.setCallTime(TimeUtil.now());
                                        callMessage.setContent(CallTypeConstant.GUEST_CALL.getName() + ":" +
                                                t_caller.getCategoryName() + "-" + t_caller.getName() + "," + CallTypeConstant.GUEST_CALL.getName());
                                        callMessageService.save(callMessage);
                                    }
                                }

                                if(t_caller.getTvMac() != null && !t_caller.getTvMac().equals("")){//已经对接spa设备的情况
                                    String result = HttpUtil.getOpenUrl("http://" + CommonConstants.CLOUD_API_SVR +
                                            "/api/get-device.json?mac=" + t_caller.getTvMac() + "&name=" + t_caller.getName() +
                                            "&categoryName=" + t_caller.getCategoryName() , true, "utf-8");//请求设备信息
                                    if(!result.equals("url request error")){
                                        Gson gson = new GsonBuilder().create();
                                        ModelBean modelBean = gson.fromJson(result,ModelBean.class);
                                        if(modelBean.getCode() == 200){
                                            DeviceBean deviceBean = gson.fromJson(modelBean.getData().toString(),DeviceBean.class);
                                            if(t_caller.getCustomerName() == null || t_caller.getCustomerName().equals("")){//更新caller表的customer_name字段
                                                t_caller.setCustomerName(deviceBean.getCustomerName());
                                                callerService.update(t_caller);
                                            }
                                            //呼叫:休息厅-B233,呼叫
                                            String content = CallTypeConstant.GUEST_CALL.getName() + ":" + t_caller.getCategoryName() +
                                                    "-" + t_caller.getName() + "," + CallTypeConstant.GUEST_CALL.getName();
                                            String result1 = HttpUtil.getOpenUrl("http://" + CommonConstants.CLOUD_API_SVR +
                                                    "/api/call.json?customerId=" + deviceBean.getCustomerId() + "&mac=" +
                                                    deviceBean.getMac() + "&content=" + content + "&status=0", true, "utf-8");
                                            if(!result1.equals("url request error")){
                                                ModelBean modelBean1 = gson.fromJson(result1,ModelBean.class);
                                                if(modelBean1.getCode() == 200){
                                                    System.out.println("呼叫成功！");
                                                }else if(modelBean1.getCode() == -1){
                                                    System.out.println("SPA服务器中已有呼叫记录：" + modelBean1.getMsg());
                                                }
                                            }
                                        }
                                    }
                                }
                            }else {
                                logger.warn("异常（" + customerName + ")：" + "编码为" + code + "的呼叫器未添加到总表中");
                            }
                        }else {
                            logger.warn("异常（" + customerName + ")：" + "授权已过期！");
                        }
                    }
                }else {
                    logger.warn("MAC地址为" + mac + "的呼叫主机未注册！");
                }

            }
        }catch (Exception e){
            logger.warn("socket异常3：" + e.getMessage());
        }
    }
}
