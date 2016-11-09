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
//    @EJB
//    private ProfesoresFacade ejbProfesor;
    @EJB
    private CodigosFacade ejbCodigos;
    @EJB
    private GrupousuarioFacade ejbGrpUsr;
//    @EJB
//    private AnioslectivosFacade ejbAniosLectivos;


//    private  Grupousuario grpUsuario;
//    private Instituciones institucion;
//    private Anioslectivos lectivo;
    @GET
    @Path("{logeo}")
    @Produces({"application/json"})
    public Response getUsuario(@PathParam("logeo") String logeo) throws ConsultarException {

        Map parametros = new HashMap();
        String[] aux = logeo.split("_");
        String userid = (aux[0]);
        String clave = (aux[1]);
//        String rol = (aux[2]);
        Codificador c = new Codificador();
        String passw = c.getEncoded(clave, "MD5");
        JSONArray usuarios = new JSONArray();
//        Profesores profe= new Profesores();
        byte[] foto = new byte[]{};
//        List<AuxiliarEntidades> aLista = new LinkedList<>();
        parametros.put(";where", "o.userid=:usuario and o.pwd=:password and o.activo=true");
        parametros.put("usuario", userid);
        parametros.put("password", passw);
//        parametros.put("rol","%"+rol.toUpperCase()+"%");
        List<Entidades> lista = null;
        try {
            lista = ejbEntidades.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ((lista == null) || (lista.isEmpty())) {
//            AuxiliarMensajesServidor mensaje= new AuxiliarMensajesServidor();
//            mensaje.setNombre("Usuario no Entontrado");
//            GenericEntity< AuxiliarMensajesServidor> entity;
//            entity = new GenericEntity<AuxiliarMensajesServidor>(mensaje) {
//             };
//              return Response.ok(entity).build();
            return null;
        }

        logeado = lista.get(0);
//        parametros = new HashMap();
//        parametros.put(";where", "o.codigo=:codigo");
//        parametros.put("codigo", rol);
//        List<Codigos> grupo=ejbCodigos.encontarParametros(parametros);

//        parametros = new HashMap();
//        parametros.put(";where", "o.usuario=:usuario and o.grupo=:grupo");
//        parametros.put("usuario", logeado.getPin());
//        parametros.put("grupo", grupo.get(0));
//        
//        
//        List<Grupousuario> lgu = ejbGrpUsr.encontarParametros(parametros);
//         if (!((lgu == null) || (lgu.isEmpty()))) {
//           String passw = c.getEncoded(clave, "MD5");             grpUsuario = lgu.get(0);
//                    if (grpUsuario.getInstitucion() != null) {
//                        institucion = grpUsuario.getInstitucion();
//                        lectivo= ejbAniosLectivos.anioLectivo(institucion);
//                    }
//          }else{
//             
//             
//           return  null;
//         
//         }
//         
//          if(rol.equals("dct")){
//              parametros= new HashMap();
//              parametros.put(";where", "o.profesor=:profesor");
//              parametros.put("profesor", logeado);
//              List<Profesores> apro=  ejbProfesor.encontarParametros(parametros);
//      
//              for(Profesores pro: apro){
//               profe= pro;
//              }
//             foto=profe.getFotografia().getFoto();
//          }
//          Entidades docente= new Entidades();
//           for(Entidades ent: lista){
//             docente=ent;
//           }
//          parametros = new HashMap();
//          parametros.put(";where", "o.profesor=:profesor and o.activo=true");
//          parametros.put("profesor", docente);
//          try {
//                listaProf=ejbProfesor.encontarParametros(parametros);
//            } catch (ConsultarException ex) {
//                Logger.getLogger(LoginRest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//       
//           if(listaProf==null || listaProf.isEmpty()) {
//               return null;
//           }
//       
////           Profesores profe= new Profesores():
//           
//          Profesores profes= new Profesores();
//           
//          for(Profesores pro: listaProf){
//                  profes=pro;  
//          }
//       for (Profesores l : listaProf) {
//            AuxiliarEntidades al = new AuxiliarEntidades();
//            al = new AuxiliarEntidades();
//            al.setActivo(l.getActivo());
//            al.setNombres(l.getProfesor().getNombres());
//            al.setApellidos(l.getProfesor().getApellidos());
//            al.setEmail(l.getProfesor().getEmail());
//            al.setId(l.getId());
//            al.setUserid(l.getProfesor().getUserid());
//            al.setRoles(l.getProfesor().getRol());
//            al.setInstitucion(l.getInstitucion().getNombre());
////            al.setTelefono(l.ge == null ? "" : l.getTelefono().toString());
////            al.setTelefono1(l.getTelefono1() == null ? "" : l.getTelefono1().toString());
////            al.setNombre(l.getNombre());
////            al.setCodigo(l.getCodigo());
////            al.setLatitud(l.getLatitud() == null ? 0 : l.getLatitud().doubleValue());
////            al.setLongitud(l.getLongitud() == null ? 0 : l.getLongitud().doubleValue());
//              aLista.add(al);
        JSONObject json = new JSONObject();
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
//        } 

//        if(usuarios.length()==0){
//        return null;
//        }
//        GenericEntity< List< AuxiliarEntidades>> entity;
//        entity = new GenericEntity<List<AuxiliarEntidades>>(aLista) {
//        };
        return Response.ok("" + usuarios + "").build();

    }
    

    

    @POST
    @Path("/registro")
    @Consumes("application/json")
    @Produces({"application/json"})
    public Response RegistroUsuario(Entidades enti) throws ConsultarException {
        
//      ObjectMapper mapper = new ObjectMapper();
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
        
//        if(entidad.getNombres()==null || entidad.getNombres().isEmpty()  ){
////        Mensaje+="*No Ingreso Nombres";
//                  return null;
//        }
        
//                ValidadorEmail email= new ValidadorEmail();
//        if(entidad.getEmail()==null || email.validate(entidad.getEmail())==false  ){
////        Mensaje+="*Mail no Ingresado o Invalido";
//                  return null;
//        }
        
//        if(entidad.getApellidos()==null || entidad.getApellidos().isEmpty()  ){
////        Mensaje+="*No Re ingreso Contraseña";
//                  return null;
//        }
//        
//        if(entidad.getPwd()==null || entidad.getPwd().isEmpty()){
////        Mensaje+="*Contraseña no Ingresado";
//          return null;
//        }
//        
//       if(!entidad.getPwd().equals(entidad.getApellidos())){
////        Mensaje+="*Contraseñas no Iguales";
//         return null;
//        }
        
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
    
    
    
    
    
    
    
 
    
    

}
