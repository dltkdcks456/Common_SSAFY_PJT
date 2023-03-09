/* eslint-disable */
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate, useParams } from 'react-router-dom';
import Button from '../../components/commons/button';
import Input from '../../components/commons/input';
import FreeArticleList from '../../components/Community/FreeArticleList';
import { SEARCH_BOARD_REQUEST } from '../../store/modules/communityModule';

const ArticleList = () => {
  const { boardCode } = useParams();

  const [keyword, setKeyword] = useState('');
  const [inputKeyword, setInputKeyword] = useState('');
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { myPageDone } = useSelector(state => state.myPage);
  const onChange = e => {
    setKeyword(e.target.value);
  };

  // page 이동 시 글쓰기 클릭하면 navigate가 이를 인지하고 이동할 수 있게 해야함.
  // 뒤로가기에도 해당 내용 적용 필요
  const onSubmit = e => {
    e.preventDefault();
    setInputKeyword(keyword);
    dispatch({
      type: SEARCH_BOARD_REQUEST,
      data: {
        page: 0,
        keyword,
        boardCode,
      },
    });
  };

  const onClick = () => {
    navigate('/community/create', { state: { board_code: boardCode } });
  };

  return (
    <div>
      <FreeArticleList
        currentArticle={boardCode}
        keyword={keyword}
        inputKeyword={inputKeyword}
      />
      <div
        style={{
          display: 'flex',
          width: '80%',
          justifyContent: 'space-between',
          marginLeft: '10%',
        }}
      >
        <form style={{ marginLeft: '38%' }} onSubmit={onSubmit}>
          <Input
            onChange={onChange}
            value={keyword}
            type="text"
            placeholder="검색어를 입력해주세요"
            width="18rem"
            mr="0.1rem"
            ml="0.1rem"
            mt="0.2rem"
          />
          <Button
            width="5rem"
            ml="0.1rem"
            mr="0.1rem"
            mt="0.2rem"
            height="2.3rem"
            bc="white"
            name="검색"
          />
        </form>
        <Button
          onClick={onClick}
          width="5rem"
          ml="0.1rem"
          mr="0.1rem"
          mt="0.2rem"
          height="2.3rem"
          bc="white"
          name="글쓰기"
        />
      </div>
    </div>
  );
};
export default ArticleList;
