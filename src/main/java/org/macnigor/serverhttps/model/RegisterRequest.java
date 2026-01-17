package org.macnigor.serverhttps.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@RegisterReflectionForBinding(RegisterRequest.class)
public record RegisterRequest(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
) {
}
