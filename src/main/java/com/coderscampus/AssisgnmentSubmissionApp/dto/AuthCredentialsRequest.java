package com.coderscampus.AssisgnmentSubmissionApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthCredentialsRequest {
    private String username;
    private String password;
}