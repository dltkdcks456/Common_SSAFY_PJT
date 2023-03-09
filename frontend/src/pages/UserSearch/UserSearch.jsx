/* eslint-disable */
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import Button from '../../components/commons/button';
import Input from '../../components/commons/input';
import NowContainer from '../../components/commons/nowLocation';
import {
  SEARCH_USER_REQUEST,
  SEARCH_USER_RESET,
} from '../../store/modules/userModule';
import { BASE_URL } from '../../utils/urls';
import {
  Container,
  Image,
  SearchForm,
  SearchInput,
  Wrapper,
} from './UserSearch.style';

const UserSearch = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [nickname, setNickname] = useState('');
  const inputNickname = e => {
    setNickname(e.target.value);
  };
  
  useEffect(() => {
    setNickname('');
    searchUsers = [];
    return () => dispatch({ type: SEARCH_USER_RESET });
  }, []);

  const onClick = e => {
    e.preventDefault();
    dispatch({ type: SEARCH_USER_REQUEST, data: { nickname } });
  };

  let searchUsers = useSelector(state => state.user.searchUsers);

  // console.log(searchUsers);
  if (nickname === '') {
    searchUsers = [];
  }
  return (
    <>
      <NowContainer desc="유 저 검 색" />
      <Container>
        <SearchInput>
          <SearchForm>
            <Input
              value={nickname}
              onChange={inputNickname}
              placeholder="닉네임을 입력하세요"
            />
            <Button onClick={onClick} name="검색" bc="#ffffff">
              검색
            </Button>
          </SearchForm>
        </SearchInput>
        <Wrapper dir="column" width="25rem" jc="center">
          {searchUsers.length > 0 && (
            <>
              {searchUsers.map(searchUser => (
                <div className="body" key={searchUser.nickname}>
                  <div
                    className="content"
                    key={searchUser.nickname}
                    onClick={() => {
                      navigate('/user/userinfo', {
                        state: searchUser.nickname,
                      });
                    }}
                  >
                    <Image
                      src={`${BASE_URL}user/imageDownloadBy/${searchUser.image_path}`}
                    />
                    <div className="text">{searchUser.nickname}</div>
                    {/* <div>{searchUsers.image_path}</div> */}
                  </div>
                  <div>
                    <Button
                      name="프로필 보기"
                      width="6rem"
                      bc="white"
                      ml="auto"
                      font="Pretendard-Regular"
                      onClick={() => {
                        navigate('/user/userinfo', {
                          state: searchUser.nickname,
                        });
                      }}
                    />
                  </div>
                </div>
              ))}
            </>
          )}
        </Wrapper>
      </Container>
    </>
  );
};

export default UserSearch;
