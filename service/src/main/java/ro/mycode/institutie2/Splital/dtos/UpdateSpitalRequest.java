package ro.mycode.institutie2.Splital.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateSpitalRequest {
    private String denumire="";
    private String adresa="";
    private String specializare="";
}
