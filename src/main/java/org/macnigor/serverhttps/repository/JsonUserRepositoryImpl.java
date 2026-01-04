package org.macnigor.serverhttps.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.macnigor.serverhttps.model.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Repository
public class JsonUserRepositoryImpl implements UserRepository {

    private final File storageFile = new File("users.json");
    private final ObjectMapper objectMapper;
    private final List<User> users;

    public JsonUserRepositoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.users = loadUsers();
    }

    private List<User> loadUsers() {
        if (!storageFile.exists()) return new ArrayList<>();
        try {
            return objectMapper.readValue(storageFile, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(storageFile, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<User> findByApiKey(String apiKey) {
        return users.stream().filter(u -> Objects.equals(u.getApiKey(), apiKey)).findFirst();
    }

    @Override
    public void save(User user) {
        findByUsername(user.getUsername()).ifPresent(users::remove); // обновление
        users.add(user);
        saveToFile();
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }
}

