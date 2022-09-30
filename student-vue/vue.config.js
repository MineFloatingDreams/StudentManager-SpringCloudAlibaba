// vue.config.js

/**
 * @type {import('@vue/cli-service').ProjectOptions}
 */
module.exports = {
    devServer: {
        host: '0.0.0.0',
        port: 80,
        open: true,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                pathRewrite: {'^/api': ''}
            }
        },
        disableHostCheck: true,
    },
}