/* eslint-disable */
import React, { Component } from 'react';
import OpenViduVideoComponent from './OvVideo';
import styled from 'styled-components';

const StyledContainer = styled.div`
  position: absolute;
  margin-left: 24%;
  margin-top: 2rem;
`;

export default class UserVideoComponent extends Component {
  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <StyledContainer>
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
          </StyledContainer>
        ) : null}
      </div>
    );
  }
}
