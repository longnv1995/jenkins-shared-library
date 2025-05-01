#!/usr/bin/env groovy

def runTests(String testCommand = 'test') {
  sh """
    TEST_ENV=${env.TEST_ENV} \
    TEST_TYPE=${env.TEST_TYPE} \
    WORKERS=${env.WORKERS} \
    make ${testCommand}
  """
}

def setupTools() {
    nodejs 'Node22'
}


def checkoutCode(Map config = [:]) {
  def defaultBranch = config.branch ?: 'main'
  def repoUrl = config.repoUrl ?: '';

  if (repoUrl) {
    // External repository checkout
    checkout([
      $class: 'GitSCM',
      branches: [[ name: "*/${defaultBranch}" ]],
      userRemoteConfigs: [[
        credentialsId: config.credentialsId ?: '',
        url: repoUrl
      ]]
    ])
  } else {
    // Standard SCM checkout (from Jenkinsfile's repo)
    checkout scm
  }
}

def installDependencies() {
  sh 'npm ci'
  sh 'npx playwright install --with-deps'
}

def publishReports() {
  archiveArtifacts artifacts: 'playwright-report/**', allowEmptyArchive: true
  publishHTML([
    allowMissing: false,
    alwaysLinkToLastBuild: true,
    keepAll: true,
    reportDir: 'playwright-report',
    reportFiles: 'index.html',
    reportName: 'Playwright Report'
  ])
}

def cleanup() {
  cleanWs()
}