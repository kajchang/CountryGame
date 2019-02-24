const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

if (process.env.TRAVIS) {
    config.plugins.push(new UglifyJsPlugin);
}
