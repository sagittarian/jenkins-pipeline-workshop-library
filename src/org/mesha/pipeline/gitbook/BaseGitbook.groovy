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
        // Build the docker image using a Dockerfile
        script.docker.build(image)
        // override the latest tag
        script.docker.build(imageLatest)
    }

    void publish() {
        script.docker.withRegistry("https://registry.hub.docker.com", "dockerHub")	{
            // version eq latest git tag
            script.docker.image(image).push()
            // override the latest tag
            script.docker.image(imageLatest).push()
        }
    }
}
