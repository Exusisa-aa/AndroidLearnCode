package com.film.app.data.repository;

import com.film.app.data.api.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class MovieRepository_Factory implements Factory<MovieRepository> {
  private final Provider<ApiService> apiServiceProvider;

  public MovieRepository_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public MovieRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static MovieRepository_Factory create(Provider<ApiService> apiServiceProvider) {
    return new MovieRepository_Factory(apiServiceProvider);
  }

  public static MovieRepository newInstance(ApiService apiService) {
    return new MovieRepository(apiService);
  }
}
