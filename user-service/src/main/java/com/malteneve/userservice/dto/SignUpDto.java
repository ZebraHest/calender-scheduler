package com.malteneve.userservice.dto;

public record SignUpDto(String firstName, String lastName, String login, char[] password) {
}
