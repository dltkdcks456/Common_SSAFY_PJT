module.exports = {
  env: {
    browser: true,
    es2021: true,
  },
  extends: ['airbnb', 'plugin:prettier/recommended'],
  plugins: ['prettier'],
  env: {
    browser: true,
    es6: true,
    node: true,
  },
  rules: {
    'react/jsx-filename-extension': [1, { extensions: ['.js', '.jsx'] }],
    'no-param-reassign': 0,
    'prettier/prettier': [
      'error',
      {
        endOfLine: 'auto',
        userTabs: false,
      },
    ],
    // react 함수형 컴포넌트 저으이 방식 선언
    'react/function-component-definition': [
      2,
      { namedComponents: 'arrow-function' },
    ],
    'arrow-body-style': ['error', 'as-needed'],
    'import/prefer-default-export': 'off',
    'react/button-has-type': 'off',
    'jsx-a11y/label-has-associated-control': [
      2,
      {
        labelAttributes: ['htmlFor'],
      },
    ],
    "react/jsx-props-no-spreading": "off",
    "react/prop-types": "off",
  },
  settings: {
    'import/resolver': {
      node: {
        extensions: ['.js', '.jsx', '.ts', '.tsx'],
      },
    },
  },
};
