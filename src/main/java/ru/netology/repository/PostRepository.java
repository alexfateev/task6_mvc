package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {

    private ConcurrentHashMap<Long, Post> repository = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong(1);

    public List<Post> all() {
        List<Post> list = new ArrayList<>();
        repository.entrySet().stream().forEach(e -> list.add(e.getValue()));
        return list;
    }

    public Post getById(long id) {
        return repository.get(id);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            repository.put(id.incrementAndGet(), post);
        } else {
            if (repository.containsKey(post.getId())) {
                repository.put(post.getId(), post);
            } else {
                throw new NotFoundException();
            }
        }
        return post;
    }

    public void removeById(long id) {
        repository.remove(id);
    }
}
