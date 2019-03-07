module.exports = {
	chainWebpack: config => {
		if (config.plugins.has('extract-css')) {
			const extractCSSPlugin = config.plugin('extract-css')
			// Don't add build hash to output files
			extractCSSPlugin && extractCSSPlugin.tap(() => [{
				filename: '[name].css',
				chunkFilename: '[name].css'
			}])
		}

		config.module
			.rule("i18n")
			.resourceQuery(/blockType=i18n/)
			.type('javascript/auto')
			.use("i18n")
			.loader("@kazupon/vue-i18n-loader")
			.end();
	},
	configureWebpack: {
		// Don't add build hash to output files
		output: {
			filename: '[name].js',
			chunkFilename: '[name].js'
		}
	},
	outputDir: '../'.repeat(3) + 'assets',
	filenameHashing: false
}