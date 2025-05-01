#!/usr/bin/env groovy

import org.longnv.pipeline.SlackChannel
import org.longnv.pipeline.BuildStatus

def pipelineStart(Map config = [:]) {
  String channel = config.channel ?: SlackChannel.JENKINS_TESTS_PRODTEST
  Map params = config.additionalParams ?: [:]
  String currentTime = new Date().format("yyyy-MM-dd HH:mm:ss")
  String currentUser = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userId ?: 'default';

  def message = """
    *[${currentBuild.fullProjectName}]* Running tests with following parameters
    *Started at:* ${currentTime}
    *Executed by:* ${currentUser}
  """.stripIndent()

  params.each { key, value ->
    message += "*${key}:* ${value}\n"
  }
  message += "*Build number* :point_right: <${env.BUILD_URL}|${env.BUILD_NUMBER}>"

  slackSend(channel: channel, color: "good", message: message)
}

def pipelineSuccess(Map config = [:]) {
  String channel = config.channel ?: SlackChannel.JENKINS_TESTS_PRODTEST
  String customMessage = config.customMessage ?: ''

  def message = """
    *All tests PASSED!* :tada:
    *Build number* :point_right: <${env.BUILD_URL}|${env.BUILD_NUMBER}>
    *Duration:* ${currentBuild.durationString}
  """.stripIndent()

  if (customMessage) {
    message += "\n${customMessage}"
  }

  slackSend(channel: channel, color: "good", message: message)
}

def pipelineFailure(Map config = [:]) {
  String channel = config.channel ?: SlackChannel.JENKINS_TESTS_PRODTEST
  def error = config.error ?: ''
  def failedTests = config.failedTests ?: ''

  def message = """
    *Some tests failed!* :sob:
    *Build number* :point_right: <${env.BUILD_URL}|${env.BUILD_NUMBER}>
    *Duration:* ${currentBuild.durationString}
  """.stripIndent()

  if (failedTests) {
    message += "\n*Failed tests:* ${failedTests}"
  }

  if (error) {
    message += "\n*Error:* ```${error}```"
  }

  slackSend(channel: channel, color: "danger", message: message)
}
