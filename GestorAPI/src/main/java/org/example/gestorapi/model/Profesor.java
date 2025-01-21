package org.example.gestorapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.gestorapi.model.enumerados.Rol;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "profesores")
public class Profesor {
    @Id
    @Size(max = 36)
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    @Size(max = 9)
    @NotNull
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Size(max = 25)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 25)
    private String nombre;

    @Size(max = 45)
    @NotNull
    @Column(name = "apellidos", nullable = false, length = 45)
    private String apellidos;

    @NotNull
    @Lob
    @Column(name = "correo", nullable = false)
    private String correo;

    @Size(max = 32)
    @NotNull
    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @NotNull
    @ColumnDefault("'PROF'")
    @Lob
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "activo", nullable = false)
    private Boolean activo = false;

    @Lob
    @Column(name = "url_foto")
    private String urlFoto;

    @ColumnDefault("0")
    @Column(name = "es_jefe_dep")
    private Boolean esJefeDep;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "depart_id", nullable = false)
    private Departamento depart;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Boolean getEsJefeDep() {
        return esJefeDep;
    }

    public void setEsJefeDep(Boolean esJefeDep) {
        this.esJefeDep = esJefeDep;
    }

    public Departamento getDepart() {
        return depart;
    }

    public void setDepart(Departamento depart) {
        this.depart = depart;
    }

}