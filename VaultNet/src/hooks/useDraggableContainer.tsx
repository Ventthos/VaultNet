import { useRef, useEffect, useState } from "react";
import { useDragContext } from "../contexts/DragContext";

export function useDraggableContainer() {
  const { isDraggingObject } = useDragContext();
  const isDraggingObjectRef = useRef(isDraggingObject);
  const containerRef = useRef<HTMLDivElement>(null);
  const [isDragging, setIsDragging] = useState(false);
  const position = useRef({ x: 0, y: 0 });
  const lastPos = useRef({ x: 0, y: 0 });
  const scale = useRef(1);
  const lastDistance = useRef<number | null>(null);

  const applyTransform = () => {
    if (containerRef.current) {
      containerRef.current.style.transform = `translate(${position.current.x}px, ${position.current.y}px) scale(${scale.current})`;
      containerRef.current.style.transformOrigin = "0 0";
    }
  };

  useEffect(() => {
  isDraggingObjectRef.current = isDraggingObject;
}, [isDraggingObject]);

  useEffect(() => {
    const container = containerRef.current;
    if (!container) return;

    // Mouse drag
    const onMouseDown = (e: MouseEvent) => {
      setIsDragging(true);
      lastPos.current = { x: e.clientX, y: e.clientY };
      container.style.cursor = "grabbing";
    };

    const onMouseMove = (e: MouseEvent) => {
      if (isDraggingObjectRef.current || !isDragging) return;
      const dx = e.clientX - lastPos.current.x;
      const dy = e.clientY - lastPos.current.y;
      position.current.x += dx;
      position.current.y += dy;
      lastPos.current = { x: e.clientX, y: e.clientY };
      applyTransform();
    };

    const onMouseUp = () => {
      setIsDragging(false);
      container.style.cursor = "grab";
    };

    // Touch drag and pinch zoom
    const onTouchStart = (e: TouchEvent) => {
      if (e.touches.length === 1) {
        setIsDragging(true);
        lastPos.current = { x: e.touches[0].clientX, y: e.touches[0].clientY };
      } else if (e.touches.length === 2) {
        setIsDragging(false);
        lastDistance.current = getTouchDistance(e.touches);
      }
    };

    const onTouchMove = (e: TouchEvent) => {
      if (isDraggingObjectRef.current) return;

      if (e.touches.length === 1 && isDragging) {
        // Movimiento con un dedo
        const dx = e.touches[0].clientX - lastPos.current.x;
        const dy = e.touches[0].clientY - lastPos.current.y;
        position.current.x += dx;
        position.current.y += dy;
        lastPos.current = { x: e.touches[0].clientX, y: e.touches[0].clientY };
        applyTransform();
      } else if (e.touches.length === 2) {
        // Pinch zoom con dos dedos
        e.preventDefault(); // Previene scroll del navegador

        const newDistance = getTouchDistance(e.touches);
        if (lastDistance.current != null) {
          const zoomFactor = newDistance / lastDistance.current;
          const prevScale = scale.current;
          const newScale = prevScale * zoomFactor;
          const scaleDelta = newScale / prevScale;

          const rect = container.getBoundingClientRect();
          const centerX = (e.touches[0].clientX + e.touches[1].clientX) / 2 - rect.left;
          const centerY = (e.touches[0].clientY + e.touches[1].clientY) / 2 - rect.top;

          position.current.x -= centerX * (scaleDelta - 1);
          position.current.y -= centerY * (scaleDelta - 1);66

          scale.current = newScale;
          applyTransform();
        }
        lastDistance.current = newDistance;
      }
    };

    const onTouchEnd = () => {
      setIsDragging(false);
      lastDistance.current = null;
    };

    // Mouse wheel zoom
    const onWheel = (e: WheelEvent) => {
      e.preventDefault();
      if (!container) return;

      const rect = container.getBoundingClientRect();
      const offsetX = e.clientX - rect.left;
      const offsetY = e.clientY - rect.top;

      const zoomIntensity = 0.001;
      const delta = e.deltaY * zoomIntensity;

      const prevScale = scale.current;
      const newScale = prevScale * (1 - delta);
      const scaleDelta = newScale / prevScale;

      position.current.x -= offsetX * (scaleDelta - 1);
      position.current.y -= offsetY * (scaleDelta - 1);

      scale.current = newScale;
      applyTransform();
    };

    container.addEventListener("mousedown", onMouseDown);
    document.addEventListener("mousemove", onMouseMove);
    document.addEventListener("mouseup", onMouseUp);

    container.addEventListener("touchstart", onTouchStart, { passive: false });
    container.addEventListener("touchmove", onTouchMove, { passive: false });
    container.addEventListener("touchend", onTouchEnd);

    container.addEventListener("wheel", onWheel, { passive: false });

    container.style.cursor = "grab";
    applyTransform();

    return () => {
      container.removeEventListener("mousedown", onMouseDown);
      document.removeEventListener("mousemove", onMouseMove);
      document.removeEventListener("mouseup", onMouseUp);

      container.removeEventListener("touchstart", onTouchStart);
      container.removeEventListener("touchmove", onTouchMove);
      container.removeEventListener("touchend", onTouchEnd);

      container.removeEventListener("wheel", onWheel);
    };
  }, [isDragging]);

  return { containerRef };
}

// Helpers
function getTouchDistance(touches: TouchList) {
  const dx = touches[0].clientX - touches[1].clientX;
  const dy = touches[0].clientY - touches[1].clientY;
  return Math.sqrt(dx * dx + dy * dy);
}
