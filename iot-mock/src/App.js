import {createBrowserRouter, RouterProvider,} from 'react-router-dom';
import DeviceVote from "./DeviceVote";
import DeviceConnect from "./DeviceConnect";
import PollResult from "./PollResult";

function MyRouter() {
    return createBrowserRouter([
        {
            path: "/",
            element: <DeviceConnect />,
        },
        {
            path: "/vote/:id",
            element: <DeviceVote />,
        },
        {
            path: "poll_result/:id",
            element: <PollResult />
        }
    ], );
}

function App() {
    return (
        <RouterProvider forceRefresh={true} router={MyRouter()}/>
    )
}

export default App;
