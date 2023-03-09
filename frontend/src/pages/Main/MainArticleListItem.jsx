import React from 'react';
import moment from 'moment';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import newImg from '../../assets/images/new.png';

const MainStyledTr = styled.tr`
  /* cursor: pointer; */
  &:hover {
    background-color: ${({ theme }) => theme.noticeHoverColor};
  }
  & + & {
    border-top: 1px solid #dedede;
  }
`;

const MainStyledTd = styled.td`
  height: 1.5rem;
  vertical-align: middle;
  padding: 0 0.7rem;
  font-size: 13px;
  white-space: nowrap;
`;

const MainArticleListItem = ({ board, boardCode }) => {
  const articleTime = moment(board.regTime).format('YY-MM-DD');
  // const { boardId } = board.boardId;
  return (
    <MainStyledTr>
      <MainStyledTd>
        <Link
          to="/community/free/detail"
          state={{ boardId: board.boardId, boardCode: Number(boardCode) }}
          style={{
            textDecoration: 'none',
            color: 'black',
          }}
        >
          <div
            style={{
              width: '17px',
              whiteSpace: 'nowrap',
              textOverflow: 'ellipsis',
              overflow: 'hidden',
            }}
          >
            {board.boardId}
          </div>
        </Link>
      </MainStyledTd>
      <MainStyledTd>
        <Link
          to="/community/free/detail"
          state={{ boardId: board.boardId, boardCode: Number(boardCode) }}
          style={{
            textDecoration: 'none',
            color: 'black',
          }}
        >
          <div
            style={{
              width: '320px',
              whiteSpace: 'nowrap',
              textOverflow: 'ellipsis',
              overflow: 'hidden',
            }}
          >
            {board.title}
            <img
              src={newImg}
              style={{
                marginLeft: '5px',
                marginTop: '2px',
                width: '12px',
                height: '12px',
              }}
              alt=""
            />
          </div>
        </Link>
      </MainStyledTd>
      <MainStyledTd>{articleTime}</MainStyledTd>
      <MainStyledTd>
        <div
          style={{
            width: '54px',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            overflow: 'hidden',
            textAlign: 'center',
          }}
        >
          {board.userNickname}
        </div>
      </MainStyledTd>
      <MainStyledTd>
        <div
          style={{
            width: '25px',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            overflow: 'hidden',
            textAlign: 'center',
          }}
        >
          {board.visited}
        </div>
      </MainStyledTd>
    </MainStyledTr>
  );
};
export default MainArticleListItem;
