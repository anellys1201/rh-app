import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Login({ setUsuario })
{
    
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const iniciarSesion = async(e)=>{
        e.preventDefault();

        try {

            console.log(username);
            console.log(password);

            // Peticion al Backend (Spring Boot)
            const response = await axios.post(
                "/auth/login",
                {
                    username,
                    password
                }
            );

            localStorage.setItem("usuario", JSON.stringify(response.data));

            setUsuario(response.data);

            navigate("/listado"); //redirecciona

        } catch(error)
        {
            alert("Credenciales incorrectas");
        }
    };

    return(
        <div className="container mt-5">
            <form onSubmit={iniciarSesion} className="col-md-4 offset-md-4">
                
                <h3 className="text-center mb-4">
                    Iniciar Sesión
                </h3>

                <div className="mb-3">
                    <label htmlFor="username" className="form-label">Username</label>
                    <input type="text" className="form-control" id="username" name="username" placeholder="Usuario" onChange={(e) => setUsername(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input type="password" className="form-control" id="password" name="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
                </div>
                <button type="submit" className="btn btn-primary">Login</button>
            </form>
        </div>
    );
}

export default Login;
