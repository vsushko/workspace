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
### prop-types
https://github.com/facebook/prop-types

#### How to Use CSS Modules with Create React App:
https://medium.com/nulogy/how-to-use-css-modules-with-create-react-app-9e44bec2b5c2

### Validation & React validation
https://validatejs.org/<br>
https://github.com/christianalfoni/formsy-react<br>
https://www.npmjs.com/package/react-validation<br>
https://react.rocks/tag/Validation<br>

### Fontawesome
https://stackoverflow.com/questions/23116591/how-to-include-a-font-awesome-icon-in-reacts-render

## Node js
### TROUBLESHOOTING
#### NPM self_signed_cert_in_chain
solution: [link](https://stackoverflow.com/questions/34498736/npm-self-signed-cert-in-chain)

IE11
```
<script nomodule src="https://cdnjs.cloudflare.com/ajax/libs/babel-polyfill/6.26.0/polyfill.min.js" integrity="sha256-WRc/eG3R84AverJv0zmqxAmdwQxstUpqkiE+avJ3WSo="    crossorigin="anonymous"></script>
```

Trouble with finding the node in ubuntu:
```
https://askubuntu.com/questions/501485/having-trouble-to-find-the-node-in-ubuntu-14-04-after-installing
```
npm throws error without sudo:
```
sudo chown -R `whoami` ~/.npm
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

## Webpack
#### Upgrade to Webpack 4 (from 3)
https://dev.to/flexdinesh/upgrade-to-webpack-4---5bc5

### webpack project setup
```
npm install --save-dev webpack webpack-dev-server
```
### install babel
```
npm install --save-dev babel-loader babel-core babel-preset-react babel-preset-env
```
### Browser list
```
https://github.com/browserslist/browserslist
```
### NextJS
```
https://github.com/zeit/next.js/
```
### Jest
enospc system limit for number of file watchers reached:
```
echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p
```
### Flexbox
https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Basic_Concepts_of_Flexbox
<br>
https://css-tricks.com/snippets/css/a-guide-to-flexbox/
<br>
https://internetingishard.com/html-and-css/flexbox/

