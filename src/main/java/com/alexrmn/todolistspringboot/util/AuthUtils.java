package com.alexrmn.todolistspringboot.util;

import com.alexrmn.todolistspringboot.model.Category;
import com.alexrmn.todolistspringboot.model.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthUtils {

    public static boolean taskBelongsToUser(Task task, Authentication authentication) {
        return authentication.getName().equals(task.getUser().getUsername());
    }

    public static boolean categoryBelongsToUser(Category category, Authentication authentication) {
        return !authentication.getName().equals(category.getUser().getUsername());
    }

    public static boolean isAdmin(Authentication authentication){
        return authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .contains("ROLE_ADMIN");
    }
}
