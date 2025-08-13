import { useState } from "react";
import { IoAddOutline } from "react-icons/io5";
import { twMerge } from "tailwind-merge";

export function CreationMenu({containerStyles, onCreateCategory}: {readonly containerStyles?: string, readonly onCreateCategory?: ()=>void}){
    const [sectionsActive, setSectionsActive] = useState<boolean>(false)
    return (
        <div className={twMerge("z-200 fixed top-20 right-0 flex flex-col items-end m-3", containerStyles)}>
            <button className="bg-(--main-color) p-3 rounded-full" onClick={()=> setSectionsActive(prev=>{
                return !prev;
            })}>
                <IoAddOutline className="text-2xl font-bold text-white"/>
            </button>
            <div className={`flex flex-col mt-2 ${sectionsActive ? "visible" : "invisible"}`}>
                <button className="bg-white border-2 border-black px-2 py-1 rounded-full hover:bg-(--light-gray-2) transition duration-300" onClick={onCreateCategory}>Crear categoría</button>
                <button className="bg-white border-2 border-black px-2 py-1 rounded-full mt-1 hover:bg-(--light-gray-2) transition duration-300">Crear producto</button>
            </div>
        </div>
    )
}