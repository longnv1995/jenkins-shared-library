def buildNotifyChannel() {
  return env.NOTIFY_ME ? "${env.SLACK_CHANNEL},${env.NOTIFY_ME}" : env.SLACK_CHANNEL
}
