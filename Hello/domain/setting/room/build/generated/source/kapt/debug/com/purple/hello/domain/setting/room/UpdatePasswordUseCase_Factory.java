// Generated by Dagger (https://dagger.dev).
package com.purple.hello.domain.setting.room;

import com.purple.data.rooms.repository.RoomRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class UpdatePasswordUseCase_Factory implements Factory<UpdatePasswordUseCase> {
  private final Provider<RoomRepository> roomRepositoryProvider;

  public UpdatePasswordUseCase_Factory(Provider<RoomRepository> roomRepositoryProvider) {
    this.roomRepositoryProvider = roomRepositoryProvider;
  }

  @Override
  public UpdatePasswordUseCase get() {
    return newInstance(roomRepositoryProvider.get());
  }

  public static UpdatePasswordUseCase_Factory create(
      Provider<RoomRepository> roomRepositoryProvider) {
    return new UpdatePasswordUseCase_Factory(roomRepositoryProvider);
  }

  public static UpdatePasswordUseCase newInstance(RoomRepository roomRepository) {
    return new UpdatePasswordUseCase(roomRepository);
  }
}
