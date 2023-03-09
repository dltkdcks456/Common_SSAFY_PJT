/* eslint-disable import/no-extraneous-dependencies */
import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${reset}
  body {
    background: ${({ theme }) => theme.bgColor};
    color: ${({ theme }) => theme.textColor};
    font-family: "Noto Sans KR", sans-serif;
  }
`;

export default GlobalStyle;
