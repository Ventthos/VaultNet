export function PersonToAddWidget(){
    return(
        <div className="w-full h-12 bg-white rounded-lg flex items-center justify-between px-4">
            <div className="flex items-center gap-2">
                <img src="https://i.pinimg.com/564x/e8/ee/07/e8ee0728e1ba12edd484c111c1f492f2.jpg" alt="User profile" className="w-10 h-10 rounded-full border border-dashed border-2"/>
                <span className="text-sm font-normal">Nombre de la persona</span>
            </div>
            <button className="text-red-500 hover:text-red-700">Eliminar</button>
        </div>
    )
}