import { createContext } from "react";
import type { MessageWindowProps } from "../components/generalUse/MessageWindow";
import { useMessageWindow } from "../hooks/messageWindow";

interface MessageContextType{
    messageInfo: MessageWindowProps|null,
    setWaiting: (options:{message: string})=>void,
    setSuccess: (options:{message: string, onConfirm?: ()=>void})=>void,
    setError: (options:{message: string, mode: "accept", onConfirm?: ()=>void})=>void,
    setQuestion: (options:{title: string, message: string, onConfirm?: ()=>void, onCancel?: ()=>void})=>void,
    clearMessage:()=>void
}

export const MessageContext = createContext<MessageContextType>({
    messageInfo: null,
    setWaiting: ()=>{},
    setSuccess: ()=>{},
    setError: ()=>{},
    setQuestion: ()=>{},
    clearMessage: ()=>{}
})

export function MessageContextProvider({children} : {readonly children: React.ReactNode} ){
    const {messageInfo, setWaiting, setSuccess, setError, setQuestion, clearMessage} = useMessageWindow();
    return (
        <MessageContext.Provider value={{ messageInfo, setWaiting, setSuccess, setError, setQuestion, clearMessage}}>
            {children}
        </MessageContext.Provider>
    )
    
}
