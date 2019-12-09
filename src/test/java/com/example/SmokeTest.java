package com.example;

import com.example.jpa.Post;
import com.example.jpa.repositories.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Essential tests
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SmokeTest {

    @Autowired
    private PostRepository postRepository;

    @Before
    public void setup() {
        // The most important precondition is that the injected repository is not null
        Assert.assertNotNull(postRepository);
    }

    /**
     * Test create, read, update, delete
     */
    @Test
    public void testPostRepositoryCRUD() {
        // READ ALL
        List<Post> posts = postRepository.findAll();
        Assert.assertNotNull(posts);
        Assert.assertEquals(0, posts.size());

        // CREATE ONE
        Post post = new Post();
        post.setTitle("A new beginning");
        post.setContent("Dear diary, today I eat all burgers and was still hungry.");
        post.setCreatedDate(LocalDateTime.now());

        Post persistedPost = postRepository.save(post);
        Assert.assertNotNull(persistedPost);
        Assert.assertNotNull(persistedPost.getId());

        // FIND ALL
        posts = postRepository.findAll();
        Assert.assertNotNull(posts);
        Assert.assertEquals(1, posts.size());
        Assert.assertEquals(persistedPost.getId(), posts.get(0).getId());

        // FIND BY TITLE
        Post result = postRepository.findByTitle(post.getTitle());
        Assert.assertNotNull(result);
        Assert.assertEquals(persistedPost.getId(), result.getId());
        Assert.assertEquals(persistedPost.getTitle(), result.getTitle());

        // UPDATE
        result.setPublishDate(LocalDateTime.now());
        postRepository.save(result);

        // FIND AGAIN ONE AND CHECK IF STILL SAME ID
        result = postRepository.findByTitle(post.getTitle());
        Assert.assertNotNull(result.getPublishDate());
        Assert.assertEquals(persistedPost.getId(), result.getId());

        // DELETE
        postRepository.delete(result);

        // CHECK IF EMPTY
        posts = postRepository.findAll();
        Assert.assertNotNull(posts);
        Assert.assertEquals(0, posts.size());
    }
}
