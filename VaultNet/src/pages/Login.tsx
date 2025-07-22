import { InputWithLabel } from "../components/generalUse/InputWithLabel";
import { useState, useEffect } from "react";

export function Login(){
    const [isLogin, setIsLogin] = useState(true);
    const [visibleImage, setVisibleImage] = useState("/img/Login.jpg");
    const [isFading, setIsFading] = useState(false);

  useEffect(() => {
    setIsFading(true); // empieza la animación de oscurecimiento

    const timeout = setTimeout(() => {
      // Cambiar la imagen cuando ya está negro
      setVisibleImage(isLogin ? "/img/Inventario.jpg" : "/img/Inventario 2.jpg");
      setIsFading(false); // y volver a mostrar
    }, 400); // 400ms: mitad de una animación de 800ms

    return () => clearTimeout(timeout);
  }, [isLogin]);

    return(
        <div className="h-[100dvh] flex w-full relative">
            {/* Contenedor de registro*/}
            <div className="w-full flex flex-col items-center justify-center ">
                <div className="max-w-150 w-full p-4">
                    <h1 className="text-(--main-color) text-5xl">Crear cuenta</h1>
                    <p className="text-base">¿Ya tienes cuenta? <button onClick={()=>setIsLogin(true)} className="underline text-(--main-color)">Inicia sesión</button></p>
                    <div className="flex flex-col gap-4 pt-5">
                        <InputWithLabel text="Nombre" labelStyles="font-(family-name:--title-font) text-base"
                        inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)"/>
                        <div className="flex gap-4">
                            <InputWithLabel text="Apellido paterno" labelStyles="font-(family-name:--title-font) text-base"
                            inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)"/>
                            <InputWithLabel text="Apellido materno" labelStyles="font-(family-name:--title-font) text-base"
                            inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)"/>
                        </div>
                        <InputWithLabel text="Correo" labelStyles="font-(family-name:--title-font) text-base"
                            inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)" inputType="email"/>
                        <InputWithLabel text="Contraseña" labelStyles="font-(family-name:--title-font) text-base"
                            inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)" inputType="password"/>
                        <InputWithLabel text="Confirmar contraseña" labelStyles="font-(family-name:--title-font) text-base"
                            inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)" inputType="password"/>
                        <input type="submit" className="rounded-button blue-rounded-button mt-5" value={"Crear cuenta"}/>
                    </div>
                    
                </div>
            </div>
            {/* La imagen*/}
             <div
                className={`absolute w-1/2 h-full overflow-hidden transition-all duration-700 ease-in-out
                    ${isLogin ? 'translate-x-0 rounded-r-[3rem]' : 'translate-x-full rounded-l-[3rem]'}
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
            {/* Contenedor de login*/}
            <div className="w-full flex flex-col items-center justify-center ">
                <div className="max-w-150 w-full p-4">
                    <h1 className="text-(--main-color) text-5xl">Iniciar sesión</h1>
                    <p className="text-base">¿No tienes cuenta? <button onClick={()=>setIsLogin(false)} className="underline text-(--main-color)">Crea una aquí</button></p>
                    <div className="flex flex-col gap-4 pt-5">
                        <InputWithLabel text="Correo" labelStyles="font-(family-name:--title-font) text-base"
                        inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)" inputType="email"/>
                        <InputWithLabel text="Contraseña" labelStyles="font-(family-name:--title-font) text-base"
                        inputStyles="text-base p-4 rounded-lx border-none bg-(--light-gray)" inputType="password"/>
                        <input type="submit" className="rounded-button blue-rounded-button mt-5" value={"Iniciar sesión"}/>
                    </div>
                </div>
            </div>
        </div>
    )
}