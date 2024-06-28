package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Carta {

    private int id;
    private String imageLink;
    // 1 -> bronce; 2 -> plata; 3 -> oro; 4 -> JV; 5 -> especial;
    private int tier;
}
