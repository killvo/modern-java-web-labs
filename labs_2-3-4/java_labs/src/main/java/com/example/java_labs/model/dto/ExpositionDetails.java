package com.example.java_labs.model.dto;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.Hall;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpositionDetails {
    private Exposition exposition;
    private List<Hall> halls;
}
