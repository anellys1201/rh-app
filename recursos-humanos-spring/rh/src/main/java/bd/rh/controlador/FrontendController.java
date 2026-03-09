package bd.rh.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //Indicamos que es controlador y que sirve para paginas web
public class FrontendController {
    // Esta ruta captura cualquier URL que no tenga un punto (para ignorar archivos .js, .css, etc..)
    @GetMapping(value = "/{path:[^\\.]*}") //La expresion: /{path:[^\.]*} indica "cualquier ruta que no tenga un punto"
    public String redirect()
    {
        return "forward:/index.html";
    }
}
