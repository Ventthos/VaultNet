import { twMerge } from "tailwind-merge"
// Mode: modo advertencia, error, pregunta, correcto
// type: ok; yes, no; yes, no, cancel. 
export type MessageWindowProps = {
    readonly title: string,
    readonly message: string,
    readonly type: "correct" | "error" | "warning" | "question" | "message",
    readonly mode: "waiting" | "accept" | "yesNo" | "retry"
    readonly onConfirm?: () => void,
    readonly onCancel?: () => void,
    readonly onRetry?: () => void
}

function getTypeColor(type: string){
    switch(type){
        case "warning":
            return "bg-[#ff8a01]"
        case "error":
            return "bg-[#d35a5a]"
        case "question":
            return "bg-[#0877e8]"
        case "correct":
            return "bg-[#2e8b56]"
        case "message":
            return "bg-[#0352a2]"
    }
}

function getImage(type: string){
    const generalRoute = "/img/"
    switch(type){
        case "warning":
            return generalRoute+"advertencia.png"
        case "error":
            return generalRoute+"error.png"
        case "question":
            return generalRoute+"pregunta.png"
        case "correct":
            return generalRoute+"aceptar.png"
        case "message":
            return generalRoute+"message.png"
    }
}

export function MessageWindow({title, message, mode, type, onConfirm, onCancel, onRetry}: MessageWindowProps){
    
    return(
        <div className="w-full h-[100dvh] bg-black/40 absolute flex place-content-center items-center z-50">
            <div className={twMerge("w-8/10 lg:w-1/2 p-1", `${getTypeColor(type)} rounded-xl`)}>
                <h1 className="text-white text-lg font-bold py-1 px-2">{title}</h1>
                <div className="bg-white rounded-b-xl">
                    <div className="p-2 flex gap-2">
                        <img src={getImage(type)} alt="" className="w-8 h-8"/>
                        <p className="text-md/4">{message}</p>
                    </div>
                    <div className="flex place-content-center items-center gap-3 bg-[#e0e0e0] p-2 rounded-b-lg">
                        {mode == "yesNo" && 
                            <button 
                            className="bg-[#d35a5a] text-white py-1 px-3.5 rounded-md hover:bg-[#d87d7d] active:bg-[#e7aeae] transition-bg duration-300"
                            onClick={onCancel}>Cancelar</button>
                        }
                        {
                            (mode == "yesNo" || mode == "accept") &&
                            <button className="bg-[#2e8b56] text-white py-1 px-3.5 rounded-md hover:bg-[#59a278] active:bg-[#97c5ab] transition-bg duration-300"
                            onClick={onConfirm}>Aceptar</button> 
                        }  
                        {
                            (mode == "retry") &&
                            <button className="bg-[#0877e8] text-white py-1 px-3.5 rounded-md hover:bg-[#52a0ef] active:bg-[#84bbf4] transition-bg duration-300"
                            onClick={onRetry}>Reintentar</button> 
                        }       
                    </div>
                </div>
            </div>
        </div>
        
    )
}