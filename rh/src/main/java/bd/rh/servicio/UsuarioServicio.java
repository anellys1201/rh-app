package bd.rh.servicio;

import bd.rh.modelo.Usuario;
import bd.rh.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio repo;

    public Usuario login(String username, String password)
    {
        Usuario usuario = repo.findByUsername(username);

        if(usuario != null && usuario.getPassword().equals(password))
        {
            return usuario;
        }

        return null;
    }
}
