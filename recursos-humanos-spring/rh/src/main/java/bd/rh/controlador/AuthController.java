package bd.rh.controlador;

import bd.rh.modelo.Usuario;
import bd.rh.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController
{
    @Autowired
    private UsuarioServicio servicio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario)
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
