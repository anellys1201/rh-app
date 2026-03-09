package bd.rh.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "bienes")
public class Empleado
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idEmpleado;
    String nombre;
    String departamento;
    Double sueldo;
    String documento;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "empleado",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<DetalleBien> bienes;
}
