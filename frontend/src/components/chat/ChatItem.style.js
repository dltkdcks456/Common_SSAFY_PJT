import styled from 'styled-components';

export const List = styled.div`
  /* cursor: pointer; */
  display: grid;
  grid-template-columns: 2fr 7fr 2fr;
  margin-bottom: 1rem;
  /* margin-top: 1rem; */
`;

export const Image = styled.img`
  width: 3rem;
  height: 3rem;
  border-radius: 45px;
`;

export const Middle = styled.div`
  display: grid;
  grid-template-rows: 0fr 1fr;
  font-size: 1rem;
  max-width: 12rem;
`;

export const Name = styled.div`
  color: black;
`;

export const Content = styled.div`
  margin: 0.4rem 0;
  line-height: 1.5rem;
  font-size: 0.9rem;
  color: ${({ theme }) => theme.menuColor};

  /* ... 으로 만들어 주는 코드 */
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
`;

export const Date = styled.div`
  color: #c4c4c4;
  font-size: 0.8rem;
  text-align: center;
  div {
    color: red;
  }
`;
