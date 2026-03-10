/* global Swal */

import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

export default function EditarEmpleado() 
{
    const urlBase = "/rh-app/empleados";
    const urlBien = "/rh-app/bienes";
    
    let navegacion = useNavigate();

    const {id} = useParams();

    const [empleado, setEmpleado] = useState({
        nombre:"",
        departamento:"",
        sueldo:"",
        bienes:[]
    })

    const [catalogoBienes, setCatalogoBienes] = useState([]);

    const{nombre, departamento, sueldo} = empleado

    const [archivo, setArchivo] = useState(null);

    const [bienActual, setBienActual] = useState({
        idBien: "",
        comentario: ""
    });

    useEffect(() => {
        cargarEmpleado();
        cargarCatalogoBienes();
    }, [])

    const cargarEmpleado = async () => {
        const resultado = await axios.get(`${urlBase}/${id}`)
        setEmpleado(resultado.data);
    }

    const cargarCatalogoBienes = async () => {
        const resultado = await axios.get(`${urlBien}`);
        setCatalogoBienes(resultado.data);
    }

    const manejarCambio = (e) => {
        setArchivo(e.target.files[0]);
    }

    const onInputChange = (e) => {
        //spread operator ... es para expandir atributos
        setEmpleado({...empleado, [e.target.name]: e.target.value})
    }

    const onSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();

        formData.append(
            "empleado",
            new Blob([JSON.stringify(empleado)], { type: "application/json" })
        );

        if(archivo)
        {
            formData.append("archivo", archivo);
        }

        try {

            console.log('Entro al try');
            console.log(formData);

            await axios.put(
                `${urlBase}/${id}`,
                formData
            );

            Swal.fire({
                title: 'Correcto',
                text: 'Empleado actualizado',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });

            navegacion('/listado');

        } catch (error) {

            console.error(error);

            Swal.fire({
                title: 'Error',
                text: 'Error al actualizar',
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });

        }
    }

    /*const agregarBien = () => {
        setEmpleado({
            ...empleado,
            bienes:[
                ...empleado.bienes,
                {
                    bien:{idBien:""},
                    comentario:""
                }
            ]
        });
    }

    const onBienChange = (index, e) => {
        const nuevosBienes = [...empleado.bienes];

        if(e.target.name === "idBien")
        {
            //nuevosBienes[index].bien.idBien = e.target.value;
            nuevosBienes[index] = { 
                ...nuevosBienes[index],
                bien:{
                    idBien: Number(e.target.value) 
                }
            };
        } else 
        {
            //nuevosBienes[index].comentario = e.target.value;
            nuevosBienes[index] = { 
                ...nuevosBienes[index], 
                comentario: e.target.value 
            };
        }

        setEmpleado({
            ...empleado,
            bienes:nuevosBienes
        });
    }

    const eliminarBien = (index) => {
        const nuevosBienes = empleado.bienes.filter((_,i)=> i !== index);
        setEmpleado({
            ...empleado,
            bienes:nuevosBienes
        });
    }*/

    const agregarBien = () => {

        if(!bienActual.idBien){
            Swal.fire({
                title: 'Advertencia',
                text: 'Seleccione un bien',
                icon: 'warning'
            });
            return;
        }

        const nuevoBien = {
            bien: { idBien: bienActual.idBien },
            comentario: bienActual.comentario
        };

        setEmpleado({
            ...empleado,
            bienes: [...empleado.bienes, nuevoBien]
        });

        // limpiar inputs
        setBienActual({
            idBien: "",
            comentario: ""
        });
    };

    const onBienChange = (e) => {
        setBienActual({
            ...bienActual,
            [e.target.name]: e.target.value
        });
    };

    const eliminarBien = (index) => {
        const nuevosBienes = empleado.bienes.filter((_, i) => i !== index);

        setEmpleado({
            ...empleado,
            bienes: nuevosBienes
        });
    };

  return (
    <div className="container">
        <div className='container text-center' style={{margin: "30px"}}>
            <h3>Editar Empleado</h3>
        </div>

        <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
                <label htmlFor="nombre" className="form-label">Nombre</label>
                <input type="text" className="form-control" id="nombre" name="nombre" 
                    required={true} value={nombre} onChange={(e)=>onInputChange(e)} />
            </div>
            <div className="mb-3">
                <label htmlFor="departamento" className="form-label">Departamento</label>
                <input type="text" className="form-control" id="departamento" name="departamento"
                    value={departamento} onChange={(e)=>onInputChange(e)} />
            </div>
            <div className="mb-3">
                <label htmlFor="sueldo" className="form-label">Sueldo</label>
                <input type="number" step="any" className="form-control" id="sueldo" name="sueldo"
                    value={sueldo} onChange={(e)=>onInputChange(e)} />
            </div>

            <div className="mb-3">
                <label htmlFor="archivo" className="form-label">Subir documento PDF</label>
                <input type="file" accept="application/pdf" className="form-control" id="archivo" name="archivo"
                    onChange={manejarCambio} />
            </div>

            {empleado.documento && (
                <div className="mb-3">
                    <label className="form-label">Documento actual</label>
                    <br/>

                    <a
                        href={`/rh-app/documentos/${empleado.documento}`}
                        target="_blank"
                        rel="noreferrer"
                        className="btn btn-info btn-sm"
                    >
                        Ver PDF
                    </a>
                </div>
            )}
            
            <div className='text-center'>
                <button type="submit" className="btn btn-warning btn-sm me-3">Guardar</button>
                <a href="/listado" className='btn btn-danger btn-sm'>Regresar</a>
            </div>

            <h5>Bienes asignados</h5>

            <div className='border p-2 mb-2'>
                <select className='form-control mb-2' name='idBien' value={bienActual.idBien} onChange={onBienChange}>
                    <option value="">Seleccione bien</option>
                    {
                        catalogoBienes.map(bien=>(
                            <option key={bien.idBien} value={bien.idBien}>{bien.nombre}</option>
                        ))
                    }
                </select>

                <input type='text' className='form-control' placeholder='Comentario' name='comentario' value={bienActual.comentario} onChange={onBienChange}/>

            </div>

            <button type="button" className='btn btn-primary btn-sm mb-3' onClick={agregarBien}>Agregar Bien</button>

            <table className="table table-striped table-hover aling-middle">
                <thead className='table-dark'>
                    <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Bien</th>
                    <th scope="col">Descripción</th>
                    <th scope="col">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        //Iteramos el arreglo de bienes
                        empleado.bienes.map((item, index) => {
                            const bienInfo = catalogoBienes.find(
                                b => b.idBien == item.bien.idBien
                            );

                            return (
                                <tr key={index}>
                                <th scope="row">{item.bien.idBien}</th>
                                <td>{bienInfo?.nombre}</td>
                                <td>{item.comentario}</td>
                                <td className='text-center'>
                                    <div>
                                        <button type="button" className='btn btn-danger btn-sm' onClick={()=>eliminarBien(index)}>Eliminar</button>
                                    </div>
                                </td>
                            </tr>
                            )
                        })
                    }
                </tbody>
            </table>

            {
              /*
                <button type="button" className='btn btn-primary btn-sm mb-3' onClick={agregarBien}>Agregar Bien</button>

                {
                    empleado.bienes?.map((item,index)=>(
                        <div key={index} className='border p-2 mb-2'>
                            <select className='form-control mb-2' name='idBien' value={item.bien?.idBien || ""} onChange={(e)=>onBienChange(index,e)}>
                                <option value="">Seleccione bien</option>

                                {
                                    catalogoBienes.map(bien=>(
                                        <option key={bien.idBien} value={bien.idBien}>{bien.nombre}</option>
                                    ))
                                }

                            </select>

                            <input type="text" className='form-control mb-2' name='comentario' value={item.comentario || ""} placeholder='Comentario' onChange={(e)=>onBienChange(index,e)} />

                            <button type="button" className='btn btn-danger btn-sm' onClick={()=>eliminarBien(index)}>Eliminar</button>

                        </div>
                    ))
                }
                    */
                  
            }
            
        </form>
    </div>
  )
}
