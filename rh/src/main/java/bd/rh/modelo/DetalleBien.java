package bd.rh.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "detalle_bien")
@ToString(exclude = "empleado")
public class DetalleBien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer consec;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    //@JsonIgnore
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_bien")
    private Bien bien;

    private String comentario;

    public Integer getConsec() {
        return consec;
    }

    public void setConsec(Integer consec) {
        this.consec = consec;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
