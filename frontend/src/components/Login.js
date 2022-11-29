import React, {useState} from "react";
import AppNavbar from "./AppNavbar";
import PropTypes from 'prop-types';
import {useNavigate} from "react-router-dom"

async function loginUser(credentials) {
    return fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}

export default function Login({ setToken }) {
    const [username, setUserName] = useState([]);
    const [password, setPassword] = useState([]);
    const [message, setMessage] = useState([]);

    const navigate = useNavigate();

    const handleSubmit = async e => {
        e.preventDefault();

        const response = await loginUser({
            username,
            password
        });
        console.log(response);
        if (response.status == "Login failed") {
            setMessage("Wrong username or password");
            navigate("/login", { replace: true });
        }
        else if (response.status == "Login Success") {
            console.log(response.token);
            const token = response.token;
            if (token != null) {
                setToken(response);

                console.log("Logged in!")
                navigate("/polls", { replace: true });
            }
        }
        else {
            console.log("Something wrong");
            navigate("/login", { replace: true });
        }
    }

    return(
            <div className="login-wrapper">
                <AppNavbar/>
                <h1>Please Log In</h1>
                <form onSubmit={handleSubmit}>
                    <div style={{color:'red'}}>
                        {message}
                    </div>
                    <label>
                        <p>Username</p>
                        <input type="text" onChange={e => setUserName(e.target.value)} />
                    </label>
                    <label>
                        <p>Password</p>
                        <input type="password" onChange={e => setPassword(e.target.value)} />
                    </label>
                    <div>
                        <button type="submit">Log in</button>
                    </div>
                </form>
            </div>
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};
