package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The {@code Tag dto} is dto for transferring tag data
 * after request with <b>POST</b> http method..
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$")
    @Size(min = 4, max = 255)
    private String name;
}
