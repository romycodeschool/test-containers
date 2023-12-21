package ro.mycode.institutie2.Splital.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.mycode.institutie2.Splital.model.Spital;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateSpitalResponse {
    private Spital spital;

    @Builder.Default
    private String message="Spitalul a fost creata cu succes";
}
