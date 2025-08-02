import { InputWithLabel } from "../generalUse/InputWithLabel";

export function CreateBusinessWindow(){
    return(
        <div className="w-full h-[calc(100%-3.5rem)] md:h-[calc(100%-4.5rem)] bg-black/40 absolute flex place-content-center items-center z-50 mt-[3.5rem] md:mt-[4.5rem]">
            <div className="max-w-200 max-h-95/100 bg-white overflow-y-auto p-4 rounded-lg flex flex-col items-center gap-2.5 m-2">
                <div className="relative">
                    <button className="font-bold w-full text-right rounded-full absolute text-xl">X</button>
                    <h1 className="text-2xl text-center w-9/10">Administrar un nuevo inventario</h1>
                </div>
                
                <img src="https://i.pinimg.com/564x/e8/ee/07/e8ee0728e1ba12edd484c111c1f492f2.jpg" alt="" className="w-25 h-25 rounded-full border border-dashed border-2"/>
                <InputWithLabel text="Nombre" labelStyles="font-normal text-sm" inputStyles="text-md"/>

                <div className="w-full mt-2">
                    <div className="flex place-content-between mb-2">
                        <h2 className="text-lg ">Integrantes</h2>
                        <button className="rounded-button px-3 py-2 text-white bg-(--main-color) text-xs">Agregar persona</button>
                    </div>
                    
                    <div className="w-full bg-(--light-gray-2) h-50 max-h-50 rounded-xl overflow-y-auto mb-2">

                    </div>
                </div>

                <button className="rounded-button p-2 bg-(--main-color) text-white font-bold w-full">Guardar </button>
            </div>
        </div>
        
    )
}