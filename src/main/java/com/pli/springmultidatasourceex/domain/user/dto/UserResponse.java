package com.pli.springmultidatasourceex.domain.user.dto;

import com.pli.springmultidatasourceex.domain.user.entity.User;

public record UserResponse(String name, String email) {

  public static UserResponse fromEntity(User user) {
    return new UserResponse(user.getName(), user.getEmail());
  }
}
