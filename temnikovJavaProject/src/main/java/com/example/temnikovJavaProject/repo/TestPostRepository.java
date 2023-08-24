package com.example.temnikovJavaProject.repo;

import com.example.temnikovJavaProject.models.TestPost;
import org.springframework.data.repository.CrudRepository;

public interface TestPostRepository extends CrudRepository<TestPost, Long> {
}
