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
}
