import { PersistGate } from 'redux-persist/integration/react';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import storeConfigure from './store/index';
import App from './App';
import './index.css';
import Footer from './components/Footer/Footer';

// import './assets/fonts/pretendard.css';
// import { ThemeProvider } from '@mui/material';
// import { theme } from './utils/config';
const store = storeConfigure();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Provider store={store.store}>
    <PersistGate loading={null} persistor={store.persistor}>
      <App />
      <Footer />
    </PersistGate>
  </Provider>,
);
