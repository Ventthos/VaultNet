import {Routes, Route } from "react-router-dom";
import { Login } from "../pages/Login";
import { InventoryManagementPage } from "../pages/InventoryManagementPage";

export default function VaultNetRouter(){
    return(
        <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/" element={<InventoryManagementPage/>}/>
        </Routes>
    )  
}