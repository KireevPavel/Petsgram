package ru.sberbank.homework.petsgram.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private int id;
    @Email
    @NonNull
    private String email;
    @NonNull
    @NotBlank
    private String login;
    @NonNull
    @NotBlank
    private String name;
    @Past
    private LocalDate birthday;

}
