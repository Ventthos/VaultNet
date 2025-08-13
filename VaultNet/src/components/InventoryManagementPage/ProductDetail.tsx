import { InputWithLabel } from "../generalUse/InputWithLabel";

export function ProductDetail(){
    

    return( 
        <div className="p-4 w-full flex flex-col items-center overflow-y-auto h-full">
            <h1 className="text-2xl mb-2">Producto</h1>
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
    )
}