import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import rootReducer from './modules/index';
import rootSaga from './sagas/index';

const storeConfigure = () => {
  const sagaMiddleware = createSagaMiddleware({});

  const persistConfig = {
    key: 'root',
    storage,
    whitelist: ['myPage', 'logInTime'],
  };
  const persistedReducer = persistReducer(persistConfig, rootReducer);

  const store = configureStore({
    reducer: persistedReducer,
    middleware: [sagaMiddleware],
  });
  const persistor = persistStore(store);
  sagaMiddleware.run(rootSaga);
  return { store, persistor };
};

export default storeConfigure;
