package ssaver.gob.mx.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "documento", schema = "gestion_documental")
public class Documento {
    private Long idDocumento;
    private Long sistema;
    private String url;
    private Timestamp fecha;
    private String usuario;

    @Id
    @Column(name = "id_documento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Basic
    @Column(name = "sistema")
    public Long getSistema() {
        return sistema;
    }

    public void setSistema(Long sistema) {
        this.sistema = sistema;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "fecha")
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "usuario")
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Documento documento = (Documento) o;
        return Objects.equals(idDocumento, documento.idDocumento) && Objects.equals(sistema, documento.sistema) && Objects.equals(url, documento.url) && Objects.equals(fecha, documento.fecha) && Objects.equals(usuario, documento.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocumento, sistema, url, fecha, usuario);
    }
}
