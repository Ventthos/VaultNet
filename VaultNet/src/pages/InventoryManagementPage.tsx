import { useState } from "react";
import { BusinessesDisplay } from "../components/InventoryManagementPage/BusinessesDisplay";
import { BusinessHeader } from "../components/InventoryManagementPage/BusinessHeader";
import { CategoryTable } from "../components/InventoryManagementPage/CategoryTable";
import { MultiInfoContainer, multiInfoContainerModes } from "../components/InventoryManagementPage/MultiInfoContainer";
import type { MultiInfoContainerMode } from "../components/InventoryManagementPage/MultiInfoContainer";
import { useDraggableContainer } from "../hooks/useDraggableContainer";
import { CreateBusinessWindow } from "../components/InventoryManagementPage/CreateBusinessWindow";
import { LateralMenu } from "../components/InventoryManagementPage/LateralMenu";

export function InventoryManagementPage(){
    const {containerRef} = useDraggableContainer();
    const [containerMode, setContainerMode] = useState<MultiInfoContainerMode>(multiInfoContainerModes.INVISIBLE)
    const [openedLateralMenu, setOpenedLateralMenu] = useState<boolean>(false)
    function onTopTableTouched(){
        setContainerMode(multiInfoContainerModes.CATEGORY_DETAIL)
    }

    function onProductTouched(){
        setContainerMode(multiInfoContainerModes.PRODUCT_DETAIL)
    }

    function dismissDetails(){
        setContainerMode(multiInfoContainerModes.INVISIBLE)
    }

    return(
        <div className="h-[100dvh] flex flex-col w-full relative overflow-hidden">
            <CreateBusinessWindow/>
            <div className="w-full">
                <BusinessHeader businessName="Garage Cocina legado" userImageUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThBuS01sBVXcXM81DVC7ROvpFECrHjcDJjFw&s"
                setOpenedMenu={setOpenedLateralMenu}/>
            </div>
            <LateralMenu openedMenu={openedLateralMenu} setOpenedMenu={setOpenedLateralMenu}/>
            <CreateBusinessWindow/>
            <BusinessesDisplay/>
            <MultiInfoContainer mode={containerMode} object={null}/>
            <div ref={containerRef} className="aspect-square w-[5000px]  bg-radial from-pink-400 from-40% to-fuchsia-700 relative" onClick={dismissDetails}>
                
                <CategoryTable onHeaderTouched={onTopTableTouched} onProductTouched={onProductTouched}/>
            </div>
        </div>
    )
}