package br.com.doni.ecomm.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode @AllArgsConstructor
public class Dimension {
    private Double height;
    private Double width;
    private Double depth;

    public Double calculateVolume(){
        return (height * width * depth) / Math.pow(10, 6);
    }
}
