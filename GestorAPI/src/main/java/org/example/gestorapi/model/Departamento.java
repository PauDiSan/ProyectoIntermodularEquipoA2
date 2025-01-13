package org.example.gestorapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "departamentos")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depar", nullable = false)
    private Integer id;

    @Size(max = 3)
    @NotNull
    @Column(name = "codigo", nullable = false, length = 3)
    private String codigo;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

<<<<<<< Updated upstream
    @OneToMany(mappedBy = "depart")
    private Set<Profesore> profesores = new LinkedHashSet<>();
=======
>>>>>>> Stashed changes

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

<<<<<<< Updated upstream
    public Set<Profesore> getProfesores() {
        return profesores;
    }

    public void setProfesores(Set<Profesore> profesores) {
        this.profesores = profesores;
    }
=======
>>>>>>> Stashed changes

}