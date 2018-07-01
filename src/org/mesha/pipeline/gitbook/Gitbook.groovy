package org.mesha.pipeline.gitbook

class Gitbook extend BaseGitbook {
    void initParams() {
        // calculate GIT lastest commit short-hash
        this.gitCommitHash = script.sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
        this.shortCommitHash = gitCommitHash.take(7)
        // calculate a sample version tag
        this.version = shortCommitHash
        // set the build display name
        this.currentBuildDisplayName = "#${BUILD_ID}-${VERSION}"
        this.image = "$PROJECT:$VERSION"
        this.imageLatest = "$PROJECT:latest"
    }
}
