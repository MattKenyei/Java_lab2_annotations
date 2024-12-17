import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class EventManager {
    private final Map<String, List<Object>> eventHandlers = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void registerHandler(Object handler) {
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Event.class)) {
                Event eventAnnotation = method.getAnnotation(Event.class);
                eventHandlers.computeIfAbsent(eventAnnotation.value(), k -> new ArrayList<>()).add(handler);
            }
        }
    }

    public void triggerEvent(String eventName, Object... args) {
        List<Object> handlers = eventHandlers.get(eventName);
        if (handlers != null) {
            for (Object handler : handlers) {
                invokeHandler(handler, eventName, args);
            }
        } else {
            Logger.log("No handlers for event: " + eventName);
        }
    }

    public void triggerEventWithDelay(String eventName, long delay, Object... args) {
        scheduler.schedule(() -> triggerEvent(eventName, args), delay, TimeUnit.MILLISECONDS);
    }

    private void invokeHandler(Object handler, String eventName, Object... args) {
        for (Method method : handler.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Event.class) && method.getAnnotation(Event.class).value().equals(eventName)) {
                try {
                    method.invoke(handler, args);
                    Logger.log("Triggered event: " + eventName);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
