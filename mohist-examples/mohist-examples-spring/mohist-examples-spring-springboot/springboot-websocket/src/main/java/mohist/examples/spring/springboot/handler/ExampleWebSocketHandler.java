package mohist.examples.spring.springboot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Slf4j
@Component
public class ExampleWebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("[ afterConnectionEstablished ] 新的连接，session id: {}", session.getId());
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("[ handleMessage ]");
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("[ handleTextMessage ] message: {}", message.getPayload());
        session.sendMessage(new TextMessage("[echo] > " + message.getPayload()));
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        log.debug("[ handlePongMessage ]");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.debug("[ handleTransportError ] 处理异常 : {}", exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("[ afterConnectionClosed ] 连接关闭, session id: {}", session.getId());
        this.sessions.remove(session);
    }

    public void sendMessageToSessions(TextMessage message) {
        for (WebSocketSession session : this.sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                    log.debug("[ sendMessageToSessions ] success session id: {}", session.getId());
                } catch (IOException e) {
                    log.error("[ sendMessageToSessions ] failed session id: {}, IOException : {}", session.getId(), e.getMessage());
                }
            }
        }
    }
}
