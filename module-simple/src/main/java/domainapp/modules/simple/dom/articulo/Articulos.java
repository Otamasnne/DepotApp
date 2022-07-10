package domainapp.modules.simple.dom.articulo;


import domainapp.modules.simple.dom.comprobante.ajuste.AjusteNegativo;
import domainapp.modules.simple.dom.comprobante.ajuste.AjustePositivo;
import domainapp.modules.simple.types.articulo.CodigoArticulo;
import domainapp.modules.simple.types.articulo.Descripcion;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "depotapp.Articulos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Articulos {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Articulo create(
            @CodigoArticulo final String codigo,
            @Descripcion final String descripcion) {
        return repositoryService.persist(Articulo.withName(codigo, descripcion));
    }

    //Esta acción debe generar un comprobante de tipo AJP.
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public String ajustePositivo(String articulo, int cantidad){
        Articulo objetivo = findByCodigoExact(articulo);
        objetivo.setStock(objetivo.getStock()+cantidad);
        AjustePositivo comprobante = repositoryService.persist(AjustePositivo.creacion(objetivo, cantidad));
        return "Se realizó el ajuste positivo para el artículo " + articulo + " por " + cantidad + " unidades. " +
                "Comprobante: " + comprobante.title();
    }

    //Esta acción debe generar un comprobante de tipo AJN.
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public String ajusteNegativo(String articulo, int cantidad){
        Articulo objetivo = findByCodigoExact(articulo);
        objetivo.setStock(objetivo.getStock()-cantidad);
        AjusteNegativo comprobante = repositoryService.persist(AjusteNegativo.creacion(objetivo, cantidad));
        return "Se realizó el ajuste negativo para el artículo " + articulo + " por " + cantidad + " unidades. " +
                "Comprobante: " + comprobante.title();
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Articulo> findByCodigo(
            @CodigoArticulo final String codigo
    ) {
        return repositoryService.allMatches(
                Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_CODIGO_LIKE)
                        .withParameter("codigo", codigo));
    }



    @Programmatic
    public Articulo findByCodigoExact(final String codigo) {
        return repositoryService.firstMatch(
                        Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_CODIGO_EXACT)
                                .withParameter("codigo", codigo))
                .orElse(null);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Articulo> listAll() {
        return repositoryService.allInstances(Articulo.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Articulo> articulosHabilitados() {
        return repositoryService.allMatches(
                Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_HABILITADO)
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Articulo> articulosDeshabilitados() {
        return repositoryService.allMatches(
                Query.named(Articulo.class, Articulo.NAMED_QUERY__FIND_BY_DESHABILITADO)
        );
    }







   @Programmatic
  public void ping() {
       JDOQLTypedQuery<Articulo> q = jdoSupportService.newTypesafeQuery(Articulo.class);
       final QArticulo candidate = QArticulo.candidate();
        q.range(0,2);
        q.orderBy(candidate.codigo.asc());
        q.executeList();
    }




}
