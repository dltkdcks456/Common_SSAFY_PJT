/* eslint-disable */
import moment from 'moment';
import styled from 'styled-components';
import React, { useEffect, useState } from 'react';
import { Viewer } from '@toast-ui/react-editor';
import { useLocation, useNavigate } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import {
  FreeDetailAPI,
  onDeleteArticleAPI,
} from '../../store/apis/communityApi';
import {
  LIKE_BOARD_REQUEST,
  LIKE_CLICK_BOARD_REQUEST,
} from '../../store/modules/communityModule';
import { MY_PAGE_REQUEST } from '../../store/modules/myPageModule';
import Comment from '../../components/Community/Comment';
import NowContainer from '../../components/commons/nowLocation';
import likeImg from '../../assets/images/like.png';
import eyeImg from '../../assets/images/eye.png';
import emptyHeartImg from '../../assets/images/emptyheart.png';
import fullHeartImg from '../../assets/images/fullheart.png';
import Button from '../../components/commons/button';

const StyledTitleContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledLikeVisited = styled.div`
  display: flex;
  align-items: flex-end;
`;

const StyledLike = styled.div`
  display: flex;
`;

const StyledVisited = styled.div`
  display: flex;
`;

const StyledNickNameTime = styled.div`
  display: flex;
  font-size: 13px;
  justify-content: flex-end;
`;

const StyledUpdateDelete = styled.div`
  /* display: flex;
  justify-content: flex-end; */
  margin-right: 0%;
`;

const StyledLikeBtn = styled.div`
  /* display: flex;
  justify-content: center; */
  /* margin-left: 10%; */
`;

const StyledLikeUpdateDelete = styled.div`
  display: flex;
  justify-content: space-between;
`;

const DetailContainer = styled.div`
  margin-top: 2rem;
  margin-left: 15%;
  margin-right: 15%;
  padding: 2rem 2rem;
  box-sizing: border-box;
  overflow: auto;
  border: 1px solid #a0a0a0;
  border-radius: 10px;
  animation: smoothAppear;
  animation-duration: ${props => (props.duration ? props.duration : '0.5s')};
`;

const DetailArticle = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [detail, setData] = useState('');
  // const [userId, setUserId] = useState(null);
  const {
    state: { boardId, boardCode },
  } = useLocation();
  const { setLikeDone } = useSelector(state => state.community);
  const { logInDone } = useSelector(state => state.user);
  const { myPageDone, user } = useSelector(state => state.myPage);
  const userToken = sessionStorage.getItem('userToken');

  // console.log(user.user_id);
  // console.log(detail.userId);

  // 좋아요 조회하기
  const getLiked = () => {
    dispatch({
      type: LIKE_BOARD_REQUEST,
      data: {
        boardId,
        userId: user.user_id,
      },
    });
  };

  const getDetailArticle = async () => {
    const result = await FreeDetailAPI({ boardId, boardCode, user });
    setData(result);
    // setUserId(result.userId);
  };
  useEffect(() => {
    if (!detail) {
      getDetailArticle();
    }
    if (user.user_id) {
      getLiked();
    }
    if (!myPageDone && userToken) {
      dispatch({
        type: MY_PAGE_REQUEST,
        data: {
          token: userToken,
        },
      });
    }
    // dispatch({
    //   type: LOAD_FREE_DETAIL_REQUEST,
    //   data: { boardId, boardCode },
    // });
  }, [detail, dispatch, myPageDone, logInDone, setLikeDone]);
  // const data = useSelector(state => state.community.freeDetail);
  // console.log(detail);
  // const detail = useSelector(state => state.community.freeDetail);
  const detailTime = moment(detail.regTime).format('YYYY-MM-DD');
  const like = useSelector(state => state.community.like);
  // console.log('좋아요', like);
  // 삭제하기 버튼 액션
  const onDelete = async () => {
    try {
      await onDeleteArticleAPI({ boardId });
    } catch (error) {
      console.log(error);
    }
    navigate(`/community/free/${boardCode}`);
  };
  // console.log('detail:', detail);
  // console.log(detail.content);
  // console.log(loading);

  // 수정하기 버튼 액션
  const onUpdate = async () => {
    try {
      navigate(`/community/update`, { state: detail });
    } catch (error) {
      console.log(error);
    }
  };
  // 좋아요 버튼 클릭
  const onLike = () => {
    dispatch({
      type: LIKE_CLICK_BOARD_REQUEST,
      data: {
        board_id: boardId,
        user_id: user.user_id,
        navigate,
      },
    });
  };

  const onList = () => {
    navigate(`/community/free/${boardCode}`);
  };

  return (
    <div>
      <NowContainer desc="R i d e W a y" />
      {detail && (
        <DetailContainer>
          <StyledTitleContainer>
            <h1>{detail.title}</h1>
            <StyledLikeVisited>
              <StyledVisited>
                <img
                  src={eyeImg}
                  style={{
                    width: '20px',
                    marginLeft: '5px',
                    marginRight: '5px',
                    height: '20px',
                  }}
                  alt="a"
                ></img>
                <div>{detail.visited}</div>
              </StyledVisited>
              <StyledLike>
                <img
                  src={likeImg}
                  style={{
                    width: '20px',
                    marginLeft: '5px',
                    marginRight: '5px',
                    height: '20px',
                  }}
                  alt="a"
                ></img>
                <div>{detail.likeCount}</div>
              </StyledLike>
            </StyledLikeVisited>
          </StyledTitleContainer>
          <hr />
          <StyledNickNameTime>
            <div style={{ fontWeight: 'bolder', marginRight: '5px' }}>
              {detail.userNickname}
            </div>
            <div style={{ fontWeight: 'bolder' }}>{detailTime}</div>
          </StyledNickNameTime>
          <Viewer initialValue={detail.content} />
          <StyledLikeUpdateDelete>
            <Button
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="글 목록"
              onClick={onList}
            ></Button>
            <StyledLikeBtn>
              {like ? (
                <img
                  src={fullHeartImg}
                  style={{ width: '50px', cursor: 'pointer' }}
                  onClick={onLike}
                ></img>
              ) : (
                <img
                  src={emptyHeartImg}
                  style={{ width: '50px', cursor: 'pointer' }}
                  onClick={onLike}
                ></img>
              )}
            </StyledLikeBtn>
            {user && detail && user.user_id === detail.userId ? (
              <StyledUpdateDelete>
                <Button
                  width="5rem"
                  ml="0.1rem"
                  mr="0.1rem"
                  mt="0.2rem"
                  height="2.3rem"
                  bc="white"
                  name="수정하기"
                  onClick={onUpdate}
                ></Button>
                <Button
                  width="5rem"
                  ml="0.1rem"
                  mr="0.1rem"
                  mt="0.2rem"
                  height="2.3rem"
                  bc="white"
                  name="삭제하기"
                  onClick={onDelete}
                ></Button>
              </StyledUpdateDelete>
            ) : (
              <StyledUpdateDelete>
                <div> </div>
              </StyledUpdateDelete>
            )}
          </StyledLikeUpdateDelete>
          <hr />
          <Comment user={user} boardId={boardId} boardCode={boardCode} />
        </DetailContainer>
      )}
    </div>
  );
};

export default DetailArticle;
