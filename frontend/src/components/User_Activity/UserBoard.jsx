/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { onDeleteArticleAPI } from '../../store/apis/communityApi';
import { getBoardList } from '../../store/apis/myPageApi';
import { localDateTime } from '../../utils/DateFormatter';
import Button from '../commons/button';
import {
  BoardItem,
  BoardList,
  Container,
  ItemEnd,
  ItemTitle,
} from './UserBoard.style';

const UserBoard = ({ user, me }) => {
  const [boardList, setBoardList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const getBoards = () => {
    getBoardList({ nickname: user.nickname })
      .then(res => {
        setBoardList(res.data);
        setIsLoading(true);
      })
      .catch(err => console.log(err));
  };

  useEffect(() => {
    getBoards();
  }, [isLoading]);
  // console.log(boardList);
  return (
    <>
      <Container>
        <BoardList>
          {boardList.length > 0 ? boardList.map(board => (
            <BoardItem key={board.board_code_name + board.board_id}>
              <ItemTitle
                onClick={e => {
                  e.preventDefault();
                  navigate('/community/free/detail', {
                    state: {
                      boardId: board.board_id,
                      boardCode: board.board_code,
                    },
                  });
                }}
              >
                <div className="kind">{board.board_code_name}</div>
                <div className="title">{board.board_title}</div>
                <div className="number">
                  <div className="numberItem">
                    <i
                      className="tiny material-icons"
                      style={{ fontSize: '1rem' }}
                    >
                      remove_red_eye
                    </i>
                    {board.board_visited}
                  </div>
                  <div className="numberItem">
                    <i
                      className="tiny material-icons"
                      style={{ fontSize: '1rem' }}
                    >
                      thumb_up
                    </i>
                    {board.board_like_count}
                  </div>
                </div>
              </ItemTitle>
              <ItemEnd>
                {user.nickname === me.nickname ? (
                  <Button
                    name="삭제"
                    width="3rem"
                    bc="white"
                    font="Pretendard-Regular"
                    onClick={async e => {
                      e.preventDefault();
                      await onDeleteArticleAPI({
                        boardId: board.board_id,
                      }).then(() => getBoards());
                    }}
                  />
                ) : (
                  <div></div>
                )}
                <div>{localDateTime(board.board_reg_time)}</div>
              </ItemEnd>
            </BoardItem>
          )) : (
            <div style={{textAlign: 'center'}}>
              작성한 글이 없습니다
            </div>
          )}
        </BoardList>
      </Container>
    </>
  );
};

export default UserBoard;
