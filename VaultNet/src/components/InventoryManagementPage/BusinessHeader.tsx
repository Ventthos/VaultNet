import { GiHamburgerMenu } from "react-icons/gi";

type props = {
    readonly businessName: string,
    readonly userImageUrl: string,
    readonly setOpenedMenu: (opened: boolean) => void,
    readonly activeUsers?:{
        name: string,
        userImageUrl: string
    } 
}

export function BusinessHeader({businessName, userImageUrl, activeUsers, setOpenedMenu}: props){
    return(
        <div className="bg-(--main-color) w-full p-2 md:p-4 flex items-center fixed shadow-[0_4px_4px_rgba(0,0,0,0.25)] z-50">
            <div className="flex-1 flex">
                <button className="md:hidden mr-4 text-white" onClick={() => setOpenedMenu(true)}>
                    <GiHamburgerMenu className="text-2xl"/>
                </button>
                <h1 className="text-white text-xl md:text-3xl truncate whitespace-nowrap overflow-hidden max-w-6/10">{businessName}</h1>
            </div>
            <div>
                <img src={userImageUrl} alt="User profile" className="rounded-full w-10 h-10 min-w-10"/>
            </div>
            
        </div>
    )
}