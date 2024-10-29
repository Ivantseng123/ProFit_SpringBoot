package com.ProFit.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 連接建立後的處理
        System.out.println("WebSocket 連接建立: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 處理接收到的消息
        System.out.println("收到消息:" + message.getPayload());
        // 可以在這裡處理消息並回覆
        session.sendMessage(new TextMessage("服務器收到消息"));
    }

}
