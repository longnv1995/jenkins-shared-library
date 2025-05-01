package org.longnv.jenkins

class TestType {
  static final String E2E_TEST = 'e2e-test'
  static final String API_TEST = 'api-test'
  static final String VISUAL_TEST = 'visual-test'
  static final String SMOKE_TEST = 'smoke-test'
  static final String REGRESSION_TEST = 'regression-test'

  static List<String> getAllTestTypes() {
    return [ E2E_TEST, API_TEST, VISUAL_TEST, SMOKE_TEST, REGRESSION_TEST ]
  }
}
