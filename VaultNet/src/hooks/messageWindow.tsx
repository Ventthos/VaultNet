import { useState } from "react";
import type { MessageWindowProps } from "../components/generalUse/MessageWindow";

export function useMessageWindow(){
    const [messageInfo, setMessageInfo] = useState<MessageWindowProps|null>(null)

    function setWaiting(options:{message: string}){
       setMessageInfo({
            title: "Cargando",
            message: options.message,
            type: "message",
            mode: "waiting"
        })
    }

    function setSuccess(options:{message: string, onConfirm?: ()=>void}){
        setMessageInfo({
            title: "Exito",
            message: options.message,
            type: "correct",
            mode: "accept",
            onConfirm: options.onConfirm
        })
    }

    function setError(options:{message: string, mode: "accept" | "retry", onConfirm?: ()=>void, onRetry?: ()=>void}){
        setMessageInfo({
            title: "Error",
            message: options.message,
            type: "error",
            mode: options.mode,
            onConfirm: options.onConfirm,
            onRetry: options.onRetry
        })
    }

    function setQuestion(options:{title: string, message: string, onConfirm?: ()=>void, onCancel?: ()=>void}){
        setMessageInfo({
            title: options.title,
            message: options.message,
            type: "question",
            mode: "yesNo",
            onConfirm: options.onConfirm,
            onCancel: options.onCancel
        })
    }

    function clearMessage(){
        setMessageInfo(null)
    }

    return {messageInfo, setWaiting, setSuccess, setError, setQuestion, clearMessage}
}