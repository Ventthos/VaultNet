// DragContext.tsx
import { createContext, useContext, useState } from "react";
import type {ReactNode} from "react"

type DragContextType = {
  isDraggingObject: boolean;
  setIsDraggingObject: (value: boolean) => void;
};

const DragContext = createContext<DragContextType | undefined>(undefined);

export function DragProvider({ children }: { children: ReactNode }) {
  const [isDraggingObject, setIsDraggingObject] = useState(false);

  return (
    <DragContext.Provider value={{ isDraggingObject, setIsDraggingObject }}>
      {children}
    </DragContext.Provider>
  );
}

export function useDragContext() {
  const context = useContext(DragContext);
  if (!context) throw new Error("useDragContext must be used within DragProvider");
  return context;
}
