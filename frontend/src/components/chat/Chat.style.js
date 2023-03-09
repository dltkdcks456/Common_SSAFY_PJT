import styled from 'styled-components';

export const Container = styled.div`
  position: fixed;
  bottom: 50px;
  right: 50px;
  width: 50px;
  height: 50px;
  color: #def1ff;
  padding: 28px;
  cursor: pointer;
  text-align: center;
  line-height: 5rem;
  font-size: 2.3rem;
  z-index: 10;
  .fa-comment-dots {
    filter: ${({ theme }) => theme.chatFilter};
  }
  .fa-bell {
    position: absolute;
    right: -10px;
    top: 20px;
    content: attr(data-count);
    font-size: 40%;
    padding: 0.6em;
    border-radius: 999px;
    line-height: 0.75em;
    color: white;
    background: rgba(255, 0, 0, 0.85);
    text-align: center;
    min-width: 2em;
    font-weight: bold;
  }
`;
