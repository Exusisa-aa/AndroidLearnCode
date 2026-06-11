package com.film.app.ui.detail;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u0015"}, d2 = {"Lcom/film/app/ui/detail/MovieDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/film/app/data/repository/MovieRepository;", "(Lcom/film/app/data/repository/MovieRepository;)V", "_movie", "Landroidx/lifecycle/MutableLiveData;", "Lcom/film/app/data/model/Movie;", "_schedules", "", "Lcom/film/app/data/model/Schedule;", "movie", "Landroidx/lifecycle/LiveData;", "getMovie", "()Landroidx/lifecycle/LiveData;", "schedules", "getSchedules", "loadMovie", "", "movieId", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MovieDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.film.app.data.repository.MovieRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.film.app.data.model.Movie> _movie = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.film.app.data.model.Movie> movie = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.film.app.data.model.Schedule>> _schedules = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.film.app.data.model.Schedule>> schedules = null;
    
    @javax.inject.Inject()
    public MovieDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.film.app.data.repository.MovieRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.film.app.data.model.Movie> getMovie() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.film.app.data.model.Schedule>> getSchedules() {
        return null;
    }
    
    public final void loadMovie(long movieId) {
    }
}