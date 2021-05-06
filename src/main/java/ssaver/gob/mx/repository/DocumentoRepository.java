package ssaver.gob.mx.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ssaver.gob.mx.model.Documento;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentoRepository implements PanacheRepository<Documento> {

}
