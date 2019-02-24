const CopyPlugin = require('copy-webpack-plugin');
const path = require('path');

console.log(__dirname);

config.plugins.push(new CopyPlugin([
    { from: path.join(__dirname, '..', 'src', 'main', 'web', 'index.prod.html'), to: path.join(__dirname, 'index.html')}
]));
