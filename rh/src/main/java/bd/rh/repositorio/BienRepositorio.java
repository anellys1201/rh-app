package bd.rh.repositorio;

import bd.rh.modelo.Bien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BienRepositorio extends JpaRepository<Bien, Integer> {
}
