/* eslint-disable */
import React, { useRef, useState } from 'react';
import styled from 'styled-components';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';
import Swal from 'sweetalert2';
import { CREATE_SHOP_REQUEST } from '../../store/modules/shopModule';
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

const CreateShop = () => {
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

  const onSubmit = e => {
    e.preventDefault();
    if (!kind && !content) {
      Swal.fire({
        title: '?????? ?????? ??????',
        text: '??????, ??????, ????????? ??????, ?????? ????????? ?????? ??????????????????',
        icon: 'error',
      });
    } else {
      if (title && content) {
        dispatch({
          type: CREATE_SHOP_REQUEST,
          data: {
            token: userToken,
            title,
            content,
            kind,
            name,
            price,
            navigate,
          },
        });
      } else {
        Swal.fire({
          title: '?????? ?????? ??????',
          text: '?????? ??? ????????? ?????? ??????????????????',
          icon: 'error',
        });
      }
    }
  };

  const onClick = () => {
    navigate(`/shop`);
  };

  const onSelect = e => {
    setKind(e.target.value);
  };

  const saleName = e => {
    setName(e.target.value);
  };

  const inputNumberFormat = e => {
    const numValue = parseInt(e.target.value);
    setPrice(numValue);

    setShowPrice(numValue);
    // if (numValue) {
    //   setShowPrice(numValue.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
    // }
    // console.log(value);
  };

  return (
    <div>
      <NowContainer desc="??? ??? ??? ???" />
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
              placeholder="????????? ??????????????????"
            />
            <div style={{ display: 'flex', marginTop: '8px' }}>
              <Box sx={{ minWidth: 120 }}>
                <FormControl sx={{ width: '200px' }}>
                  <InputLabel id="demo-simple-select-label">
                    ????????? ??????
                  </InputLabel>
                  <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Kind"
                    value={kind}
                    onChange={onSelect}
                    sx={{ height: 40, marginTop: 1 }}
                  >
                    <MenuItem value="????????? / ?????????">????????? / ?????????</MenuItem>
                    <MenuItem value="?????????">?????????</MenuItem>
                    <MenuItem value="??????">??????</MenuItem>
                    <MenuItem value="?????????">?????????</MenuItem>
                    <MenuItem value="??????">??????</MenuItem>
                    <MenuItem value="?????? ??????">?????? ??????</MenuItem>
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
                  placeholder="?????? ????????? ??????????????????"
                  sx={{ marginLeft: '15px' }}
                />
              </div>
              <div style={{ marginTop: '7px', marginLeft: '7px' }}>
                <StyledInput
                  placeholder="?????? ????????? ??????????????????"
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
            placeholder="????????? ??????????????????."
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
            name="??????"
          />
          <Button
            width="5rem"
            ml="0.1rem"
            mr="0.1rem"
            mt="0.2rem"
            height="2.3rem"
            bc="white"
            name="??????"
            onClick={onClick}
          ></Button>
        </form>
      </StyleContainer>
      <div style={{ height: '20px' }}></div>
    </div>
  );
};

export default CreateShop;
