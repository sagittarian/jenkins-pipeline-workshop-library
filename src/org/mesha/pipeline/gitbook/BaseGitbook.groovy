package org.mesha.pipeline.gitbook

abstract class BaseGitbook implements Serializable {
    def script
    def image
    def imageLatest
    def gitCommitHash
    def shortCommitHash
    def version
    def buildId
    def project
    def currentBuildDisplayName

    BaseGitbook(script) {
        this.script = script
    }

    void run() {
        script.timestamps() {
            runImpl()
        }
    }

    void runStage(String name, Closure stage) {
        script.echo "--- Start stage [$name] ---"
        script.stage(name, stage)
        script.echo "--- End stage [$name] ---"
    }

    void runImpl() {
        runStage('Setup', this.&initParams)
        runStage('Build', this.&build)
        runStage('Publish', this.&publish)
    }

    void build() {
        script.dockerBuild(image, imageLatest)
    }

    void publish() {
        script.gitbookPublish(image, imageLatest)
    }
}
