package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Carta {

    private int id;
    private String imageLink;
    // 1 -> bronce; 2 -> plata; 3 -> oro; 4 -> JV; 5 -> especial;
    private int tier;
    private String alt;

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + id +
                ", imageLink='" + imageLink + '\'' +
                ", tier=" + tier +
                ", alt='" + alt + '\'' +
                '}';
    }
}
