package com.ProFit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // 設定消息代理前綴
    // /topic 用於廣播消息
    // /queue 用於一對一的私人消息
    config.enableSimpleBroker("/topic", "/queue");

    // 設置服務端接收客戶端消息的前綴
    config.setApplicationDestinationPrefixes("/app");

    // 設定一對一消息的前綴
    config.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // 註冊 WebSocket 端點
    // 客戶端通過這個端點連接到 WebSocket 服務器
    // withSockJS() 提供 SockJS 回退機制
    registry.addEndpoint("/ws")
        // .setAllowedOrigins("*") // 設置允許的來源
        .withSockJS(); // 啟用 SockJS 支持
  }
}
