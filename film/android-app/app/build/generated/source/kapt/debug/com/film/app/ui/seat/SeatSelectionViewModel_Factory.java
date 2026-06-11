package com.film.app.ui.seat;

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
public final class SeatSelectionViewModel_Factory implements Factory<SeatSelectionViewModel> {
  private final Provider<MovieRepository> repositoryProvider;

  public SeatSelectionViewModel_Factory(Provider<MovieRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SeatSelectionViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static SeatSelectionViewModel_Factory create(
      Provider<MovieRepository> repositoryProvider) {
    return new SeatSelectionViewModel_Factory(repositoryProvider);
  }

  public static SeatSelectionViewModel newInstance(MovieRepository repository) {
    return new SeatSelectionViewModel(repository);
  }
}
