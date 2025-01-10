package org.example.gestorapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "grupos_participantes")
public class GrupoParticipante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "actividades_id", nullable = false)
    private Actividad actividades;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "num_participantes", nullable = false)
    private Integer numParticipantes;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Actividad getActividades() {
        return actividades;
    }

    public void setActividades(Actividad actividades) {
        this.actividades = actividades;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Integer getNumParticipantes() {
        return numParticipantes;
    }

    public void setNumParticipantes(Integer numParticipantes) {
        this.numParticipantes = numParticipantes;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}