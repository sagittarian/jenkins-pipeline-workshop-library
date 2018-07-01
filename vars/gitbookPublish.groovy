def call(String image, String imageLatest) {
    docker.withRegistry("https://registry.hub.docker.com", "dockerHub")	{
        // version eq latest git tag
        docker.image(image).push()
        // override the latest tag
        docker.image(imageLatest).push()
    }
}
