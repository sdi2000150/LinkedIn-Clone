package gr.uoa.di.ted.userdemo;

import gr.uoa.di.ted.userdemo.User;

public interface UserRepositoryCustom {
    User findByUsername(String username);
}
