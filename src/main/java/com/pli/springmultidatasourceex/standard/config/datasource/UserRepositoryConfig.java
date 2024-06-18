package com.pli.springmultidatasourceex.standard.config.datasource;

import com.pli.springmultidatasourceex.domain.user.repository.UserRepository;
import com.pli.springmultidatasourceex.domain.user.repository.jpn.JpnUserRepository;
import com.pli.springmultidatasourceex.domain.user.repository.kor.KorUserRepository;
import com.pli.springmultidatasourceex.domain.user.repository.usa.UsaUserRepository;
import com.pli.springmultidatasourceex.domain.user.types.Type;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRepositoryConfig {

  private final ApplicationContext applicationContext;

  public UserRepositoryConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public Map<Type, UserRepository> repositoryMap() {
    Map<Type, UserRepository> repositoryMap = new HashMap<>();

    Map<String, UserRepository> beansOfType =
        applicationContext.getBeansOfType(UserRepository.class);

    for (Map.Entry<String, UserRepository> entry : beansOfType.entrySet()) {
      UserRepository repository = entry.getValue();
      Type bizType = getTypeFromRepository(repository);
      repositoryMap.put(bizType, repository);
    }

    return repositoryMap;
  }

  private Type getTypeFromRepository(UserRepository repository) {
    //    새로운 Type과 UserRepository를 추가할 때 해당 메서드만 업데이트하면 됩니다.
    if (repository instanceof KorUserRepository) {
      return Type.KOR;
    } else if (repository instanceof JpnUserRepository) {
      return Type.JPN;
    } else if (repository instanceof UsaUserRepository) {
      return Type.USA;
    }
    throw new IllegalArgumentException("알 수 없는 레파지토리 타입: " + repository.getClass().getName());
  }
}
