package ru.sberbank.homework.petsgram.exeption;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String massage) {
        super(massage);
    }
}
