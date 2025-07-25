import { InputWithLabel } from "../components/generalUse/InputWithLabel";
import { useState, useEffect } from "react";
import { useWindowWidth } from "../utils/useWindowWidth";
import { logIn as loginService } from "../services/users/Login";
import { MessageWindow } from "../components/generalUse/MessageWindow";
import { useContext } from "react";
import { MessageContext } from "../contexts/messageContext";

export function Login(){
    const [isLogin, setIsLogin] = useState(true);
    const [visibleImage, setVisibleImage] = useState("/img/Login.jpg");
    const [isFading, setIsFading] = useState(false);
    const [firstLoading, setFirstLoading] = useState(true);
    const windowWidth = useWindowWidth(); 
    const {setWaiting, setError, clearMessage, messageInfo} = useContext(MessageContext);

  useEffect(() => {
    setIsFading(true); // empieza la animación de oscurecimiento
    const timeout = setTimeout(() => {
      // Cambiar la imagen cuando ya está negro
      setVisibleImage(isLogin ? "/img/Inventario.jpg" : "/img/Inventario 2.jpg");
      setIsFading(false); // y volver a mostrar
    }, 400); // 400ms: mitad de una animación de 800ms

    return () => clearTimeout(timeout);
  }, [isLogin]);

    async function logIn(event: React.FormEvent<HTMLFormElement>){
        event.preventDefault();
        const form = event.currentTarget;
        const email = (form.elements.namedItem("email") as HTMLInputElement).value;
        const password = (form.elements.namedItem("password") as HTMLInputElement).value;

        setWaiting({message: "Iniciando sesión..."})
        const response = await loginService({email, password})
        if(response.error){
            setError({message: response.error.code, mode:"accept", onConfirm: ()=>clearMessage()})
            console.error(response.error.message);
            
            return
        }
        clearMessage();
        console.log("si se pudo")
        
    }  

    return(
        <div className="h-[100dvh] lg:flex w-full relative overflow-x-hidden">
            {
                messageInfo && <MessageWindow title={messageInfo.title} message={messageInfo.message} type={messageInfo.type} mode={messageInfo.mode} onConfirm={messageInfo.onConfirm}
                onCancel={messageInfo.onCancel} onRetry={messageInfo.onRetry}/> 
            }
            {/* La imagen*/}
             <div
                className={`relative lg:absolute w-full h-3/10 lg:w-1/2 lg:h-full overflow-hidden transition-all duration-700 ease-in-out rounded-b-[3rem] mb-4 lg:mb-0
                     ${isLogin ? 'lg:translate-x-0 lg:rounded-r-[3rem] lg:rounded-bl-none' : 'lg:translate-x-full lg:rounded-l-[3rem] lg:rounded-br-none'}
                `}
                >
                {/* Imagen */}
                <img
                    src={visibleImage}
                    alt="Background"
                    className="w-full h-full object-cover"
                />

                {/* Overlay negro animado */}
                <div
                    className={`absolute inset-0 bg-black transition-opacity duration-700 pointer-events-none
                    ${isFading ? 'opacity-100' : 'opacity-0'}
                    `}
                />
            </div>
            {/* Contenedor de registro*/}
            <div className={`lg:w-full flex flex-col items-center justify-center ${firstLoading ? "invisible" : ""} ${(windowWidth < 1024 && isLogin) ? "hidden" : ""}`}>
                <div className="max-w-150 w-full p-4">
                    <h1 className="text-(--main-color) text-5xl">Crear cuenta</h1>
                    <p className="text-base">¿Ya tienes cuenta? <button onClick={()=>{
                        setIsLogin(true)
                        if(firstLoading){
                            setFirstLoading(false);
                        }
                        }} className="underline text-(--main-color)">Inicia sesión</button></p>
                    <form className="flex flex-col gap-4 pt-5">
                        <InputWithLabel text="Nombre" labelStyles="label-input-login"
                        inputStyles="input-login"/>
                        <div className="flex gap-4 flex-col lg:flex-row">
                            <InputWithLabel text="Apellido paterno" labelStyles="label-input-login"
                            inputStyles="input-login"/>
                            <InputWithLabel text="Apellido materno" labelStyles="label-input-login"
                            inputStyles="input-login"/>
                        </div>
                        <InputWithLabel text="Correo" labelStyles="label-input-login"
                            inputStyles="input-login" inputType="email"/>
                        <InputWithLabel text="Contraseña" labelStyles="label-input-login"
                            inputStyles="input-login" inputType="password"/>
                        <InputWithLabel text="Confirmar contraseña" labelStyles="label-input-login"
                            inputStyles="input-login" inputType="password"/>
                        <input type="submit" className="rounded-button blue-rounded-button mt-5" value={"Crear cuenta"}/>
                    </form>
                    
                </div>
            </div>
            
            {/* Contenedor de login*/}
            <div className={`w-full flex flex-col items-center justify-center ${(windowWidth < 1024 && !isLogin) ? "hidden" : ""}`}>
                <div className="max-w-150 w-full p-4">
                    <h1 className="text-(--main-color) text-5xl">Iniciar sesión</h1>
                    <p className="text-base">¿No tienes cuenta? <button onClick={()=>{
                        setIsLogin(false)
                        if(firstLoading){
                            setFirstLoading(false);
                        }
                        }} className="underline text-(--main-color)">Crea una aquí</button></p>
                    <form className="flex flex-col gap-4 pt-5" onSubmit={(event)=>logIn(event)}>
                        <InputWithLabel text="Correo" labelStyles="label-input-login"
                        inputStyles="input-login" inputType="email" inputName="email" inputId="email"/>
                        <InputWithLabel text="Contraseña" labelStyles="label-input-login"
                        inputStyles="input-login" inputType="password" inputName="password" inputId="password"/>
                        <input type="submit" className="rounded-button blue-rounded-button mt-5" value={"Iniciar sesión"}/>
                    </form>
                </div>
            </div>
        </div>
    )
}