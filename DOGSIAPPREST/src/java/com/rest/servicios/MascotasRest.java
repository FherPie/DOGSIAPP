/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.servicios;

//import apple.laf.JRSUIState;
import com.appdog.entidades.Mascotas;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.CodigosFacade;
import com.appdog.servicios.GrupousuarioFacade;
import com.appdog.servicios.MascotasFacade;
import com.sun.jersey.core.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Esta es un aprueba de andres
 */
@Stateless(name = "Mascotas")
@Path("Mascotas")

public class MascotasRest {

    private Mascotas logeado;
    private Mascotas entidad;
    @EJB
    private MascotasFacade ejbMascotas;
    @EJB
    private CodigosFacade ejbCodigos;
    @EJB
    private GrupousuarioFacade ejbGrpUsr;
    @GET
    @Path("{dueno}")
    @Produces({"application/json"})
    public Response getMascotas(@PathParam("dueno") String dueno) throws ConsultarException {

        Map parametros = new HashMap();
        String[] aux = dueno.split("_");
        String userid = (aux[0]);
        JSONArray mascotas = new JSONArray();
        byte[] foto = new byte[]{};
        parametros.put(";where", "o.dueno=:usuario and o.activo=true");
        parametros.put("usuario", userid);
        List<Mascotas> lista = null;
        try {
            
            
            lista = ejbMascotas.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Logger.getLogger(MascotasRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ((lista == null) || (lista.isEmpty())) {
            return null;
        }

        for (Mascotas m : lista) {
            JSONObject json = new JSONObject();
            try {
                json.put("id", m.getId());
                json.put("nombres", m.getNombres());
                json.put("apellidos", m.getApellidos() == null ? "" : m.getApellidos() );
                json.put("raza", m.getRaza());
                json.put("color1", m.getColor1());
                json.put("color2",  m.getColor2() == null ? "" : m.getColor2());
                json.put("dueno",  m.getDueno() == null ? "" : m.getDueno());
                json.put("responsable",  m.getResponsable() == null ? "" : m.getResponsable());
                json.put("tipopelo", m.getTipopelo()  );
                json.put("edad", m.getEdad());
                json.put("edad", m.getEdad());
                json.put("status", m.getStatus());
                json.put("observacion", m.getObservacion());
                if(m.getFoto()!=null){
                json.put("pin", new String(Base64.encode(m.getFoto())));
                }
            } catch (JSONException ex) {
                Logger.getLogger(MascotasRest.class.getName()).log(Level.SEVERE, null, ex);
            }
            mascotas.put(json);
        }
        return Response.ok("" + mascotas + "").build();

    }

    @POST
    @Path("/registro")
    @Consumes("application/json")
    @Produces({"application/json"})
    public Response RegistroMascota(Mascotas enti) throws ConsultarException {
        JSONArray mensajes = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            entidad = new Mascotas();
            entidad = (Mascotas) enti;
            String base64Image = entidad.getPin();
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            entidad.setPin(null);
            String Mensaje = null;
            try {
                entidad.setFoto(imageBytes);
                entidad.setActivo(Boolean.TRUE);
                ejbMascotas.create(entidad, "ds");
                json = new JSONObject();
                json.append("Mensaje", "Haz registrado a esta mascota: "+entidad.getNombres());
                json.append("tipo", 1);
                mensajes.put(json);
                json = new JSONObject();
                 json.put("id", entidad.getId());
                json.put("nombres", entidad.getNombres());
                json.put("apellidos", entidad.getApellidos() == null ? "" : entidad.getApellidos() );
                json.put("raza", entidad.getRaza());
                json.put("color1", entidad.getColor1());
                json.put("color2",  entidad.getColor2() == null ? "" : entidad.getColor2());
                json.put("tipopelo", entidad.getTipopelo());
                json.put("edad", entidad.getEdad());
                json.put("edad", entidad.getEdad());
                json.put("status", entidad.getStatus());
                json.put("observacion", entidad.getObservacion());
                if(entidad.getFoto()!=null){
                json.put("pin", new String(Base64.encode(entidad.getFoto())));
                }
            } catch (InsertarException ex) {
                System.out.println("Causa: " + ex.getCause() + "Mensaje: " + ex.getMessage());
                Logger.getLogger(MascotasRest.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(MascotasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        mensajes.put(json);

        return Response.ok("" + mensajes + "").build();
    }

}
