import { createContext, useState } from "react";

type AuthContext = {
    token:string,
    setToken: (token:string) => void
}

export const AuthContext = createContext<AuthContext>({
    token: "",
    setToken: () => {}
});

export function AuthContexProvider({children}: {children: React.ReactNode}) {
    const [token, setToken] = useState<string>("");
    return (
        <AuthContext.Provider value={{token, setToken}}>
            {children}
        </AuthContext.Provider>
    )
}