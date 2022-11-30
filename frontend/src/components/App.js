import Home from './Home';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import Polls from './Polls';
import Vote from './Vote';
import Register from './Register';
import Login from './Login';
import useToken from './useToken';
import PollCreate from "./PollCreate";
import PollEdit from "./PollEdit";
import PollResult from "./PollResult";

function MyRouter() {
    const {setToken } = useToken();

    return createBrowserRouter([
        {path: "/", element: <Home />,},
        {path: "/polls", element: <Polls />},
        {path: "/create_poll", element: <PollCreate />},
        {path: "/edit_poll/:id", element: <PollEdit />},
        {path: "/vote/:id", element: <Vote />},
        {path: "poll_result/:id", element: <PollResult />},
        {path: "/register", element: <Register />},
        {path: "/login", element: <Login setToken={setToken} />}
    ], );
}

function App() {
    return (
        <RouterProvider forceRefresh={true} router={MyRouter()}/>
    )
}

export default App;
