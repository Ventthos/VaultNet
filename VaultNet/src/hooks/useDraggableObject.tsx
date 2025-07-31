import { useRef, useState, useEffect, useCallback } from "react";
import { useDragContext } from "../contexts/DragContext";

export function useDraggableObject() {
  const [position, setPosition] = useState({ x: 0, y: 0 });
  const dragging = useRef(false);
  const offset = useRef({ x: 0, y: 0 });
  const ref = useRef<HTMLDivElement>(null);
  const { setIsDraggingObject } = useDragContext();

  const startDrag = useCallback((x: number, y: number) => {
    setIsDraggingObject(true);
    offset.current = {
      x: x - position.x,
      y: y - position.y,
    };
    dragging.current = true;
  }, [position, setIsDraggingObject]);

  const updatePosition = useCallback((x: number, y: number) => {
    if (!dragging.current) return;
    setPosition({
      x: x - offset.current.x,
      y: y - offset.current.y,
    });
  }, []);

  const stopDrag = useCallback(() => {
    dragging.current = false;
    setIsDraggingObject(false);
  }, [setIsDraggingObject]);

  // Mouse
  const onMouseDown = useCallback((e: React.MouseEvent) => {
    e.stopPropagation();
    e.preventDefault();
    startDrag(e.clientX, e.clientY);
  }, [startDrag]);

  const onMouseMove = useCallback((e: MouseEvent) => {
    updatePosition(e.clientX, e.clientY);
  }, [updatePosition]);

  const onMouseUp = useCallback(() => stopDrag(), [stopDrag]);

  // Touch
  const onTouchStart = useCallback((e: React.TouchEvent) => {
    e.stopPropagation();
    const touch = e.touches[0];
    
    startDrag(touch.clientX, touch.clientY);
  }, [startDrag]);

  const onTouchMove = useCallback((e: TouchEvent) => {
    if (!dragging.current) return;
    const touch = e.touches[0];
    updatePosition(touch.clientX, touch.clientY);
  }, [updatePosition]);

  const onTouchEnd = useCallback(() => stopDrag(), [stopDrag]);

  useEffect(() => {
    document.addEventListener("mousemove", onMouseMove);
    document.addEventListener("mouseup", onMouseUp);
    document.addEventListener("touchmove", onTouchMove);
    document.addEventListener("touchend", onTouchEnd);
    return () => {
      document.removeEventListener("mousemove", onMouseMove);
      document.removeEventListener("mouseup", onMouseUp);
      document.removeEventListener("touchmove", onTouchMove);
      document.removeEventListener("touchend", onTouchEnd);
    };
  }, [onMouseMove, onMouseUp, onTouchMove, onTouchEnd]);

  return {
    ref,
    position,
    onMouseDown,
    onTouchStart,
    style: {
      transform: `translate(${position.x}px, ${position.y}px)`,
      cursor: dragging.current ? "grabbing" : "grab",
      touchAction: "none"
    }
  };
}
