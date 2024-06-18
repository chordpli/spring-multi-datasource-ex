package com.pli.springmultidatasourceex.domain.user.controller;

import com.pli.springmultidatasourceex.domain.user.dto.UserResponse;
import com.pli.springmultidatasourceex.domain.user.service.UserService;
import com.pli.springmultidatasourceex.domain.user.types.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;

  @GetMapping
  public List<UserResponse> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{userId}/type/{type}")
  public UserResponse getUser(final Long userId, final Type type) {
    return userService.getUser(userId, type);
  }
}
