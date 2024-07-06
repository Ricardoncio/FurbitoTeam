package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Carta implements Comparable{

    private Integer id;
    private Integer id_modelo;
    private String imageLink;
    // 1 -> bronce; 2 -> plata; 3 -> oro; 4 -> JV; 5 -> especial;
    private int tier;
    private int media;
    private int pac;
    private int sho;
    private int pas;
    private int dri;
    private int def;
    private int phy;

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + id +
                ", id_modelo=" + id_modelo +
                ", imageLink='" + imageLink + '\'' +
                ", tier=" + tier +
                ", media=" + media +
                ", pac=" + pac +
                ", sho=" + sho +
                ", pas=" + pas +
                ", dri=" + dri +
                ", def=" + def +
                ", phy=" + phy +
                '}';
    }

    public void calcularMedia() {
        int totalPuntos = pac + sho + pas + dri + def + phy;
        media = Math.round((float) totalPuntos / 6);
    }

    @Override
    public int compareTo(Object o) {
        int resultado = 0;

        if (o instanceof Carta) {
            if (this.tier < ((Carta) o).tier)
                resultado = 1;
            else if (this.tier > ((Carta) o).tier)
                resultado = -1;
            else if (this.media > ((Carta) o).media)
                resultado = -1;
            else if (this.media < ((Carta) o).media)
                resultado = 1;
        }

        return resultado;
    }
}
