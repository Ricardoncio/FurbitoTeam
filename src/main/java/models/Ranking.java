package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter

public class Ranking {
    private String[] primero;
    private String[] segundo;
    private String[] tercero;
    private String[] cuarto;
    private String[] quinto;
    private String[] sexto;


    @Override
    public String toString() {
        return "Ranking{" +
                "primero=" + Arrays.toString(primero) +
                ", segundo=" + Arrays.toString(segundo) +
                ", tercero=" + Arrays.toString(tercero) +
                ", cuarto=" + Arrays.toString(cuarto) +
                ", quinto=" + Arrays.toString(quinto) +
                ", sexto=" + Arrays.toString(sexto) +
                '}';
    }
}
