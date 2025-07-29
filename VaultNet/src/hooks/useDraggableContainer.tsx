import { useRef, useEffect, useState } from "react";

export function useDraggableContainer() {
  const containerRef = useRef<HTMLDivElement>(null);
  const [isDragging, setIsDragging] = useState(false);
  const position = useRef({ x: 0, y: 0 });
  const lastPos = useRef({ x: 0, y: 0 });

  useEffect(() => {
    const container = containerRef.current;
    if (!container) return;

    const setTransform = () => {
      container!.style.transform = `translate(${position.current.x}px, ${position.current.y}px)`;
    };

    // 🖱 Mouse handlers
    const onMouseDown = (e: MouseEvent) => {
      setIsDragging(true);
      lastPos.current = { x: e.clientX, y: e.clientY };
      container!.style.cursor = "grabbing";
    };

    const onMouseMove = (e: MouseEvent) => {
      if (!isDragging) return;

      const dx = e.clientX - lastPos.current.x;
      const dy = e.clientY - lastPos.current.y;

      position.current.x += dx;
      position.current.y += dy;
      setTransform();

      lastPos.current = { x: e.clientX, y: e.clientY };
    };

    const onMouseUp = () => {
      setIsDragging(false);
      container!.style.cursor = "grab";
    };

    // 🤳 Touch handlers
    const onTouchStart = (e: TouchEvent) => {
      if (e.touches.length !== 1) return;
      setIsDragging(true);
      const touch = e.touches[0];
      lastPos.current = { x: touch.clientX, y: touch.clientY };
    };

    const onTouchMove = (e: TouchEvent) => {
      if (!isDragging || e.touches.length !== 1) return;

      const touch = e.touches[0];
      const dx = touch.clientX - lastPos.current.x;
      const dy = touch.clientY - lastPos.current.y;

      position.current.x += dx;
      position.current.y += dy;
      setTransform();

      lastPos.current = { x: touch.clientX, y: touch.clientY };
    };

    const onTouchEnd = () => {
      setIsDragging(false);
    };

    // Agregar eventos
    container.addEventListener("mousedown", onMouseDown);
    document.addEventListener("mousemove", onMouseMove);
    document.addEventListener("mouseup", onMouseUp);

    container.addEventListener("touchstart", onTouchStart, { passive: false });
    document.addEventListener("touchmove", onTouchMove, { passive: false });
    document.addEventListener("touchend", onTouchEnd);

    return () => {
      container.removeEventListener("mousedown", onMouseDown);
      document.removeEventListener("mousemove", onMouseMove);
      document.removeEventListener("mouseup", onMouseUp);

      container.removeEventListener("touchstart", onTouchStart);
      document.removeEventListener("touchmove", onTouchMove);
      document.removeEventListener("touchend", onTouchEnd);
    };
  }, [isDragging]);

  return { containerRef };
}