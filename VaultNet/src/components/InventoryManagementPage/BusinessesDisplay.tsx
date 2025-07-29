export function BusinessesDisplay(){
    return(
        <div className="hidden md:block md:fixed top-18 bottom-0 w-19 bg-(--light-gray-3) shadow-[1px_4px_4px_rgba(0,0,0,0.25)] z-49 p-2 flex flex-col">
            <div className="flex-1 flex flex-col items-center gap-2 scrollbar-fade">
                {Array.from({ length: 40 }).map((_, i) => (
                    <div
                        key={i}
                        className="rounded-full w-12 min-h-12"
                        style={{ backgroundColor: "var(--light-gray-2)" }}
                    />
                ))}
            </div>
            <div className="rounded-full w-12 h-12 bg-(--light-gray-2) flex items-center justify-center mt-2">
                <button className="font-bold text-2xl">+</button>
            </div>
        </div>
    )
}