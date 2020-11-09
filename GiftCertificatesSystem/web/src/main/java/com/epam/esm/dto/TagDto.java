package com.epam.esm.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
public class TagDto {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$")
    @Size(min = 4, max = 255)
    String name;
}
