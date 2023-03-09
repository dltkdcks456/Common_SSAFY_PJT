/* eslint-disable */
import { Editor } from '@toast-ui/react-editor';
import styled from 'styled-components';
import '@toast-ui/editor/dist/toastui-editor.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import React, { useEffect, useRef, useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router';
import { UPDATE_BOARD_REQUEST } from '../../store/modules/communityModule';
import { BASE_URL } from '../../utils/urls';
import { UPDATE_SHOP_REQUEST } from '../../store/modules/shopModule';
import NowContainer from '../../components/commons/nowLocation';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

const StyledButton = styled.button`
  width: ${props => (props.width ? props.width : '10rem')};
  height: ${props => (props.height ? props.height : '2rem')};
  font-size: ${props => (props.fontSize ? props.fontSize : '1rem')};
  color: ${props => (props.color ? props.color : '')};
  background-color: ${props => (props.bc ? props.bc : '#c4c4c4')};
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  padding: ${props => (props.padding ? props.padding : '')};
  /* border: 'solid 0.5px'; */
  border: ${props => (props.border ? props.border : 'solid 0.5px')};
  border-color: #a3a3a3;
  border-radius: ${props => (props.br ? props.br : '5px')};
  font-family: ${props => (props.font ? props.font : 'Pretendard-SemiBold')};
  z-index: ${props => (props.z ? props.z : '')};
  transition: 0.2s;
  cursor: pointer;

  &:hover {
    transition: 0.3s;
    background-color: ${props =>
      props.hoverColor ? props.hoverColor : '#a2a2a2'};
  }

  &:disabled {
    background-color: whitesmoke;
    cursor: no-drop;
  }
`;

const Button = props => (
  <StyledButton {...props} disabled={props.disabled}>
    {props.name}
  </StyledButton>
);

const StyleContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const StyledCommentInput = styled.input`
  width: ${props => (props.width ? props.width : '95%')};
  height: ${props => (props.height ? props.height : '2rem')};
  border-radius: ${props => (props.br ? props.br : '5px')};
  border: 1px solid #a3a3a3;
  padding-left: 1rem;
  font-size: 1.3rem;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  font-family: 'Pretendard-Regular';
  width: 68.7vw;
  padding: 0.5rem;
  min-width: 863.87px;
`;

const StyledInput = styled.input`
  width: ${props => (props.width ? props.width : '95%')};
  height: ${props => (props.height ? props.height : '1.5rem')};
  border-radius: ${props => (props.br ? props.br : '5px')};
  border: 1px solid #a3a3a3;
  padding-left: 1rem;
  font-size: 1rem;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  font-family: 'Pretendard-Regular';
  width: 200px;
  padding: 0.5rem;
`;

const UpdateShop = () => {
  const { state } = useLocation();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [kind, setKind] = useState('');
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [showPrice, setShowPrice] = useState('');
  const userToken = sessionStorage.getItem('userToken');

  const editorRef = useRef();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const contentChange = () => {
    const data = editorRef.current.getInstance().getHTML();
    setContent(data);
  };

  const titleChange = e => {
    setTitle(e.target.value);
  };

  const onSelect = e => {
    setKind(e.target.value);
  };

  const saleName = e => {
    setName(e.target.value);
  };

  function numPrice(value) {
    const num = value.replace(',', '');
    return parseInt(num, 10);
  }

  function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
  }

  function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
  }

  const inputNumberFormat = e => {
    const numValue = parseInt(e.target.value);
    setPrice(numValue);

    setShowPrice(numValue);
  };
  // console.log(state);
  const onSubmit = e => {
    e.preventDefault();
    dispatch({
      type: UPDATE_SHOP_REQUEST,
      data: {
        token: userToken,
        deal_id: state.dealId,
        title,
        content,
        kind,
        name,
        price,
        navigate,
      },
    });
  };

  const onClick = () => {
    navigate(`/shop/detail`, {
      state: { dealId: state.dealId },
    });
  };

  useEffect(() => {
    setTitle(state.title);
    setContent(state.content);
    setShowPrice(state.price);
    setPrice(state.price);
    setName(state.name);
    setKind(state.kind);
  }, []);
  // console.log(state);
  return (
    <div>
      {content && (
        <>
          <NowContainer desc="중 고 장 터" />
          <StyleContainer>
            <hr />
            <form onSubmit={onSubmit}>
              <div>
                <StyledCommentInput
                  required
                  value={title}
                  onChange={titleChange}
                  id="title"
                  type="text"
                  placeholder="제목을 입력해주세요"
                />
                <div
                  style={{
                    display: 'flex',
                    marginTop: '8px',
                  }}
                >
                  <Box sx={{ minWidth: 120 }}>
                    <FormControl sx={{ width: '200px' }}>
                      <InputLabel id="demo-simple-select-label">
                        구성품 종류
                      </InputLabel>
                      <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        label="Kind"
                        value={kind}
                        onChange={onSelect}
                        sx={{ height: 40, marginTop: 1 }}
                      >
                        <MenuItem value="완성차 / 프레임">
                          완성차 / 프레임
                        </MenuItem>
                        <MenuItem value="구동계">구동계</MenuItem>
                        <MenuItem value="휠셋">휠셋</MenuItem>
                        <MenuItem value="부속품">부속품</MenuItem>
                        <MenuItem value="의류">의류</MenuItem>
                        <MenuItem value="기타 장비">기타 장비</MenuItem>
                      </Select>
                    </FormControl>
                  </Box>
                  <div style={{ marginTop: '7px', marginLeft: '7px' }}>
                    <StyledInput
                      required
                      value={name}
                      onChange={saleName}
                      id="name"
                      type="text"
                      placeholder="판매 물건을 입력해주세요"
                      sx={{ marginLeft: '15px' }}
                    />
                  </div>
                  <div style={{ marginTop: '7px', marginLeft: '7px' }}>
                    <StyledInput
                      placeholder="판매 가격을 입력해주세요"
                      maxLength="11"
                      value={showPrice}
                      onChange={inputNumberFormat}
                      required
                      type="number"
                      id="price"
                      sx={{ marginLeft: '15px' }}
                    />
                  </div>
                </div>
              </div>
              <hr />
              <Editor
                initialValue={content}
                placeholder="내용을 입력해주세요."
                previewStyle="vertical"
                height="600px"
                initialEditType="wysiwyg"
                plugins={[colorSyntax]}
                language="ko-KR"
                ref={editorRef}
                onChange={contentChange}
                hooks={{
                  addImageBlobHook: async (blob, callback) => {
                    // console.log(blob);
                    const formData = new FormData();
                    formData.append('imageFile', blob);

                    await axios({
                      method: 'post',
                      url: `${BASE_URL}deal/imageUpload`,
                      data: formData,
                      headers: {
                        'Content-Type': 'multipart/form-data',
                      },
                    })
                      .then(res => {
                        const imageUrl = res.data;
                        const url = `${BASE_URL}deal/imageDownload/${imageUrl}`;
                        callback(url, 'image');
                      })
                      .catch(err => {
                        console.log(err);
                      });
                    return false;
                  },
                }}
              />
              <hr />
              <Button
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
                name="작성"
              />
              <Button
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
                name="취소"
                onClick={onClick}
              ></Button>
            </form>
          </StyleContainer>
        </>
      )}
      <div style={{ height: '20px' }}></div>
    </div>
  );
};
export default UpdateShop;
