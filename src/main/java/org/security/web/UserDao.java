package org.security.web;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDao {
    List<User> users = new ArrayList<User>(){{
        add(new User(1L, "lee", "finger1"));
        add(new User(2L, "rachel", "finger2"));
        add(new User(3L, "foo", "finger3"));
        add(new User(4L, "hans", "finger4"));
    }};

    public Optional<User> queryByName(String name) {
        return users.stream().filter(user -> Objects.equals(name, user.getName())).findFirst();
    }
}
