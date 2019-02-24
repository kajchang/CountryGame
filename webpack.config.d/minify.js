const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

if (defined.PRODUCTION) {
    config.plugins.push(new UglifyJsPlugin);
}
