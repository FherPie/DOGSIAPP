/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.servicios;

//import apple.laf.JRSUIState;
import com.appdog.entidades.Entidades;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.CodigosFacade;
import com.appdog.servicios.EntidadesFacade;
import com.appdog.servicios.GrupousuarioFacade;
import com.rest.utilitarios.Codificador;
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
 * @author evconsul
 */
@Stateless(name = "Login")
@Path("Login")
public class LoginRest {
    
     private Entidades logeado;
     private Entidades entidad;

    @EJB
    private EntidadesFacade ejbEntidades;
    @EJB
    private CodigosFacade ejbCodigos;
    @EJB
    private GrupousuarioFacade ejbGrpUsr;

    @POST
    @Path("{logeo}")
    @Produces({"application/json"})
    public Response getUsuario(@PathParam("logeo") String logeo) throws ConsultarException {

        Map parametros = new HashMap();
        String[] aux = logeo.split("_");
        String userid = (aux[0]);
        String clave = (aux[1]);
        Codificador c = new Codificador();
        String passw = c.getEncoded(clave, "MD5");
        JSONArray usuarios = new JSONArray();
        byte[] foto = new byte[]{};
        parametros.put(";where", "o.userid=:usuario and o.pwd=:password and o.activo=true");
        parametros.put("usuario", userid);
        parametros.put("password", passw);
        List<Entidades> lista = null;
        try {
            lista = ejbEntidades.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ((lista == null) || (lista.isEmpty())) {

            return null;
        }

        logeado = lista.get(0);

        JSONObject json = new JSONObject();
        try {
            json.put("nombres", logeado.getNombres());
            json.put("email", logeado.getEmail());
            json.put("id", logeado.getId());
            json.put("userid", logeado.getUserid());
            json.put("tk", c.getEncoded(logeado.getUserid() + "APP" + logeado.getPin(), "MD5"));
        } catch (JSONException ex) {
            Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        usuarios.put(json);

        return Response.ok("" + usuarios + "").build();

    }
    

    

    @POST
    @Path("/registro")
    @Consumes("application/json")
    @Produces({"application/json"})
    public Response RegistroUsuario(Entidades enti) throws ConsultarException {
        
        JSONArray mensajes = new JSONArray();
        JSONObject json = new JSONObject();
        
        try { 
        entidad= new Entidades();
        entidad  = (Entidades) enti;
        entidad.setApellidos(null);
//        entidad.setUserid(entidad.getEmail());//Se establece como usuario el mail
         Codificador c = new Codificador();
        entidad.setPwd(c.getEncoded(entidad.getPwd(), "MD5"));
//        entidad=enti;
    
       
         String Mensaje=null;

        
        Map parametros = new HashMap();
//        parametros.put(";where", "(o.email=:email  ) and o.activo=true");
        parametros.put(";where", "(o.userid=:userid) and o.activo=true");
//        parametros.put("email", entidad.getEmail());
        parametros.put("userid", entidad.getUserid());
         List<Entidades> lista = null;
        try {
            lista = ejbEntidades.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        if (!lista.isEmpty()) {
            Mensaje="Usuario ya registrado";
//            return null;
        }
        
        if(Mensaje!=null &&  !Mensaje.isEmpty() ){
         json = new JSONObject();
         json.append("Mensaje", Mensaje);
         json.append("tipo", 0);
        }else{
           try {
               entidad.setActivo(Boolean.TRUE);
               entidad.setDireccion(null);
               entidad.setFecha(null);
//               entidad.setId(null);
               entidad.setPin(null);
               entidad.setPregunta(null);
               entidad.setRol(null);
//               entidad.setUserid(null);
               entidad.setVertodos(Boolean.FALSE);
               ejbEntidades.create(entidad, "ds");
               json = new JSONObject();
               json.append("Mensaje", "Gracias por registrarse");
               json.append("tipo", 1);
                 
        } catch (InsertarException ex) {
               System.out.println("Causa: "+ex.getCause()+"Mensaje: "+ex.getMessage());
            Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }  
        
                } catch (JSONException  ex) {
                    Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
                }
        mensajes.put(json);

        return Response.ok("" + mensajes + "").build();
    }
    
    
    
    @GET
    @Path("/xid/{id}")
    @Produces({"application/json"})
    public Response getUsuarioid(@PathParam("id") String id) throws ConsultarException {
        Codificador c = new Codificador();
        JSONArray usuarios = new JSONArray();
        JSONObject json = new JSONObject();
        Integer idm = Integer.parseInt(id);
         logeado= ejbEntidades.find(idm);
        try {
            json.put("nombres", logeado.getNombres());
//                  json.put("apellidos", logeado.getApellidos());
//                  json.put("foto", new String(Base64.encode(foto)) );
            json.put("email", logeado.getEmail());
            json.put("id", logeado.getId());
            json.put("userid", logeado.getUserid());
//                  json.put("rol", grpUsuario.getGrupo().getNombre());
            json.put("tk", c.getEncoded(logeado.getUserid() + "APP" + logeado.getPin(), "MD5"));
//                  json.put("institucion", institucion.getNombre());
//                  json.put("logoinstitucion", new String(Base64.encode(institucion.getLogotipo().getFoto())));
//                  json.put("lectivo", lectivo.getAniolectivo());
//                  json.put("genero", logeado.getGenero()==null ?"" :logeado.getGenero().getNombre());
        } catch (JSONException ex) {
            Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        usuarios.put(json);
        return Response.ok("" + usuarios + "").build();

    }
    
    
    
 
    
    

}
