
import { MessageContextProvider } from './contexts/messageContext'
import VaultNetRouter from './router/router'
import { BrowserRouter, Router } from 'react-router-dom'

function App() {

  return (
    <MessageContextProvider>
      <BrowserRouter>
        <VaultNetRouter/>
      </BrowserRouter>
    </MessageContextProvider>
  )
}

export default App
