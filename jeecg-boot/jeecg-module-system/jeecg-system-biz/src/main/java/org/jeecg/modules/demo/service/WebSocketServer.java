package org.jeecg.modules.demo.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.chat.entity.ChatLog;
import org.jeecg.modules.demo.chat.service.IChatLogService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.hutool.json.JSONUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author lihaoran
 * @date 2023/11/10 14:03
 */
@Slf4j
@ServerEndpoint("/qqServer/{qqNumber}")
@Component
public class WebSocketServer {
    static {
        System.out.println("----------------------------------");
        System.out.println("------   WebSocket服务启动   -------");
        System.out.println("---------                ----------");
        System.out.println("----------------------------------");
    }


    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * QQ号
     */
    private String qqNumber;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocketServer> webSockets = new CopyOnWriteArraySet<>();

    // 用来存在线连接用户信息( 线程安全 )
    // key: QQ号  value: 每个客户端的session对象
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    // 1. 当客户端连接时做什么事情
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "qqNumber") String qqNumber) throws Exception {
        System.out.println("--客户端连接[qq：" + qqNumber + "]--");
        this.session = session;
        this.qqNumber = qqNumber;
        sessionPool.put(qqNumber, session);
        List<WebSocketServer> collect = webSockets.stream().filter(item -> item.qqNumber.equals(qqNumber)).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            webSockets.add(this);
        }
        String s = showAllUser();
        sendAllMsg(s);
    }

    // 2.当客户端发送消息给服务器时做什么事情
    // 参数message就是客户端给服务器发送的消息内容
    @OnMessage
    public void onMsg(String message) {
        try {
            // 每次发送消息时都需要讲消息进行存储
            ChatLog chatLog = new ChatLog();
            // 判断群发还是单发
            String[] split = message.split("@#");
            chatLog.setSendPerson(split[0]);
            chatLog.setRecivePerson(split[3]);
            chatLog.setAbout(split[1]);
            chatLog.setCreateTime(DateUtil.parse(split[2]));
            chatLog.setCreateBy(split[0]);
            IChatLogService bean = SpringContextUtils.getBean(IChatLogService.class);
            bean.save(chatLog);
            if (split.length == 4) {
                // 单发
                sendOneMsg(message);
            } else {
                // 群发
                sendAllMsg(message);
            }
        } catch (IOException e) {
            System.err.println("发送消息异常");
            e.printStackTrace();
        } finally {
            showAllUser();
        }
    }

    // 3.当客户端断开连接时要做什么事情
    @OnClose
    public void onClose(@PathParam(value = "qqNumber") String qqNumber) throws Exception {
        List<WebSocketServer> collect = webSockets.stream().filter(item -> item.qqNumber.equals(qqNumber)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            webSockets.remove(collect.get(0));
            System.out.println("客户端断开连接~！~！！");
        } else {
            System.out.println("客户端断开连接失败！！~！~！！");
        }
        String s = showAllUser();
        sendAllMsg(s);
    }

    // 4.当客户端发送消息、连接或断开连接时错误做什么事情
    @OnError
    public void onErr(Throwable error) {
        error.printStackTrace();
        System.out.println("服务器或客户端异常！！！！");
    }

    /**
     * 群发功能
     *
     * @param msg 群发的消息
     */
    public void sendAllMsg(String msg) throws IOException {
        System.out.println("--群发消息：" + msg);
        // 遍历所有已经连接到服务器的客户端对象
        for (WebSocketServer socket : webSockets) {
            if (socket.session.isOpen()) {// 判断客户端是否在线
                socket.session.getAsyncRemote().sendText(msg);
            }
        }
    }

    /**
     * 单发功能
     *
     * @param msg 群发的消息
     */
    public void sendOneMsg(String msg) throws IOException {
        System.out.println("--单发消息：" + msg);
        String[] split = msg.split("@#");
        // 根据用户的 username 查询用户信息
        ISysUserService bean = SpringContextUtils.getBean(ISysUserService.class);
        SysUser one = bean.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, split[0]));
        msg+="@#" + one.getRealname();
        List<WebSocketServer> collect = webSockets.stream().filter(item -> item.qqNumber.equals(split[split.length - 1])).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            if (collect.get(0).session.isOpen()) {// 判断客户端是否在线
                collect.get(0).session.getAsyncRemote().sendText(msg);
            }
        }
    }

    /**
     * @description: 展示所有用户
     * @author lhr
     * @date 2023/11/10 18:05
     * @version 1.0
     */
    public String showAllUser() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("#@,");
        for (WebSocketServer webSocket : webSockets) {
            stringBuffer.append(webSocket.qqNumber + ",");
        }
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

}
