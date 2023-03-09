/* eslint-disable */
import React, { useState } from 'react';
import { imageUploadAPI } from '../../store/apis/userApi';
import { BASE_URL } from '../../utils/urls';

// SettingUserThumbnail

const SettingUserImage = ({ imagePath }) => {
  const url = `${BASE_URL}user/imageDownload/${imagePath}`;
  const [image, setImage] = useState({
    image_file: '',
    preview_URL: url,
  });

  let inputRef;

  const saveImage = e => {
    e.preventDefault();
    const fileReader = new FileReader();

    if (e.target.files[0]) {
      fileReader.readAsDataURL(e.target.files[0]);
    }
    fileReader.onload = () => {
      setImage({
        image_file: e.target.files[0],
        preview_URL: fileReader.result,
      });
    };
  };

  const deleteImage = () => {
    setImage({
      image_file: '',
      preview_URL: url,
    });
    // console.log('프로필 사진 삭제 버튼 눌리면 기본 이미지로 바꿔야됨!!');
  };

  const sendImageToServer = async () => {
    const token = sessionStorage.getItem('userToken');
    if (image.image_file) {
      const formData = new FormData();
      formData.append('imageFile', image.image_file);
      formData.append('token', token);
      await imageUploadAPI(formData);
      // console.log('이미지 등록 완료');
      // setImage({
      //   image_file: '',
      //   preview_URL: 'img/default_image.png',
      // });
    }
  };

  const resetValue = e => (e.target.value = null);

  const resetRef = refParam => (inputRef = refParam);

  return (
    <div className="uploader-wrapper">
      <input
        type="file"
        accept="image/*"
        onChange={saveImage}
        // 클릭할 때마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
        // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있다
        onClick={resetValue}
        ref={resetRef}
        style={{ display: 'none' }}
      />
      <div className="img-wrapper">
        <img src={image.preview_URL} />
      </div>

      <div className="upload-btn">
        <button onClick={() => inputRef.click()}>Preview</button>
        <button onClick={deleteImage}>Delete</button>
        <button onClick={sendImageToServer}>Upload</button>
      </div>
    </div>
  );
};
export default SettingUserImage;
