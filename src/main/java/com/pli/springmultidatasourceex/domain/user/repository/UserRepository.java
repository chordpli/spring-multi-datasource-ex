package com.pli.springmultidatasourceex.domain.user.repository;

import com.pli.springmultidatasourceex.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findTop10ByOrderByCreatedAtDesc();

  Optional<User> findById(Long id);
}
