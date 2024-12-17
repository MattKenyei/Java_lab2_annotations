
public class Main {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        myEventHandler eventHandler = new myEventHandler();

        // Регистрация обработчиков событий
        eventManager.registerHandler(eventHandler);

        // Триггерим события
        eventManager.triggerEvent("USER_REGISTERED", "Alice");
        eventManager.triggerEvent("USER_LOGGED_IN", "Bob", System.currentTimeMillis());
        eventManager.triggerEventWithDelay("USER_LOGGED_OUT", 5000, "Charlie"); // будет выполнено через 5 секунд

        // Завершение работы
        eventManager.shutdown();
    }
}
