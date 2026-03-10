import { BrowserRouter, Route, Routes } from "react-router-dom";
import ListadoEmpleados from "./empleados/ListadoEmpleados";
import Navegacion from "./plantilla/Navegacion";
import AgregarEmpleado from "./empleados/AgregarEmpleado";
import EditarEmpleado from "./empleados/EditarEmpleado";
import Login from "./pages/Login";
import PrivateRoute from "./auth/PrivateRoute";
import { useEffect, useState } from "react";

function App() 
{

  const [usuario, setUsuario] = useState(null);
  //const usuario = localStorage.getItem("usuario");
  useEffect(() => {
    const user = localStorage.getItem("usuario");
    setUsuario(user);
  }, []);

  return (
    <div className="container">
      <BrowserRouter>
        
        {/* SOLO muestra menú si hay sesión */}
        {usuario && <Navegacion setUsuario={setUsuario}/>}
        
        <Routes>
          {/* LOGIN */}
          <Route exact path="/" element={<Login setUsuario={setUsuario} />}/>

          {/* CRUD */}
          <Route exact path="/listado" element={<PrivateRoute><ListadoEmpleados/></PrivateRoute>}/>
          <Route exact path="/agregar" element={<PrivateRoute><AgregarEmpleado/></PrivateRoute>}/>
          <Route exact path="/editar/:id" element={<PrivateRoute><EditarEmpleado/></PrivateRoute>}/>
        
        </Routes>

      </BrowserRouter>
    </div>

    /*<div className="container">
      <BrowserRouter>
        <Navegacion/>
        <Routes>
          <Route exact path="/" element={<ListadoEmpleados/>}/>
          <Route exact path="/agregar" element={<AgregarEmpleado/>}/>
          <Route exact path="/editar/:id" element={<EditarEmpleado/>}/>
        </Routes>
      </BrowserRouter>
    </div>*/
  );
}

export default App;
