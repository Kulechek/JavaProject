package com.example.temnikovJavaProject.services;

import com.example.temnikovJavaProject.models.TestPost;
import com.example.temnikovJavaProject.repo.TestPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestPostService {

    private final TestPostRepository testPostRepository;

    @Autowired
    public TestPostService(TestPostRepository testPostRepository) {
        this.testPostRepository = testPostRepository;
    }

    public void saveTestPost(TestPost testPost) {
        testPostRepository.save(testPost);
    }

    public Iterable<TestPost> getAllTestPosts() {
        return testPostRepository.findAll();
    }
}
