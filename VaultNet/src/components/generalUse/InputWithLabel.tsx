import { twMerge } from 'tailwind-merge';

interface Option {
    id: number;
    nombre: string;
}

type InputWithLabelProps = Readonly<{
    text: string;
    inputType?: "text" | "email" | "password" | "number" | "textarea" | "select" | "date" | "datetime" | "checkbox" | "radio" | "tel" | "url" | "color";
    value?: string;
    onChange?: (event: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement> | React.ChangeEvent<HTMLSelectElement>) => void;
    placeholder?: string;
    inputId?: string;
    inputName?: string;
    selectOptions?: Option[]
    labelStyles?: string;
    inputStyles?: string;
    messureUnit?: string
}>;

export function InputWithLabel({
    text, 
    inputType = "text",
    value,
    onChange,
    placeholder, 
    inputId, 
    inputName,
    selectOptions, 
    labelStyles,
    inputStyles,
    messureUnit
    } : InputWithLabelProps){
    return(
        <div className="flex flex-col w-full">
            <label htmlFor={inputId} className={twMerge("text-black font-bold", labelStyles)}>{text}</label>
            <div className='flex items-center gap-2'>
            {(() => {
                if (inputType === "textarea") {
                    return (
                        <textarea
                            id={inputId}
                            name={inputName}
                            placeholder={placeholder}
                            className={twMerge("bg-white p-2 rounded-xl border-2 border-black flex-1", inputStyles)}
                            value={value}
                            onChange={onChange}
                    
                        />
                    );
                }

                else if(inputType === "select") {
                    return(
                        <select name={inputName} id={inputId} className={twMerge("bg-white p-2 rounded-xl border-2 border-black flex-1", inputStyles)}
                        value={value}

                        onChange={onChange}>
                        {selectOptions?.map( option=>(
                            <option value={option.id} key={option.id}>{option.nombre}</option>
                        )
                        )}
                    </select>
                    )
                }

                return (
                    <input
                        type={inputType}
                        id={inputId}
                        name={inputName}
                        placeholder={placeholder}
                        className={twMerge("bg-white p-2 rounded-xl border-2 border-black flex-1", inputStyles)}
                        value={value}
                        onChange={onChange}
                    />
                );
            })()
            }
            {messureUnit && <p>{messureUnit}</p>}
            
            </div>
        </div>
    )
}