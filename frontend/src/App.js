/* eslint-disable import/named */
import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import styled, { ThemeProvider } from 'styled-components';
import { useSelector } from 'react-redux';
import { Router } from './router/index';
import Layout from './components/Layout/Layout';
import { theme } from './Style/theme';

const RootContainer = styled.div`
  /* min-height: ; */
  min-height: 1000px;
`;

const App = () => {
  const { isDarkMode } = useSelector(state => state.theme);
  return (
    <RootContainer>
      <BrowserRouter>
        <ThemeProvider theme={isDarkMode ? theme.darkTheme : theme.lightTheme}>
          <Layout />
          <Routes>
            <Route path="*" element={<Router />} />
          </Routes>
        </ThemeProvider>
      </BrowserRouter>
    </RootContainer>
  );
};

export default App;
