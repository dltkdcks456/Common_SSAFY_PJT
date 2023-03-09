/* eslint-disable */
import React, { Component } from 'react';
import styled from 'styled-components';

const StyledContainer = styled.video`
  width: 200px;
`;

export default class SubOpenViduVideoComponent extends Component {
  constructor(props) {
    super(props);
    this.videoRef = React.createRef();
  }

  componentDidUpdate(props) {
    if (props && !!this.videoRef) {
      this.props.streamManager.addVideoElement(this.videoRef.current);
    }
  }

  componentDidMount() {
    if (this.props && !!this.videoRef) {
      this.props.streamManager.addVideoElement(this.videoRef.current);
    }
  }

  render() {
    return <StyledContainer autoPlay={true} ref={this.videoRef} />;
  }
}
