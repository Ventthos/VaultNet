
import { MessageContextProvider } from './contexts/messageContext'
import VaultNetRouter from './router/router'
import { BrowserRouter, Router } from 'react-router-dom'
import { DragProvider } from './contexts/DragContext'

function App() {

  return (
    <MessageContextProvider>
      <DragProvider>
        <BrowserRouter>
          <VaultNetRouter/>
        </BrowserRouter>
      </DragProvider>
    </MessageContextProvider>
  )
}

export default App
