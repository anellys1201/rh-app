package bd.rh.repositorio;

import bd.rh.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>
{
    Usuario findByUsername(String username);
    /* CON Query Method Naming (findBy...)
    * Spring tiene una funcionalidad que se llama Query Method Naming
    * que significa que Spring interpreta nombres como lenguaje para
    * construir solito el SQL pero de forma invisible sin que veamos
    * la consulta de SQL en la interface
    *
    * USO: Para consultas simples y usar lo mas posible
    * */

    /* CON @Query JPQL
    * Con @Query escribimos nuestra propia consulta
    * Ejemplo si quisieramos: SELECT * FROM usuario WHERE username = ?
    * seria:
    * @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    * Usuario buscarUsuario(@Param("username") String username);
    * Esto no es SQL normal es JPQL(Java Persistence Query Language)
    * Usa nombres de la clase y atributos del objeto, no nombres fisicos de tablas
    *
    * USO: Para consultas mas avanzadas con JOINS o mas especificas. Este debe ser el modo principal para estos casos
    * */

    /* Con @Query y SQL Puro (nativeQuery)
    * @Query(
    *   value = "SELECT * FROM usuario WHERE username = :username", nativeQuery = true
    * )
    * Usuario loginSQL(@Param("username") String username);
    * Aqui hablamos directamente con la BD
    *
    * USO: Solo para consultas que @Query JPQL no puede procesar
    * , consultas MUY optimizadas o complejas, uso de funciones especificas
    * , Stored procedures, etc..
    * */
}
