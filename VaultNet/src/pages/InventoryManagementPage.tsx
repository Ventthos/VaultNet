import { BusinessesDisplay } from "../components/InventoryManagementPage/BusinessesDisplay";
import { BusinessHeader } from "../components/InventoryManagementPage/BusinessHeader";
import { CategoryTable } from "../components/InventoryManagementPage/CategoryTable";
import { ProductDetail } from "../components/InventoryManagementPage/ProductDetail";
import { useDraggableContainer } from "../hooks/useDraggableContainer";

export function InventoryManagementPage(){
    const {containerRef} = useDraggableContainer();
    return(
        <div className="h-[100dvh] flex flex-col w-full relative overflow-hidden">
            <div className="w-full">
                <BusinessHeader businessName="Garage Cocina legado" userImageUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThBuS01sBVXcXM81DVC7ROvpFECrHjcDJjFw&s"/>
            </div>
            <BusinessesDisplay/>
            <ProductDetail/>
            <div ref={containerRef} className="aspect-square w-[5000px]  bg-radial from-pink-400 from-40% to-fuchsia-700 relative">
                <CategoryTable/>
            </div>
        </div>
    )
}