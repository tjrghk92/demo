var path = require('path');
 
module.exports = {
    context: path.resolve(__dirname, 'src/main/resources/jsx'),
    entry: {
        main: './main/main.jsx',
        member: './member/member.jsx',
        menu: './menu/menu.jsx',
    },
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/js/bundle/[name]/[name].bundle.js'
    },
    mode: 'none',
    module: {
        rules: [ 
            {
                test: /\.(jsx|js)$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: [ 
                            // [
                            //       //chrome 55 이상으로 지정 
                            //     /*
                            //       "@babel/preset-env", {
                            //         "targets": {"chrome": "55"}, 
                            //         "debug": true
                            //     }*/
                                
                                
                            //    //['@babel/preset-env']
                            // ],
                            // ['@babel/preset-react']
                            '@babel/preset-env','@babel/preset-react'
                             
                        ]
                    }
                }
            },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            }
        ]
    }
};