#!/usr/bin/env groovy

import org.longnv.jenkins.TestType
import org.longnv.jenkins.SlackChannel
import org.longnv.jenkins.Environment

def call(Map config = [:]) {
  return [
    string(
      name: 'TEST_BRANCH',
      defaultValue: config.testBranch ?: 'main',
      description: 'Test branch to run the tests'
    ),
    choice(
      name: 'TEST_TYPE',
      choices: config.testTypes ?: TestType.getAllTestTypes(),
      description: 'Select test type'
    ),
    choice(
      name: 'TEST_ENV',
      choices: config.environments ?: Environment.getAllEnvironments(),
      description: 'Select test environment'
    ),
    string(
      name: 'WORKERS',
      defaultValue: config.workers ?: '4',
      description: 'Number of parallel workers. Maximum should be 10'
    ),
    string(
      name: 'SLACK_CHANNEL',
      defaultValue: config.slackChannel ?: SlackChannel.JENKINS_TESTS_PRODTEST,
      description: 'Slack channel for notifications'
    )
  ]
}
