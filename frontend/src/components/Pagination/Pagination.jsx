/* eslint-disable */
import React, { useState } from 'react';
import { Button } from '@mui/material';
import styled from 'styled-components';

const PaginationAlign = styled.div`
  display: flex;
  vertical-align: center;
  justify-content: center;
`;

const Pagination = ({ page, setPage, totalPage }) => {
  //   console.log(page, setPage, totalPage);
  //   console.log(Array(totalPage));
  const [currPage, setCurrPage] = useState(page);
  let firstNum = currPage - (currPage % 10) + 1;
  let lastNum = currPage - (currPage % 10) + 10;
  return (
    <div>
      {totalPage >= 1 && (
        <PaginationAlign>
          <Button
            style={{ fontWeight: 'bolder' }}
            variant="text"
            size="large"
            onClick={() => {
              setPage(page - 1);
              setCurrPage(page - 1);
            }}
            disabled={page === 0}
          >
            &lt;
          </Button>
          {Array(10)
            .fill()
            .map((_, i) => {
              if (firstNum + i <= totalPage) {
                return (
                  <Button
                    style={{ fontWeight: 'bolder' }}
                    variant="text"
                    size="large"
                    value={i}
                    key={i}
                    onClick={() => {
                      setPage(firstNum + i - 1);
                    }}
                  >
                    {firstNum + i}
                  </Button>
                );
              }
            })}
          <Button
            style={{ fontWeight: 'bolder' }}
            size="large"
            variant="text"
            onClick={() => {
              setPage(page + 1);
              setCurrPage(page + 1);
            }}
            disabled={page === totalPage - 1}
          >
            &gt;
          </Button>
        </PaginationAlign>
      )}
    </div>
  );
};
export default Pagination;
