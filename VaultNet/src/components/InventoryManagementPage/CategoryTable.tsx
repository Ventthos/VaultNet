import { useDraggableObject } from "../../hooks/useDraggableObject";
import type { Category } from "../../models/category/Local/Category";

type Props = {
    readonly category: Category,
    readonly onHeaderTouched?: () => void,
    readonly onProductTouched?: () => void
}

export function CategoryTable({category, onHeaderTouched, onProductTouched} : Props){
    const { ref, onMouseDown, onTouchStart, style } = useDraggableObject();

    return(
       <div className="absolute left-100 top-50"
       ref={ref}
       onMouseDown={onMouseDown}
       onTouchStart={onTouchStart}
       style={style}>
            <div className={`p-3 text-white rounded-t-2xl text-center`} onClick={(e)=>{
                e.stopPropagation();
                onHeaderTouched?.()
                }}
                style={{ backgroundColor: category.color }}
                >
                <h1 className="text-2xl">{category.name}</h1>
            </div>
            <div className="p-2 bg-white rounded-b-2xl">
                <table className="">
                    <thead>
                        <tr className="bg-(--light-gray-2)">
                            <th className="p-2 rounded-l-xl font-normal text-sm text-left">Producto</th>
                            <th className="p-2 font-normal text-sm">Cant min.</th>
                            <th className="p-2 font-normal text-sm">Cant.</th>
                            <th className="p-2 rounded-r-xl font-normal text-sm">Medida</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr className="bg-white" onClick={(e) => {
                        e.stopPropagation(); 
                        onProductTouched?.();
                        }}>
                            <td className="p-2 pb-1 border-b border-black max-w-35 break-words">Pollodijsndsjkandaknad</td>
                            <td className="p-2 pb-1 border-b border-black text-center">1</td>
                            <td className="p-2 pb-1 border-b border-black text-center"><input type="number" className="max-w-20 text-center" defaultValue={30000000}/></td>
                            <td className="p-2 pb-1 border-b border-black text-center">kg</td>
                        </tr>
                    </tbody>
                    
                </table>
            </div>


       </div> 
    )
}