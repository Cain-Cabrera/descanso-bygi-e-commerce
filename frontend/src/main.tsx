import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App'
import { AuthProvider } from './contexts/AuthContext'
import { CarritoProvider } from './contexts/CarritoContext'

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <AuthProvider>
            <CarritoProvider>
                <App />
            </CarritoProvider>
        </AuthProvider>
    </StrictMode>
)