import { useEffect, useState } from "react";
import { PersonToAddWidget } from "./PersonToAddWidget";
import type { User } from "../../models/users/Local/User";
import { getUser } from "../../services/users/GetUser";
import { debounce } from "../../utils/debounce";

export function SearchMemberWindow(){
    const [peopleFound, setPeopleFound] = useState<User[]>([])
    const [emailInInput, setEmailInInput] = useState('');
    
    async function searchPeople(email: string){
        const response = (await getUser(email, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJyYTA0QGxpdmUuY29tLm14IiwidXNlcklkIjoxLCJpYXQiOjE3NTQ0Mzc1NTEsImV4cCI6MTc1NDUyMzk1MX0.7DLZD0uaFb53Z0sJA97u15AKDWuO2pU2qtoGia-WMbU")).success?.data!
        setPeopleFound(response)
        console.log("Si jala");
        
    }

    const searchPeopleDebounced = debounce(searchPeople, 500);

    useEffect(() => {
        if(emailInInput === ''){
            setPeopleFound([]);
            return;
        }
        searchPeopleDebounced(emailInInput);
    }, [emailInInput])

    return (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-60">
            <div className="bg-white p-6 rounded-lg shadow-lg w-96">
                <div className="flex mb-4 items-center place-content-between">
                    <h2 className="text-xl font-semibold ">Buscar persona</h2>
                    <button>X</button>
                </div>
                
                <input
                    type="text"
                    placeholder="Ingresa el email del usuario"
                    className="w-full p-2 border border-gray-300 rounded mb-2"
                    value={emailInInput}
                    onChange={(e) => {
                        setEmailInInput(e.target.value);
                    }}
                />

                {
                    peopleFound.length > 0 && emailInInput !== ""?
                    <div className="bg-(--light-gray-2) p-2 rounded-lg max-h-40 overflow-y-auto flex flex-col gap-2">
                    {
                        peopleFound.map((person) => (
                            <PersonToAddWidget key={person.id} user={{"name": person.name, "email": person.email, "profilePicture": person.imageUrl}} />
                        ))
                    }
                    </div>
                    :<></>
                }
                
            </div>
        </div>
    );
}