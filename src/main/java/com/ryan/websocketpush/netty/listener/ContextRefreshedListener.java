package com.ryan.websocketpush.netty.listener;

import com.ryan.websocketpush.annotation.ActionCode;
import com.ryan.websocketpush.netty.service.TcpDispacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**

 * 启动时筛选所有具有 ActionCodeConstants 的bean
 *
 */

@Component
public class ContextRefreshedListener implements ApplicationListener<ApplicationStartedEvent> {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {

        Map<Integer, Object> map = new HashMap<>();

        Map<String, Object> bizMap = applicationStartedEvent.getApplicationContext().getBeansWithAnnotation(ActionCode.class);
        for (Map.Entry<String, Object> entry : bizMap.entrySet()) {
            Object object = entry.getValue();
            Class c = object.getClass();
            logger.warn(c + "===>");

            Object value = entry.getValue();
            ActionCode actionCode = AnnotationUtils.findAnnotation(value.getClass(), ActionCode.class);
            if (actionCode != null) {
                map.put(actionCode.value(), object);
            }
        }

        TcpDispacher tcpDispacher = (TcpDispacher) applicationStartedEvent.getApplicationContext().getBean("tcpDispacher");
        tcpDispacher.setSocketBean(map);

    }
}