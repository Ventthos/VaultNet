
import { MessageContextProvider } from './contexts/messageContext'
import VaultNetRouter from './router/router'
import { BrowserRouter, Router } from 'react-router-dom'
import { DragProvider } from './contexts/DragContext'
import { AuthContexProvider } from './contexts/AuthContext'

function App() {

  return (
    <AuthContexProvider>
      <MessageContextProvider>
        <DragProvider>
          <BrowserRouter>
            <VaultNetRouter/>
          </BrowserRouter>
        </DragProvider>
      </MessageContextProvider>  
    </AuthContexProvider>
    
  )
}

export default App
