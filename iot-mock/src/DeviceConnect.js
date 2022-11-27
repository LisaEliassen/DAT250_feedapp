import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link, useNavigate} from "react-router-dom";
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
    const [device, setDevice] = useState([]);
    const [pollID, setPollID] = useState();
    const [poll, setPoll] = useState([]);
    const { token, setToken } = useToken();
    const navigate = useNavigate();

    useEffect(() => {
        if (pollID != null && pollID != undefined && !isNaN(pollID)) {
            fetch('http://localhost:8080/polls/'+pollID
            ).then((res) => res.json())
                .then((poll) => {
                    setPoll(poll);
                });
        }

    }, [poll, pollID, device]);

    const handleInputChange = (event) => {
        setPollID(event.target.value);
    }

    const createDevice = async (id) => {
        console.log("Creating device...")
        const json = {
        }
        return fetch('http://localhost:8080/devices/'+id, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json)}
        ).then((res) => res.json());
    }

    const handleSubmit = async e => {
        e.preventDefault();
        const device = await createDevice(pollID);
        console.log(device);
        if (device != null && device !== undefined) {
            let deviceID = device.deviceID;
            console.log(device);
            const response = await connectDevice({
                deviceID,
                pollID
            });
            console.log(response);

            if (response.status == "Connection failed") {
                //setMessage("Device does not exist?");
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
                //navigate("/", { replace: true });
            }
        }
        else {
            console.log("Cannot create device");
            //navigate("/", { replace: true });
        }
    }

    return (
        <div>
            <h3>Connect to poll</h3>
            <Form onSubmit={handleSubmit}>
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
