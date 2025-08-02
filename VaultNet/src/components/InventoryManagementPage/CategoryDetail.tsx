import { InputWithLabel } from "../generalUse/InputWithLabel";

export function CategoryDetail(){
    return (
        <div className="p-4 w-full flex flex-col items-center overflow-y-auto h-full">
            <h1 className="text-2xl mb-2">Categoría</h1>
            <div className="flex flex-col gap-2 w-full mb-7">
                <InputWithLabel text="Nombre" inputStyles="input-product-display" labelStyles="input-product-display-label"/>
                <InputWithLabel text="Color" inputStyles="input-product-display h-22" labelStyles="input-product-display-label" inputType="color" />
            </div>

            <button className="rounded-2xl w-full p-2 bg-white mb-2 border-solid border-2 border-black">Guardar cambios</button>
            <button className="rounded-button bg-[#E30000] text-white w-full p-2 mb-10 md:mb-0">Eliminar</button>   
        </div>
    )
}