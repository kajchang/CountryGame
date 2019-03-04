const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

config.plugins.push(
    new HtmlWebpackPlugin({
        filename: 'index.html',
        template: path.join(__dirname, '..', 'src', 'main', 'web', 'index.html')
    })
);

config.plugins.push(new webpack.NamedModulesPlugin());
config.plugins.push(new webpack.HotModuleReplacementPlugin());
