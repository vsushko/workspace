## CSS Modules
https://github.com/css-modules/css-modules

### Enable css modules
```
{
  test: cssRegex,
  exclude: cssModuleRegex,
  use: getStyleLoaders({
      importLoaders: 1,
      modules: true,
      localIdentName: '[name]__[local]__[hash:base64:5]'
  }),
}
```
#### How to Use CSS Modules with Create React App:
https://medium.com/nulogy/how-to-use-css-modules-with-create-react-app-9e44bec2b5c2

### Fontawesome
https://stackoverflow.com/questions/23116591/how-to-include-a-font-awesome-icon-in-reacts-render

## Node js
### TROUBLESHOOTING
#### NPM self_signed_cert_in_chain
solution: [link](https://stackoverflow.com/questions/34498736/npm-self-signed-cert-in-chain)

```
npx create-react-app react-client
```
Missing dependencies in package.json
solution:
```
create-react-app react-client --use-npm
```

IE11
```
<script nomodule src="https://cdnjs.cloudflare.com/ajax/libs/babel-polyfill/6.26.0/polyfill.min.js" integrity="sha256-WRc/eG3R84AverJv0zmqxAmdwQxstUpqkiE+avJ3WSo="    crossorigin="anonymous"></script>
```

## React
React Developer Tools - [link](https://chrome.google.com/webstore/detail/react-developer-tools/fmkadmapgofadopljbjfkapdkoienihi/related?hl=en)

Redux DevTools - [link](https://chrome.google.com/webstore/detail/redux-devtools/lmhkpmbekcpmknklioeibfkpmmfibljd?hl=en)

Install react app:
```
npm install -g create-react-app
```
Creates react app
```
create-react-app ppmtool-react-client
```
react router:
```
npm install react-router-dom
```
```
npm install react-router-dom@4.3.1
```
redux:
```
npm i redux react-redux redux-thunk
```
axios
```
npm i axios
```

## Bootstrap
Installation:
```
npm install bootstrap
```

## Other
for the input labels
```
npm i classnames
```

## Spring app with react
[click](https://github.com/eugenp/tutorials/tree/master/spring-security-react)
