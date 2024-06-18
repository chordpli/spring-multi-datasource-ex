package com.pli.springmultidatasourceex.domain.user.service;

import com.pli.springmultidatasourceex.domain.user.repository.UserRepository;
import com.pli.springmultidatasourceex.domain.user.types.Type;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final Map<Type, UserRepository> repositoryMap;
}
