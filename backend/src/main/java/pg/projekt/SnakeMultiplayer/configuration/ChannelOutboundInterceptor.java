package pg.projekt.SnakeMultiplayer.configuration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MultiValueMap;

public class ChannelOutboundInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        final SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);

        if (headerAccessor.getMessageType() == SimpMessageType.CONNECT_ACK) {
            final StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECTED);
            accessor.setSessionId(headerAccessor.getSessionId());
            @SuppressWarnings("unchecked") final MultiValueMap<String, String> nativeHeaders = (MultiValueMap<String, String>) headerAccessor.getHeader(StompHeaderAccessor.NATIVE_HEADERS);
            accessor.addNativeHeaders(nativeHeaders);
            accessor.addNativeHeader("id", headerAccessor.getSessionId());

            return MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());
        }

        return message;
    }

}
