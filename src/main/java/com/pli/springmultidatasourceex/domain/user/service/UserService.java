package com.pli.springmultidatasourceex.domain.user.service;

import com.pli.springmultidatasourceex.domain.user.dto.UserResponse;
import com.pli.springmultidatasourceex.domain.user.entity.User;
import com.pli.springmultidatasourceex.domain.user.repository.UserRepository;
import com.pli.springmultidatasourceex.domain.user.types.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final Map<Type, UserRepository> repositoryMap;

  public List<UserResponse> getUsers() {
    List<CompletableFuture<List<User>>> futures =
        repositoryMap.keySet().stream().map(this::getQuotationsAsync).toList();

    CompletableFuture<Void> allOf =
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    allOf.join();

    List<User> users = futures.stream().map(CompletableFuture::join).flatMap(List::stream).toList();

    return users.stream()
        .sorted(Comparator.comparing(User::getCreatedAt))
        .map(UserResponse::fromEntity)
        .toList();
  }

  @Async
  public CompletableFuture<List<User>> getQuotationsAsync(final Type type) {
    UserRepository repository = validateAndGetRepository(type);
    List<User> quotations = repository.findTop10ByOrderByCreatedAtDesc();

    return CompletableFuture.completedFuture(quotations);
  }

  private UserRepository validateAndGetRepository(final Type type) {
    UserRepository repository = repositoryMap.get(type);

    if (repository == null) {
      throw new IllegalArgumentException("지원하지 않는 TYPE: " + type);
    }

    return repository;
  }

  public UserResponse getUser(final Long userId, final Type type) {
    UserRepository repository = validateAndGetRepository(type);
    User user =
        repository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. " + userId));

    return UserResponse.fromEntity(user);
  }
}
