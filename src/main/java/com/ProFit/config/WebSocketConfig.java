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
    // 設置消息代理前綴，客戶端訂閱時使用
    config.enableSimpleBroker("/topic", "/queue");
    // 設置服務端接收客戶端消息的前綴
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // 註冊 WebSocket 端點
    registry.addEndpoint("/ws")
        .setAllowedOrigins("*") // 設置允許的來源
        .withSockJS(); // 啟用 SockJS 支持
  }
}
