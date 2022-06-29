package domainapp.modules.simple.dom.comprobante;

import domainapp.modules.simple.dom.articulo.Articulo;
import domainapp.modules.simple.dom.comprobante.ajuste.AjusteNegativo;
import domainapp.modules.simple.dom.comprobante.ajuste.AjustePositivo;
import domainapp.modules.simple.types.articulo.Codigo;
import domainapp.modules.simple.types.articulo.Descripcion;
import domainapp.modules.simple.types.comprobante.CantidadMueve;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Comprobantes"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Comprobantes {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

/*
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public AjustePositivo crearAJP(
            final Articulo articulo,
            @CantidadMueve final int cantidad) {
        return repositoryService.persist(AjustePositivo.creacion(articulo, cantidad));
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public AjusteNegativo crearAJN(
            final Articulo articulo,
            @CantidadMueve final int cantidad) {
        return repositoryService.persist(AjusteNegativo.creacion(articulo, cantidad));
    }


 */
}



