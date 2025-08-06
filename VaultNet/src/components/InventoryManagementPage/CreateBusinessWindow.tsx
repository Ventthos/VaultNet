import { InputWithLabel } from "../generalUse/InputWithLabel";
import { createBusinessService } from "../../services/business/CreateBusinessService";
import ImageUploadButton from "../generalUse/ImageUploadButton";
import type React from "react";
import { useContext } from "react";
import { MessageContext } from "../../contexts/messageContext";
import { SearchMemberWindow } from "./SearchMemberWidow";

export function CreateBusinessWindow(){
    const {setWaiting, setError, clearMessage,setSuccess} = useContext(MessageContext);

    async function createBusiness(event: React.FormEvent<HTMLFormElement>){
        event.preventDefault()
        setWaiting({message: "Creando inventario..."})
        const form = event.target
        const formData = new FormData(form as HTMLFormElement)
        const response = await createBusinessService(formData, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJyYTA0QGxpdmUuY29tLm14IiwidXNlcklkIjoxLCJpYXQiOjE3NTQzNjQ5NTUsImV4cCI6MTc1NDQ1MTM1NX0.ZHLJeNYfQPPPxykGfNoVqBve2ncBljcuMLFbDmC9FMw")
        if(response.error){
            setError({message: response.error.code, mode:"accept", onConfirm: ()=>clearMessage()})
            return
        }
        setSuccess({message: "Inventario creado con éxito", onConfirm: ()=>clearMessage()})
    }

    return(
        <div className="w-full h-[calc(100%-3.5rem)] md:h-[calc(100%-4.5rem)] bg-black/40 absolute flex place-content-center items-center z-50 mt-[3.5rem] md:mt-[4.5rem]">
            <SearchMemberWindow/>
            <form className="max-w-200 max-h-95/100 bg-white overflow-y-auto p-4 rounded-lg flex flex-col items-center gap-2.5 m-2" onSubmit={createBusiness}>
                <div className="relative">
                    <button className="font-bold w-full text-right rounded-full absolute text-xl" type="button">X</button>
                    <h1 className="text-2xl text-center w-9/10">Administrar un nuevo inventario</h1>
                </div>
                
                <ImageUploadButton name="image"/>
                <InputWithLabel text="Nombre" labelStyles="font-normal text-sm" inputStyles="text-md" inputName="name"/>

                <div className="w-full mt-2">
                    <div className="flex place-content-between mb-2">
                        <h2 className="text-lg ">Integrantes</h2>
                        <button className="rounded-button px-3 py-2 text-white bg-(--main-color) text-xs" type="button">Agregar persona</button>
                    </div>
                    
                    <div className="w-full bg-(--light-gray-2) h-50 max-h-50 rounded-xl overflow-y-auto mb-2">

                    </div>
                </div>

                <input className="rounded-button p-2 bg-(--main-color) text-white font-bold w-full text-center" value={"Guardar"} type="submit"/>
            </form>
        </div>
        
    )
}