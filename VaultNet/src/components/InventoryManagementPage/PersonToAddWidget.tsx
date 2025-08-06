type props = {
    readonly user: {
        name: string,
        email: string,
        profilePicture: string
    }
    readonly showDeleteButton?: boolean
}

export function PersonToAddWidget({user, showDeleteButton = false}:props){
    return(
        <div className="w-full bg-white rounded-lg flex items-center justify-between hover:bg-gray-200">
            <div className="flex items-center gap-2 p-2">
                <img src={user.profilePicture} alt="User profile" className="w-10 h-10 rounded-full"/>
                <div className="flex flex-col">
                    <p className="text-sm font-bold">{user.name}</p>
                    <p className="text-xs text-gray-500">{user.email}</p>
                </div>
                
            </div>
            {
                showDeleteButton && (
                    <button className="text-red-500 hover:text-red-700">Eliminar</button>
                )
            }
        </div>
    )
}