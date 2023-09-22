package ru.sberbank.homework.petsgram.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class Post {

    private int id;
    @NonNull
    @NotBlank
    private String author;
    @Past
    private final LocalDate creationDate;
    @Size(max = 200)
    private String description;
    @NonNull
    @NotBlank
    private String photoUrl;

}
