import { useContext, useState } from "react";
import { BusinessesDisplay } from "../components/InventoryManagementPage/BusinessesDisplay";
import { BusinessHeader } from "../components/InventoryManagementPage/BusinessHeader";
import { CategoryTable } from "../components/InventoryManagementPage/CategoryTable";
import { MultiInfoContainer, multiInfoContainerModes } from "../components/InventoryManagementPage/MultiInfoContainer";
import type { MultiInfoContainerMode } from "../components/InventoryManagementPage/MultiInfoContainer";
import { useDraggableContainer } from "../hooks/useDraggableContainer";
import { CreateBusinessWindow } from "../components/InventoryManagementPage/CreateBusinessWindow";
import { LateralMenu } from "../components/InventoryManagementPage/LateralMenu";
import { MessageContext } from "../contexts/messageContext";
import { MessageWindow } from "../components/generalUse/MessageWindow";

export function InventoryManagementPage(){
    const {containerRef} = useDraggableContainer();
    const [containerMode, setContainerMode] = useState<MultiInfoContainerMode>(multiInfoContainerModes.INVISIBLE)
    const [openedLateralMenu, setOpenedLateralMenu] = useState<boolean>(false)
    const {messageInfo} = useContext(MessageContext)
    const [creatingBusiness, setCreatingBusiness] = useState<boolean>(false);

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
            {
                messageInfo && <MessageWindow title={messageInfo.title} message={messageInfo.message} type={messageInfo.type} mode={messageInfo.mode} onConfirm={messageInfo.onConfirm}
                onCancel={messageInfo.onCancel} onRetry={messageInfo.onRetry}/> 
            }
            {
                creatingBusiness && 
                <CreateBusinessWindow showUsersSection={false} closeFunction={()=>setCreatingBusiness(false)}/>
            }
            <div className="w-full">
                <BusinessHeader businessName="Garage Cocina legado" userImageUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThBuS01sBVXcXM81DVC7ROvpFECrHjcDJjFw&s"
                setOpenedMenu={setOpenedLateralMenu}/>
            </div>
            <LateralMenu openedMenu={openedLateralMenu} setOpenedMenu={setOpenedLateralMenu}/>
            
            <BusinessesDisplay/>
            <MultiInfoContainer mode={containerMode} object={null}/>
            <div ref={containerRef} className="aspect-square w-[5000px] relative bg-[url(/img/grid.png)]" onClick={dismissDetails}>
                
                <CategoryTable onHeaderTouched={onTopTableTouched} onProductTouched={onProductTouched}/>
            </div>
        </div>
    )
}