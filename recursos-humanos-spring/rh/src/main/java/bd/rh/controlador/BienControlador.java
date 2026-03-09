package bd.rh.controlador;

import bd.rh.modelo.Bien;
import bd.rh.servicio.IBienServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rh-app/bienes")
@CrossOrigin(origins = "http://localhost:3000")
public class BienControlador {
    @Autowired
    private IBienServicio bienServicio;

    @GetMapping
    public List<Bien> listarBienes() {
        return bienServicio.listarBienes();
    }
}
