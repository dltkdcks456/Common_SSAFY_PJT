import React, { useEffect, useState } from 'react';
import styled from 'styled-components';

import { useDispatch, useSelector } from 'react-redux';
// import FreeArticleListItem from './FreeArticleListItem';
import {
  LOAD_FREE_BOARD_REQUEST,
  SEARCH_BOARD_REQUEST,
} from '../../store/modules/communityModule';
import Pagination from '../Pagination/Pagination';
import FreeArticleListItem from './FreeArticleListItem';

const StyledTable = styled.table`
  width: 80%;
  margin-left: 10%;
  /* border: 1px solid black; */
`;

const StyledCol = styled.col`
  width: ${props => props.width};
`;

const StyledTh = styled.td`
  background-color: #def1ff;
  height: 2.2rem;
  vertical-align: middle;
  text-align: center;
  font-weight: 600;
  border-radius: 3px;
  color: black;
  & + & {
    border-left: 2px solid ${({ theme }) => theme.ContainerColor};
  }
`;

const PageContainer = styled.div`
  margin-top: 1rem;
`;

const FreeArticleList = ({ currentArticle, keyword, inputKeyword }) => {
  const [page, setPage] = useState(0);
  const dispatch = useDispatch();
  useEffect(() => {
    if (!inputKeyword) {
      dispatch({
        type: LOAD_FREE_BOARD_REQUEST,
        data: {
          page,
          boardCode: currentArticle,
        },
      });
    } else if (inputKeyword && inputKeyword === keyword) {
      dispatch({
        type: SEARCH_BOARD_REQUEST,
        data: {
          page,
          keyword,
          boardCode: currentArticle,
        },
      });
    }
  }, [currentArticle, page, keyword]);
  const { freeBoards } = useSelector(state => state.community);
  return (
    <div>
      {freeBoards.content && (
        <StyledTable>
          <colgroup>
            <StyledCol width="7%" />
            <StyledCol width="58%" />
            <StyledCol width="13%" />
            <StyledCol width="17%" />
            <StyledCol width="5%" />
          </colgroup>
          <thead>
            <tr>
              <StyledTh>번호</StyledTh>
              <StyledTh>제목</StyledTh>
              <StyledTh>작성일</StyledTh>
              <StyledTh>작성자</StyledTh>
              <StyledTh>조회수</StyledTh>
            </tr>
          </thead>
          <tbody>
            {freeBoards.content.map(board => (
              <FreeArticleListItem
                key={board.boardId}
                board={board}
                boardCode={currentArticle}
              />
            ))}
          </tbody>
        </StyledTable>
      )}
      {freeBoards.content && (
        <PageContainer>
          <Pagination
            page={page}
            setPage={setPage}
            totalPage={freeBoards.totalPages}
          />
        </PageContainer>
      )}
    </div>
  );
};
export default FreeArticleList;
