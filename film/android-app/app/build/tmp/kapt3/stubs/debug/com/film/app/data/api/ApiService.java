package com.film.app.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0001H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\b\b\u0001\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\f0\u00032\b\b\u0003\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ$\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\f0\u00032\b\b\u0001\u0010\u0012\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f0\u00032\b\b\u0001\u0010\u0015\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\f0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ*\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\u0014\b\u0001\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00032\b\b\u0001\u0010!\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010#\u001a\u00020$H\u00a7@\u00a2\u0006\u0002\u0010%\u00a8\u0006&"}, d2 = {"Lcom/film/app/data/api/ApiService;", "", "createOrder", "Lcom/film/app/data/model/Result;", "order", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMovieDetail", "Lcom/film/app/data/model/Movie;", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMovies", "", "status", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrders", "Lcom/film/app/data/model/Order;", "userId", "getSchedules", "Lcom/film/app/data/model/Schedule;", "movieId", "getSeats", "Lcom/film/app/data/model/Seat;", "scheduleId", "login", "Lcom/film/app/data/model/User;", "loginRequest", "", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "payOrder", "", "orderId", "register", "registerRequest", "Lcom/film/app/data/model/RegisterRequest;", "(Lcom/film/app/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.GET(value = "movie/list")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMovies(@retrofit2.http.Query(value = "status")
    int status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.util.List<com.film.app.data.model.Movie>>> $completion);
    
    @retrofit2.http.GET(value = "movie/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMovieDetail(@retrofit2.http.Path(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<com.film.app.data.model.Movie>> $completion);
    
    @retrofit2.http.POST(value = "order/create")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createOrder(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.lang.Object order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.lang.Object>> $completion);
    
    @retrofit2.http.POST(value = "user/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> loginRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<com.film.app.data.model.User>> $completion);
    
    @retrofit2.http.POST(value = "user/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.film.app.data.model.RegisterRequest registerRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.lang.String>> $completion);
    
    @retrofit2.http.GET(value = "schedule/movie/{movieId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSchedules(@retrofit2.http.Path(value = "movieId")
    long movieId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.util.List<com.film.app.data.model.Schedule>>> $completion);
    
    @retrofit2.http.GET(value = "seat/schedule/{scheduleId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSeats(@retrofit2.http.Path(value = "scheduleId")
    long scheduleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.util.List<com.film.app.data.model.Seat>>> $completion);
    
    @retrofit2.http.GET(value = "order/user/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOrders(@retrofit2.http.Path(value = "userId")
    long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.util.List<com.film.app.data.model.Order>>> $completion);
    
    @retrofit2.http.POST(value = "order/pay/{orderId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object payOrder(@retrofit2.http.Path(value = "orderId")
    long orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.film.app.data.model.Result<java.lang.Boolean>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}