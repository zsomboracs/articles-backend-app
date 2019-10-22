package com.apple.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apple.repository.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
