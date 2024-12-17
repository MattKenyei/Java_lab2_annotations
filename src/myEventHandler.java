public class myEventHandler {
    @Event("USER_REGISTERED")
    public void onUserRegistered(String username) {
        Logger.log("User registered: " + username);
    }

    @Event("USER_LOGGED_IN")
    public void onUserLoggedIn(String username, long timestamp) {
        Logger.log("User logged in: " + username + " at " + timestamp);
    }

    @Event("USER_LOGGED_OUT")
    public void onUserLoggedOut(String username) {
        Logger.log("User logged out: " + username);
    }
}

