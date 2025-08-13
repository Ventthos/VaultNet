import { useContext } from "react";
import type { Category } from "../../models/category/Local/Category";
import { createCategory } from "../../services/categories/CreateCategory";
import { InputWithLabel } from "../generalUse/InputWithLabel";
import { AuthContext } from "../../contexts/AuthContext";
import { MessageContext } from "../../contexts/messageContext";

export function CategoryDetail({category, businessId}: {readonly category:Category | null, readonly businessId: number}){
    const {token} = useContext(AuthContext)
    const {setWaiting, setError, setSuccess, clearMessage} = useContext(MessageContext)

    async function uploadCategory(name:string, color:string){
        setWaiting({message:"Cargando categorías"})
        const newCategory = {
            id:-1,
            name: name,
            color: color,
            businessId: businessId
        } as Category

        const response = await createCategory(newCategory, businessId, token)
        if(response.error){
            setError({message:response.error.message, mode:"accept", onConfirm: clearMessage})
            return
        }
        setSuccess({message:"Categoría creada", onConfirm: clearMessage})

    }

    function handleSaveClick(event:React.FormEvent<HTMLFormElement>){
        event.preventDefault()
        const form = event.currentTarget
        const formData = new FormData(form);
        const name = formData.get("name") as string;
        const color = formData.get("color") as string
        if(category == null){
            uploadCategory(name, color)
        }
    }

    return (
        <form className="p-4 w-full flex flex-col items-center overflow-y-auto h-full" onSubmit={handleSaveClick}>
            <h1 className="text-2xl mb-2">Categoría</h1>
            <div className="flex flex-col gap-2 w-full mb-7">
                <InputWithLabel text="Nombre" inputStyles="input-product-display" labelStyles="input-product-display-label"
                inputName="name"/>
                <InputWithLabel text="Color" inputStyles="input-product-display h-22" labelStyles="input-product-display-label"
                 inputType="color" inputName="color"/>
            </div>

            <input className="rounded-2xl w-full p-2 bg-white mb-2 border-solid border-2 border-black" type="submit" value={"Guardar cambios"}/>
            <button className="rounded-button bg-[#E30000] text-white w-full p-2 mb-10 md:mb-0" type="button">Eliminar</button>   
        </form>
    )
}