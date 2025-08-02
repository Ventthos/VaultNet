import { GiHamburgerMenu } from "react-icons/gi"
import { GoPencil } from "react-icons/go";
import { useWindowWidth } from "../../utils/useWindowWidth";

type props = {
    readonly openedMenu: boolean,
    readonly setOpenedMenu: (opened: boolean) => void
}

export function LateralMenu({openedMenu, setOpenedMenu}:props){
    return(
        <div className={`w-full min-h-[100dvh] h-[100dvh] z-60 bg-(--main-color) md:invisible absolute p-4 flex flex-col ${openedMenu ? 'translate-x-0' : '-translate-x-full'} transition-transform duration-300 ease-in-out`}>
            <button className="md:hidden text-white self-end" onClick={()=>setOpenedMenu(false)}>
                <GiHamburgerMenu className="text-2xl"/>
            </button>
            <div className="mb-4">
                <div className="flex items-center place-content-between mt-3 mb-2" >
                    <h1 className="text-3xl font-bold text-white mr-2 max-w-9/10">Garage cocina legado</h1>
                    <button><GoPencil className="text-white text-xl"/></button>
                </div>
                <div className="flex gap-2 max-w-full overflow-x-auto">
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                    <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="" className="w-8 h-8 rounded-full"/>
                </div>
            </div>

            <h2 className="text-white mb-1 text-lg">Otros inventarios</h2>

            <div className="flex-1 bg-white rounded-lg overflow-y-auto p-3 ">
                <div className="flex flex-wrap gap-2 justify-center items-start">
                    {[...Array(18)].map((_, i) => (
                        <img
                        key={i}
                        src="https://images.pexels.com/photos/459728/pexels-photo-459728.jpeg?cs=srgb&dl=pexels-pixabay-459728.jpg&fm=jpg"
                        alt=""
                        className="rounded-full h-24 w-24"
                        />
                    ))}
                </div>
                
            </div>
        </div>
    )
}