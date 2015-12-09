'use strict';

var webpack = require('webpack'),
    HtmlWebpackPlugin = require('html-webpack-plugin'),
    ExtractTextPlugin = require('extract-text-webpack-plugin'),
    path = require('path'),
    srcPath = path.join(__dirname, 'src'),
    production = 'production' === process.env.NODE_ENV;

module.exports = {
    target: 'web',
    cache: true,
    entry: {
        module: path.join(srcPath, 'app.js')
    },
    resolve: {
        root: srcPath,
        extensions: ['', '.js'],
        modulesDirectories: ['node_modules', 'src']
    },
    output: {
        filename: production ? 'app.min.js' : 'app.js',
        path: production ? '../src/main/resources/assets' : path.join(__dirname, '.tmp'),
        publicPath: production ? '/assets/' : ''
    },
    module: {
        loaders: [
            { test: /\.js?$/, exclude: /node_modules/, loader: 'babel?cacheDirectory' },
            { test: /\.less$/, exclude: /node_modules/, loader: ExtractTextPlugin.extract('style', 'css!less') },
            { test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?limit=10000&mimetype=application/font-woff' },
            { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?limit=10000&mimetype=application/octet-stream' },
            { test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: 'file' },
            { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?limit=10000&mimetype=image/svg+xml' }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: 'Shoppinglist',
            template: 'src/index.html',
            inject: 'body'
        }),
        new ExtractTextPlugin('style.css'),
        new webpack.DefinePlugin({
            __FACEBOOK_APP_ID__: production ? "'481307368726222'" : "'481310995392526'",
            'process.env': {
                NODE_ENV: JSON.stringify(process.env.NODE_ENV)
            }
        }),
        new webpack.NoErrorsPlugin()
    ],

    debug: true,
    devtool: production ? null : 'eval-cheap-module-source-map',
    devServer: {
        host: '0.0.0.0',
        contentBase: './.tmp',
        historyApiFallback: true,
        proxy: {
            '/api/*': 'http://localhost:7100/'
        },
        quiet: false,
        noInfo: false,
        stats: {
            assets: false,
            colors: true,
            version: false,
            hash: false,
            timings: true,
            chunks: false,
            chunkModules: false
        }
    }
};
