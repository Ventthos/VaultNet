import { useState } from "react";
import { useWindowWidth } from "../../utils/useWindowWidth";
import { MdDragHandle } from "react-icons/md";
import { ProductDetail } from "./ProductDetail";
import { CategoryDetail } from "./CategoryDetail";

export const multiInfoContainerModes = {
  PRODUCT_DETAIL: "productDetail",
  CATEGORY_DETAIL: "categoryDetail",
  INVISIBLE: "invisible"
} as const;

export type MultiInfoContainerMode = typeof multiInfoContainerModes[keyof typeof multiInfoContainerModes];

type Props = {
    readonly mode: MultiInfoContainerMode,
    readonly object: any
}

export function MultiInfoContainer({mode, object}:Props){
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
        <>
            {
                mode === multiInfoContainerModes.INVISIBLE ? 
                <></>
                :
                <div className={`fixed left-0 md:left-auto ${opened ? "h-6/10" : "h-10"} md:h-auto md:top-18 bottom-0 right-0 md:w-75 bg-(--light-gray-3) shadow-[-1px_0px_4px_rgba(0,0,0,0.25)] rounded-t-lg md:rounded-t-none z-48`}>
                    <button className="bg-[#b1b1b1] mb-2 md:hidden w-full rounded-t-lg" onClick={toggleDetail}>
                        <MdDragHandle className="text-3xl text-white w-full"/>
                    </button>
                    {
                        mode === multiInfoContainerModes.PRODUCT_DETAIL ?
                        <ProductDetail/>
                        :
                        <CategoryDetail/>
                    }
                
                </div>
            }
        </>
    )
}