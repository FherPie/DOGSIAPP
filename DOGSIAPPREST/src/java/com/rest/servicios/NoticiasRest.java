/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.servicios;

//import apple.laf.JRSUIState;
import com.appdog.entidades.Entidades;
import com.appdog.entidades.Mascotas;
import com.appdog.entidades.Noticias;
import com.appdog.excepciones.BorrarException;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.EntidadesFacade;
import com.appdog.servicios.MascotasFacade;
import com.appdog.servicios.NoticiasFacade;
import com.sun.jersey.core.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author evconsul
 */
@Stateless(name = "Noticias")
@Path("Noticias")

public class NoticiasRest {

    private Noticias noticia;

    @EJB
    private MascotasFacade ejbMascotas;
    @EJB
    private NoticiasFacade ejbNoticias;
    @EJB
    private EntidadesFacade ejbEntidades;
    
    
//    @EJB
//    private GrupousuarioFacade ejbGrpUsr;

    @GET
    @Path("{dueno}")
    @Produces({"application/json"})
    public Response getNoticias(@PathParam("dueno") String dueno) throws ConsultarException {

        Map parametros = new HashMap();
        String[] aux = dueno.split("_");
        String iduser = (aux[0]);

        List<Noticias> lista = null;
        JSONArray noticias = new JSONArray();
        if (!"-1".equals(iduser)) {
            parametros.put(";orden", "o.fecha  DESC");
            parametros.put(";where", "o.entidad=:usuario and o.activo=true");
            parametros.put("usuario", iduser);
            try {
                lista = ejbNoticias.encontarParametros(parametros);
            } catch (ConsultarException ex) {
                Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            parametros.put(";orden", "o.fecha  DESC");
            parametros.put(";where", "o.activo=true");
            try {
                lista = ejbNoticias.encontarParametros(parametros);
            } catch (ConsultarException ex) {
                Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if ((lista == null) || (lista.isEmpty())) {
            return null;
        }

        for (Noticias m : lista) {
            JSONObject json = new JSONObject();
            try {
                json.put("id", m.getId());
                json.put("asunto", m.getAsunto());
                json.put("cuerpo", m.getCuerpo());
                json.put("fecha",  m.getFecha() == null ? "" : m.getFecha().getTime());
                json.put("entidad",  m.getEntidad());
                json.put("mascota",  m.getMascota());
                if (m.getFoto() != null) {
                    json.put("foto", new String(Base64.encode(m.getFoto())));
                }else{
                json.put("foto", "null");
                }
//                Integer id = Integer.parseInt(m.getEntidad());
//                Entidades publicador = ejbEntidades.find(id);
//                JSONObject publicadorrenti = new JSONObject();
//                publicadorrenti.put("id", publicador.getId());
//                publicadorrenti.put("nombres", publicador.getNombres());
//                publicadorrenti.put("userid", publicador.getUserid());
//                publicadorrenti.put("email", publicador.getEmail());
//                publicadorrenti.put("telefonos",   publicador.getTelefonos() == null ? "" :  publicador.getTelefonos());
//                json.put("entidad", publicadorrenti);

//                if (m.getMascota() != null && !m.getMascota().isEmpty()) {
//                    Integer idm = Integer.parseInt(m.getMascota());
//                    Mascotas mascota = ejbMascotas.find(idm);
//                    JSONObject publicacionmascota = new JSONObject();
//                    publicacionmascota.put("id", mascota.getId());
//                    publicacionmascota.put("nombres", mascota.getNombres());
//                    publicacionmascota.put("apellidos", mascota.getApellidos() == null ? "" : mascota.getApellidos());
//                    publicacionmascota.put("raza", mascota.getRaza());
//                    publicacionmascota.put("color1", mascota.getColor1());
//                    publicacionmascota.put("color2", mascota.getColor2() == null ? "" : mascota.getColor2());
//                    publicacionmascota.put("dueno", mascota.getDueno() == null ? "" : mascota.getDueno());
//                    publicacionmascota.put("responsable", mascota.getResponsable() == null ? "" : mascota.getResponsable());
//                    publicacionmascota.put("tipopelo", mascota.getTipopelo());
//                    publicacionmascota.put("edad", mascota.getEdad());
//                    publicacionmascota.put("status", mascota.getStatus());
//                    publicacionmascota.put("observacion", mascota.getObservacion());
//                    if (mascota.getFoto() != null) {
//                        publicacionmascota.put("pin", new String(Base64.encode(mascota.getFoto())));
//                    }
//                    json.put("mascota", publicacionmascota);
//                }

            } catch (JSONException ex) {
                Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
            noticias.put(json);
        }
        return Response.ok("" + noticias + "").build();

    }
    
    @GET
    @Path("/xid/{id}")
    @Produces({"application/json"})
    public Response getNoticia(@PathParam("id") String id) throws ConsultarException {
            JSONArray noticias = new JSONArray();
            JSONObject json = new JSONObject();
            Integer idm = Integer.parseInt(id);
            Noticias m= ejbNoticias.find(idm);
            try {
                json.put("id", m.getId());
                json.put("asunto", m.getAsunto());
                json.put("cuerpo", m.getCuerpo());
                json.put("fecha",  m.getFecha() == null ? "" : m.getFecha().getTime());
                if (m.getFoto() != null) {
                    json.put("foto", new String(Base64.encode(m.getFoto())));
                }else{
                json.put("foto", "null");
                }
            } catch (JSONException ex) {
                Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
            noticias.put(json);
        return Response.ok("" + noticias + "").build();

    }
    
    @DELETE
    @Path("/xid/{id}")
    @Produces({"application/json"})
    public Response borrarMascota(@PathParam("id") Integer id) throws ConsultarException {
//        Integer idm = Integer.parseInt(id);
        Noticias m= ejbNoticias.find(id);
        JSONObject json = new JSONObject();
//        JSONArray mensajes = new JSONArray();
        try {
            try {
                ejbNoticias.remove(m, "ds");
                json = new JSONObject();
                json.append("Mensaje", "Eliminado");
                json.append("tipo", 1);
//                mensajes.put(json);
            } catch (BorrarException ex) {
                System.out.println("Causa: " + ex.getCause() + "Mensaje: " + ex.getMessage());
                Logger.getLogger(MascotasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(MascotasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
//        mensajes.put(json);

        return Response.ok("" + json + "").build();
    }
    
    
    

    @POST
    @Path("/registro")
    @Consumes("application/json")
    @Produces({"application/json"})
    public Response setNoticia(Noticias noti) throws ConsultarException {
        JSONArray noticias = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            noticia = new Noticias();
            noticia = (Noticias) noti;
            try {
                noticia.setActivo(Boolean.TRUE);
                ejbNoticias.create(noticia, "ds");
               
                
            } catch (InsertarException ex) {
                System.out.println("Causa: " + ex.getCause() + "Mensaje: " + ex.getMessage());
                Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                json = new JSONObject();
                json.append("Mensaje", "Publicación Exitosa");
                json.append("tipo", 1);
                noticias.put(json);
                json = new JSONObject();
                json.put("id", noticia.getId());
                json.put("asunto", noticia.getAsunto());
                json.put("cuerpo", noticia.getCuerpo());
                json.put("fecha", noticia.getFecha());
                json.put("entidad", noticia.getEntidad());
                json.put("mascota", noticia.getMascota() == null ? "" : noticia.getMascota());
                if (noticia.getFoto() != null) {
                    json.put("foto", new String(Base64.encode(noticia.getFoto())));
                }else{
                json.put("foto", "null");
                }
        } catch (JSONException ex) {
            Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        noticias.put(json);
        
   Response huy=     Response.ok("" + noticias + "").build();

        return  Response.ok("" + noticias + "").build();
    }
    
    
    
    @PUT
    @Path("/registro")
    @Consumes("application/json")
    @Produces({"application/json"})
    public Response editNoticia(Noticias noti) throws ConsultarException {
        JSONArray noticias = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            noticia = new Noticias();
            noticia = (Noticias) noti;
            try {
                noticia.setActivo(Boolean.TRUE);
                ejbNoticias.edit(noticia, "ds");
                json = new JSONObject();
                json.append("Mensaje", "Edición Exitosa");
                json.append("tipo", 1);
                noticias.put(json);
                json = new JSONObject();
                json.put("id", noticia.getId());
                json.put("asunto", noticia.getAsunto());
                json.put("cuerpo", noticia.getCuerpo());
                json.put("fecha", noticia.getFecha());
                json.put("entidad", noticia.getEntidad());
                json.put("mascota", noticia.getMascota() == null ? "" : noticia.getMascota());
            } catch (GrabarException ex) {
                System.out.println("Causa: " + ex.getCause() + "Mensaje: " + ex.getMessage());
                Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(NoticiasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        noticias.put(json);

        return Response.ok("" + noticias + "").build();
    }

}
