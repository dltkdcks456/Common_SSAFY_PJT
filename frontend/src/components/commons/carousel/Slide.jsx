import React from 'react';
import styled from 'styled-components';

const Slide = ({ img }) => <IMG src={img} />;

const IMG = styled.img`
  width: 100%;
  height: 20vh;
`;

export default Slide;
