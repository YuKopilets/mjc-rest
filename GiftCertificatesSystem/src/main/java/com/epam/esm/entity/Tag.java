package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;
}
