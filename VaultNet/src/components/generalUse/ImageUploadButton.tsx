import { useRef, useState } from "react";

export function ImageUploadButton({name, id,callbackFuncion}: {readonly name: string, readonly id?: string, readonly callbackFuncion?: (file: File)=>void}) {
  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const [imageUrl, setImageUrl] = useState<string>("/img/no-image-available.jpg");

  const handleButtonClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
        const imageUrl = URL.createObjectURL(file);
        setImageUrl(imageUrl);

        callbackFuncion?.(file);
    }
  };

  return (
    <div>
      <button type="button" onClick={handleButtonClick} className="rounded-full">
        <img src={imageUrl} alt="" className="w-25 h-25 rounded-full border border-dashed border-2"/>
      </button>

      <input
        type="file"
        accept="image/*"
        ref={fileInputRef}
        onChange={handleFileChange}
        className="hidden"
        name={name}
        id={id}
      />
    </div>
  );
};

export default ImageUploadButton;