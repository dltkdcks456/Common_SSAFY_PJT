import React from 'react';
import moment from 'moment';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const StyledTr = styled.tr`
  /* cursor: pointer; */
  &:hover {
    background-color: ${({ theme }) => theme.noticeHoverColor};
  }
  & + & {
    border-top: 1px solid #dedede;
  }
`;

const StyledTd = styled.td`
  height: 3rem;
  vertical-align: middle;
  text-align: ${props => props.ta || 'center'};
  padding: 0 1.5rem;
`;

const FreeArticleListItem = ({ board, boardCode }) => {
  const articleTime = moment(board.regTime).format('YYYY-MM-DD');
  // const { boardId } = board.boardId;
  return (
    <StyledTr>
      <StyledTd>
        <Link
          to="/community/free/detail"
          state={{ boardId: board.boardId, boardCode: Number(boardCode) }}
          style={{ textDecoration: 'none', color: 'black' }}
        >
          {board.boardId}
        </Link>
      </StyledTd>
      <StyledTd>
        <Link
          to="/community/free/detail"
          state={{ boardId: board.boardId, boardCode: Number(boardCode) }}
          style={{ textDecoration: 'none', color: 'black' }}
        >
          {board.title}
        </Link>
      </StyledTd>
      <StyledTd>{articleTime}</StyledTd>
      <StyledTd>{board.userNickname}</StyledTd>
      <StyledTd>{board.visited}</StyledTd>
    </StyledTr>
  );
};
export default FreeArticleListItem;
