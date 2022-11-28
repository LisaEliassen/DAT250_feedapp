import React, {useEffect, useState} from "react";
import {Button, Form, FormGroup, Input, Label} from "reactstrap";
import {useNavigate} from "react-router-dom";
import useToken from "./useToken";

async function connectDevice(credentials) {
    return fetch('http://localhost:8080/connectDevice', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}

function DeviceConnect() {
    const [pollID, setPollID] = useState();
    const [poll, setPoll] = useState([]);
    const { token, setToken } = useToken();
    const navigate = useNavigate();
    const [message, setMessage] = useState([]);

    useEffect(() => {
        if (pollID != null && pollID != undefined && !isNaN(pollID)) {
            fetch('http://localhost:8080/polls/'+pollID
            ).then((res) => res.json())
                .then((poll) => {
                    setMessage("");
                    setPoll(poll);
                }).catch(error => {
                setMessage("Poll with given ID does not exist!");
                console.log(error.response)
            });
        }
    }, [poll, pollID]);

    const handleInputChange = (event) => {
        setPollID(event.target.value);
    }

    const createDevice = async (id) => {
        console.log("Creating device...")
        const json = {
        }
        if (pollID != null && pollID != undefined && !isNaN(pollID)) {
            return fetch('http://localhost:8080/devices/'+id, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(json)}
            ).then((res) => res.json());
        }
        else {
            setMessage("Poll with given ID does not exist!");
            return false;
        }

    }

    const handleSubmit = async e => {
        e.preventDefault();
        if (isNaN(pollID)) {
            console.log("Not a number")
        }
        const device = await createDevice(pollID);
        console.log(device);
        if (device == false) {
            navigate("/");
        }
        else if (device != null && device !== undefined) {
            let deviceID = device.deviceID;
            console.log(device);
            const response = await connectDevice({
                deviceID,
                pollID
            });
            console.log(response);

            if (response.status == "Connection failed") {
                console.log("Connection failed!")
                navigate("/", { replace: true });
            }
            else if (response.status == "Connection Success") {
                console.log(response.token);
                const token = response.token;
                if (token != null) {
                    setToken(response);

                    console.log("Connected!")
                    navigate('/vote/'+pollID, { replace: true });
                }
            }
            else {
                console.log("Something wrong");
                navigate("/", { replace: true });
            }
        }
        else {
            console.log("Cannot create device");
            navigate("/", { replace: true });
        }
    }

    return (
        <div>
            <h3>Connect to poll</h3>
            <Form onSubmit={handleSubmit}>
                <div style={{color:'red'}}>
                    {message}
                </div>
                <FormGroup>
                    <Label for="pollID">Please give poll ID:</Label>
                    <Input type="text" name="pollID" id="pollID"
                           onChange={handleInputChange} autoComplete="pollID"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Confirm</Button>
                </FormGroup>
            </Form>
        </div>
    );
}

export default DeviceConnect;
