package bd.rh.controlador;

import bd.rh.excepcion.RecursoNoEncontradoExcepcion;
import bd.rh.modelo.DetalleBien;
import bd.rh.modelo.Empleado;
import bd.rh.servicio.EmpleadoServicio;
import bd.rh.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
//http:**localhost:8080/rh-app/ejemplo/otracosa/1
@RequestMapping("rh-app")
//para indicar que vamos a hacer peticiones de nuestro frontend, para que spring reciba peticiones de otro puerto
@CrossOrigin(value = "http://localhost:3000")
public class EmpleadoControlador
{
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    // http://localhost:8080/rh-app/empleados
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados()
    {
        var empleados = empleadoServicio.listarEmpleados();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping(value = "/empleados", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Empleado agregarEmpleado(@RequestPart("empleado") Empleado empleado, @RequestPart("archivo")MultipartFile archivo)
    {
        logger.info("Empleado a agregar: " + empleado);

        try {
            String ruta = "documentos/";
            File carpeta = new File(ruta);

            if(!carpeta.exists()){
                carpeta.mkdirs();
            }

            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path rutaArchivo = Paths.get(ruta + nombreArchivo);

            Files.write(rutaArchivo, archivo.getBytes());

            empleado.setDocumento(nombreArchivo);

            return empleadoServicio.guardarEmpleado(empleado);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Integer id)
    {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestPart("empleado") Empleado empleadoRecibido, @RequestPart(value = "archivo", required = false) MultipartFile archivo) throws IOException {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw  new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());

        File carpeta = new File("documentos");
        if(!carpeta.exists()){
            carpeta.mkdirs();
        }

        if(archivo != null && !archivo.isEmpty())
        {
            try {
                // Borrar documento anterior si existe
                if(empleado.getDocumento() != null)
                {
                    Path rutaAnterior = Paths.get("documentos").resolve(empleado.getDocumento());
                    Files.deleteIfExists(rutaAnterior);
                }

                String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                Path rutaArchivo = Paths.get("documentos").resolve(nombreArchivo);
                Files.write(rutaArchivo, archivo.getBytes());
                empleado.setDocumento(nombreArchivo);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        //empleado.getBienes().clear();
        List<DetalleBien> bienesRecibidos = empleadoRecibido.getBienes() != null ? empleadoRecibido.getBienes() : new ArrayList<>();
        if (bienesRecibidos.isEmpty())
        {
            empleado.getBienes().clear();
            empleadoServicio.guardarEmpleado(empleado);
            return ResponseEntity.ok(empleado);
        }

        Map<Integer, DetalleBien> bienesActuales = empleado.getBienes().stream().collect(Collectors.toMap(DetalleBien::getConsec, b -> b));
        List<DetalleBien> nuevaLista = new ArrayList<>();

        /*if(empleadoRecibido.getBienes() != null)
        {
            for (var detalle : empleadoRecibido.getBienes())
            {
                detalle.setEmpleado(empleado);
                empleado.getBienes().add(detalle);
            }
        }*/

        /*for (DetalleBien detalle : empleadoRecibido.getBienes())
        {
            DetalleBien nuevo = new DetalleBien();
            nuevo.setConsec(detalle.getConsec());
            nuevo.setComentario(detalle.getComentario());
            nuevo.setBien(detalle.getBien());
            nuevo.setEmpleado(empleado);

            empleado.getBienes().add(nuevo);
        }*/

        for (DetalleBien recibido : bienesRecibidos)
        {
            if (recibido.getConsec() != null && bienesActuales.containsKey(recibido.getConsec()))
            {
                DetalleBien existente = bienesActuales.remove(recibido.getConsec());
                existente.setComentario(recibido.getComentario());
                existente.setBien(recibido.getBien());

                nuevaLista.add(existente);
            } else
            {
                DetalleBien nuevo = new DetalleBien();
                nuevo.setComentario(recibido.getComentario());
                nuevo.setBien(recibido.getBien());
                nuevo.setEmpleado(empleado);

                nuevaLista.add(nuevo);
            }
        }

        empleado.getBienes().clear();
        empleado.getBienes().addAll(nuevaLista);

        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@PathVariable Integer id)
    {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        empleadoServicio.eliminarEmpleado(empleado);
        // Json {"eliminado": "true"}
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Eliminado", Boolean.TRUE);
        return  ResponseEntity.ok(respuesta);
    }

    @GetMapping("/documentos/{nombre}")
    public ResponseEntity<Resource> verDocumento(@PathVariable String nombre) throws Exception {

        System.out.println("ENTRO AL METODO VER DOCUMENTO");
        Path ruta = Paths.get("documentos").resolve(nombre).toAbsolutePath();
        System.out.println("BUSCANDO ARCHIVO EN:");
        System.out.println(ruta);

        Resource recurso = new UrlResource(ruta.toUri());

        if(!recurso.exists()){
            System.out.println("NO EXISTE EL ARCHIVO");
            throw new RuntimeException("Archivo no encontrado: " + ruta);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(recurso);
    }

    @GetMapping("/test-doc")
    public String testDoc(){
        System.out.println("ENTRO AL TEST DOC");
        return "funciona";
    }
}
