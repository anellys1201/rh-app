package bd.rh.servicio;

import bd.rh.modelo.Bien;
import bd.rh.repositorio.BienRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BienServicio implements IBienServicio {

    @Autowired
    private BienRepositorio bienRepositorio;

    @Override
    public List<Bien> listarBienes() {
        return bienRepositorio.findAll();
    }
}
