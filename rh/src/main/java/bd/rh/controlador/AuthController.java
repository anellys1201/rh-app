/*
* Un controller es el que recibe peticiones HTTP del frontend
* */

package bd.rh.controlador;

/*
* Importaciones
* */
import bd.rh.modelo.Usuario; //Trae la clase Usuario del paquete modelo
import bd.rh.servicio.UsuarioServicio; //Controller -> Servicio -> Repository -> DB.
import org.springframework.beans.factory.annotation.Autowired; //Inyeccion de dependencias
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; //Activa anotaciones web

@RestController //Significa que esta clase (AuthController) recibe peticiones REST y devuelve automaticamente JSON
@RequestMapping("/auth") //Define la ruta base: http://localhost:8080/auth
@CrossOrigin("*") //Permite que React (otro puerto) pueda llamar al backend, sin esto, el navegador bloquea la peticion por seguridad
public class AuthController
{
    @Autowired
    private UsuarioServicio servicio; //Inyeccion del servicio: UsuarioServicio servicio = new UsuarioServicio();

    @PostMapping("/login") //Endpoint. URL especifica que llamamos para solicitar, enviar u obtener informacion a traves de http.
    public ResponseEntity<?> login(@RequestBody Usuario usuario) //Metodo login de respuesta HTTP que recibe usuario y contraseña desde react
    {
        Usuario user = servicio.login(
                usuario.getUsername(), usuario.getPassword()
        );

        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(user);
    }
}
