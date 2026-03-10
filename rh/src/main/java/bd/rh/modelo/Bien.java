package bd.rh.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "bien")
public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBien;
    private String nombre;
    private String descripcion;

    // Getters y Setters

    public Integer getIdBien()
    {
        return idBien;
    }

    public void setIdBien(Integer idBien)
    {
        this.idBien = idBien;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
}
