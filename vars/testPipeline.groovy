#!/usr/bin/env groovy

def call(Map config = [:]) {
  def cronSchedule = config.cronSchedule ?: null
  def testCommand = config.testCommand ?: 'test'
  def propertiesList = [
    parameters(commonParameters(config))
  ]
  
  if (cronSchedule) {
    propertiesList << pipelineTriggers([cron(config.cronSchedule)])
  }

  properties(propertiesList)

  pipeline {
    agent any

    tools {
      nodejs 'Node22'
    }

    environment {
      TEST_BRANCH = "${params.TEST_BRANCH}"
      TEST_TYPE = "${params.TEST_TYPE}"
      TEST_ENV = "${params.TEST_ENV}"
      WORKERS = "${params.WORKERS}"
      SLACK_CHANNEL = "${params.SLACK_CHANNEL}"
    }

    stages {
      stage('Build started') {
        steps {
          script {
            def slackParams = [
              'Test branch': env.TEST_BRANCH,
              'Test type': env.TEST_TYPE,
              'Test environment': env.TEST_ENV,
              'Workers': env.WORKERS,
            ]

            notifySlack.pipelineStart(
              channel: env.SLACK_CHANNEL,
              additionalParams: slackParams
            )
          }
        }
      }

      stage('Checkout code') {
        steps {
          script {
            commonStages.checkoutCode()
          }
        }
      }

      stage('Install dependencies') {
        steps {
          script {
            commonStages.installDependencies()
          }
        }
      }

      stage('Run Tests') {
        steps {
          script {
            commonStages.runTests(testCommand)
          }
        }
      }
    }

    post {
      success {
        script {
          notifySlack.pipelineSuccess(channel: env.SLACK_CHANNEL)
        }
      }

      failure {
        script {
          notifySlack.pipelineFailure(channel: env.SLACK_CHANNEL)
        }
      }

      always {
        script {
          commonStages.publishReports()
          commonStages.cleanup()
        }
      }
    }
  }
}
