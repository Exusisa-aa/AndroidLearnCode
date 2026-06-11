package com.film.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2 = {"Lcom/film/app/utils/UserSession;", "", "()V", "user", "Lcom/film/app/data/model/User;", "getUser", "()Lcom/film/app/data/model/User;", "setUser", "(Lcom/film/app/data/model/User;)V", "isLoggedIn", "", "logout", "", "app_debug"})
public final class UserSession {
    @org.jetbrains.annotations.Nullable()
    private static com.film.app.data.model.User user;
    @org.jetbrains.annotations.NotNull()
    public static final com.film.app.utils.UserSession INSTANCE = null;
    
    private UserSession() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.film.app.data.model.User getUser() {
        return null;
    }
    
    public final void setUser(@org.jetbrains.annotations.Nullable()
    com.film.app.data.model.User p0) {
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final void logout() {
    }
}