def call(String image, String imageLatest) {
    // Build the docker image using a Dockerfile
    docker.build(image)
    // override the latest tag
    docker.build(imageLatest)
}
