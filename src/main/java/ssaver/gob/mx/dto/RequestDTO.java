package ssaver.gob.mx.dto;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Juan Carlos Dominguez
 * @version 1.0
 * @since 18/03/2021
 * <h1>Clase para mandar un archivo</h1>
 * <p>Es una clase que recibe la url (incluido nombre y extención del archivo)
 *    y el archivo que se almacenará</p>
 */

public class RequestDTO {

    @FormParam("url_base")
    @PartType(MediaType.TEXT_PLAIN)
    private String url_base;

    @FormParam("url_file")
    @PartType(MediaType.TEXT_PLAIN)
    private String url_file;

    @FormParam("system")
    @PartType(MediaType.TEXT_PLAIN)
    private Long system;

    @FormParam("user")
    @PartType(MediaType.TEXT_PLAIN)
    private String user;

    @FormParam("password")
    @PartType(MediaType.TEXT_PLAIN)
    private String password;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream file;

    public RequestDTO() {
    }

    public RequestDTO(String url_base, String url_file, Long system, String user, String password, InputStream file) {
        this.url_base = url_base;
        this.url_file = url_file;
        this.system = system;
        this.user = user;
        this.password = password;
        this.file = file;
    }

    public String getUrl_base() {
        return url_base;
    }

    public void setUrl_base(String url_base) {
        this.url_base = url_base;
    }

    public String getUrl_file() {
        return url_file;
    }

    public void setUrl_file(String url_file) {
        this.url_file = url_file;
    }

    public Long getSystem() {
        return system;
    }

    public void setSystem(Long system) {
        this.system = system;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }
}
