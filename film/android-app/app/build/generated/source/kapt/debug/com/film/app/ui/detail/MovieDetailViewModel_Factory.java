package com.film.app.ui.detail;

import com.film.app.data.repository.MovieRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MovieDetailViewModel_Factory implements Factory<MovieDetailViewModel> {
  private final Provider<MovieRepository> repositoryProvider;

  public MovieDetailViewModel_Factory(Provider<MovieRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MovieDetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static MovieDetailViewModel_Factory create(Provider<MovieRepository> repositoryProvider) {
    return new MovieDetailViewModel_Factory(repositoryProvider);
  }

  public static MovieDetailViewModel newInstance(MovieRepository repository) {
    return new MovieDetailViewModel(repository);
  }
}
