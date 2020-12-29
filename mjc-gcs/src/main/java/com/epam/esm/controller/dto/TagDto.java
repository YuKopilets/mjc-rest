package com.epam.esm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String name;
}
