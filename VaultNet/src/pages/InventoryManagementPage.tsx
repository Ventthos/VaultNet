import { useContext, useEffect, useState } from "react";
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
import { getBusinessesFromUser } from "../services/business/GetBusinessesServices";
import type { Business } from "../models/business/local/Business";
import { verifyToken as verifyTokenService} from "../services/users/TokenVerify";
import { AuthContext } from "../contexts/AuthContext";

export function InventoryManagementPage(){
    const {containerRef} = useDraggableContainer();
    const [containerMode, setContainerMode] = useState<MultiInfoContainerMode>(multiInfoContainerModes.INVISIBLE)
    const [openedLateralMenu, setOpenedLateralMenu] = useState<boolean>(false)
    const {messageInfo, setError, setWaiting, clearMessage} = useContext(MessageContext)
    const [creatingBusiness, setCreatingBusiness] = useState<boolean>(false);
    const [businesses, setBusinesses] = useState<Business[]>([]);
    const {setToken} = useContext(AuthContext)


    async function getBusinesses(token: string){
        setWaiting({message:"Cargando negocios"})
       const response = await getBusinessesFromUser(token)
        if(response.error){
            setError({message:response.error.message, mode:"accept"})
            return
        }
        setBusinesses(response.success.data)
        clearMessage()
    }

    function onTopTableTouched(){
        setContainerMode(multiInfoContainerModes.CATEGORY_DETAIL)
    }

    function onProductTouched(){
        setContainerMode(multiInfoContainerModes.PRODUCT_DETAIL)
    }

    function dismissDetails(){
        setContainerMode(multiInfoContainerModes.INVISIBLE)
    }

    function onBusinessClick(business:Business){
        console.log(business)
    }

    useEffect(()=>{
        const getInfo = async ()=>{
            const vaultnetToken = localStorage.getItem("vaultnet-token")
            if(!vaultnetToken || ! (await verifyTokenService(vaultnetToken!)).success?.data){
                window.location.href = "/login"
                return
            }
            setToken(vaultnetToken)

            await getBusinesses(vaultnetToken)
        }
        getInfo()
        
    },[])

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
            
            <BusinessesDisplay businesses={businesses} onBusinessClick={onBusinessClick}/>
            <MultiInfoContainer mode={containerMode} object={null}/>
            <div ref={containerRef} className="aspect-square w-[5000px] relative bg-[url(/img/grid.png)]" onClick={dismissDetails}>
                
                <CategoryTable onHeaderTouched={onTopTableTouched} onProductTouched={onProductTouched}/>
            </div>
        </div>
    )
}