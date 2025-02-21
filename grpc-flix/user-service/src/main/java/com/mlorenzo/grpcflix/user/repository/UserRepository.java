package com.mlorenzo.grpcflix.user.repository;

import com.mlorenzo.grpcflix.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
