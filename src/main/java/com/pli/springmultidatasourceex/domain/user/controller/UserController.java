package com.pli.springmultidatasourceex.domain.user.controller;

import com.pli.springmultidatasourceex.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;
}
