package com.example.projectsale.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepo extends JpaRepository<Token, UUID> {

    @Query("""
            SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id
            WHERE u.id = :id AND(t.expired = FALSE or t.revoked = FALSE)
            """)
    List<Token> findAllValidTokensByUser(UUID id);

    Optional<Token> findByToken(String token);
}
