import React from "react";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import useToken from "./useToken";

export default function Vote() {
    let { id } = useParams();
    const navigate = useNavigate();
    const { token } = useToken();
}
