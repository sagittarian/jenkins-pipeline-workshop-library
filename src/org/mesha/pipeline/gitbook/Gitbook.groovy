package org.mesha.pipeline.gitbook

class Gitbook extends BaseGitbook {
    Gitbook(script) {
        super(script)
    }

    void initParams() {
        // calculate GIT lastest commit short-hash
        this.gitCommitHash = script.sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
        this.shortCommitHash = gitCommitHash.take(7)
        // calculate a sample version tag
        this.version = shortCommitHash
        this.bulidId = script.env.BUILD_ID
        this.project = script.env.PROJECT
        // set the build display name
        this.currentBuildDisplayName = "#${buildId}-${version}"
        this.image = "$project:$version"
        this.imageLatest = "$project:latest"
    }
}
