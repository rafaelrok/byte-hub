package br.com.rafaelvieira.bytehub.domain.repository;

import br.com.rafaelvieira.bytehub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select User from User where userProvideUid = :userUid")
    User findUserSocialLoginByUserUid(String userUid);
}
