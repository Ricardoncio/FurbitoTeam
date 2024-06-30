package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Equipo {

    private Carta por;
    private Carta defd;
    private Carta defi;
    private Carta deld;
    private Carta deli;

    public Equipo() {
        por = new Carta();
        defd = new Carta();
        defi = new Carta();
        deld = new Carta();
        deli = new Carta();
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
