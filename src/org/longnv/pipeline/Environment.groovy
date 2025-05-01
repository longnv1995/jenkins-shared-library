package org.longnv.pipeline

class Environment {
  static final String DEVELOPMENT = 'development'
  static final String STAGING = 'staging'
  static final String PRODUCTION = 'production'

	static List<String> getAllEnvironments() {
		return [ DEVELOPMENT, STAGING, PRODUCTION ]
	}
}
