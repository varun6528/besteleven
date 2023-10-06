package com.verteil.besteleven.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private Integer id;
    private String name;
    private PlayerType type;
    private String country;
    private Integer matchScore;
}
