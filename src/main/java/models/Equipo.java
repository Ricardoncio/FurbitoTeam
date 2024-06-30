package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Equipo {

    private Carta por;
    private Carta defd;
    private Carta defi;
    private Carta deld;
    private Carta deli;

    public Equipo() {
        por = null;
        defd = null;
        defi = null;
        deld = null;
        deli = null;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "por=" + por +
                ", defd=" + defd +
                ", defi=" + defi +
                ", deld=" + deld +
                ", deli=" + deli +
                '}';
    }
}
