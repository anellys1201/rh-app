import React from 'react'
import { Link } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'

function Navegacion({ setUsuario }) {
    
    const navigate = useNavigate();

    const cerrarSesion = () => {
        localStorage.removeItem("usuario");
        setUsuario(null);
        navigate("/")
    }

  return (
    <div className="container-fluid">
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <div className="container-fluid">
                <a className="navbar-brand">Sistema de Recursos Humanos</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div className="navbar-nav">
                        <a className="nav-link active" aria-current="page" href="/listado">Inicio</a>
                        <Link className="nav-link" to="/agregar">Agregar Empleado</Link>
                        <button onClick={cerrarSesion} className='btn btn-outline-danger ms-3'>Cerrar sesión</button>
                    </div>
                </div>
            </div>
        </nav>
    </div>
  )
}

export default Navegacion;
