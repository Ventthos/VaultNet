import { useState } from "react";
import { useWindowWidth } from "../../utils/useWindowWidth";
import { InputWithLabel } from "../generalUse/InputWithLabel";
import { MdDragHandle } from "react-icons/md";



export function ProductDetail(){
    const width = useWindowWidth()
    const [opened, setOpened] = useState<boolean>(false)

    function toggleDetail(){
        if(width < 768){
            setOpened(prev=>{
                return !prev
            })
        }
        
    }

    return(
        <div className={`fixed left-0 md:left-auto ${opened ? "h-6/10" : "h-10"} md:h-auto md:top-18 bottom-0 right-0 md:w-75 bg-(--light-gray-3) shadow-[-1px_0px_4px_rgba(0,0,0,0.25)] rounded-t-lg md:rounded-t-none z-48`}>
            <button className="bg-[#b1b1b1] mb-2 md:hidden w-full rounded-t-lg" onClick={toggleDetail}>
                <MdDragHandle className="text-3xl text-white w-full"/>
            </button>
            <div className="p-4 w-full flex flex-col items-center overflow-y-auto h-full">
                <img src="https://avigrupo.com.mx/images/pollo-vivo/pollo-vivo-calidad-rendimiento.png" alt="Producto actual" className="rounded-3xl shadow-md w-50 h-50 min-h-50 mb-4" />
                <div className="flex flex-col gap-2 w-full mb-7">
                    <InputWithLabel text="Nombre" inputStyles="input-product-display" labelStyles="input-product-display-label"/>
                    <InputWithLabel text="Unidad" inputStyles="input-product-display" labelStyles="input-product-display-label" inputType="select"/>
                    <InputWithLabel text="Categoría" inputStyles="input-product-display" labelStyles="input-product-display-label" inputType="select"/>
                    <InputWithLabel text="Cantidad actual" inputStyles="input-product-display" labelStyles="input-product-display-label"/>
                    <InputWithLabel text="Cantidad minima" inputStyles="input-product-display" labelStyles="input-product-display-label"/>
                    <InputWithLabel text="Cantidad para aviso" inputStyles="input-product-display" labelStyles="input-product-display-label"/>
                </div>
                <button className="rounded-2xl w-full p-2 bg-white mb-2 border-solid border-2 border-black">Guardar cambios</button>
                <button className="rounded-button bg-[#E30000] text-white w-full p-2 mb-10 md:mb-0">Eliminar</button>
            </div>
            
        </div>
    )
}