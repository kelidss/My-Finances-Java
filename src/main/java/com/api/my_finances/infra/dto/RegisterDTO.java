package com.api.my_finances.infra.dto;

import com.api.my_finances.models.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
