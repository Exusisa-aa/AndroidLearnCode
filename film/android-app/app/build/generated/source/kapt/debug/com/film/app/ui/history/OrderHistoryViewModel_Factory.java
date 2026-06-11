package com.film.app.ui.history;

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
public final class OrderHistoryViewModel_Factory implements Factory<OrderHistoryViewModel> {
  private final Provider<MovieRepository> repositoryProvider;

  public OrderHistoryViewModel_Factory(Provider<MovieRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public OrderHistoryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static OrderHistoryViewModel_Factory create(Provider<MovieRepository> repositoryProvider) {
    return new OrderHistoryViewModel_Factory(repositoryProvider);
  }

  public static OrderHistoryViewModel newInstance(MovieRepository repository) {
    return new OrderHistoryViewModel(repository);
  }
}
